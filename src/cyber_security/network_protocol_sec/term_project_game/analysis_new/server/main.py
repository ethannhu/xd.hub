"""
Server script for hosting games with rollback capability
"""

import socket
import json
import time
import random
import threading
from copy import deepcopy
from collections import deque

ADDR = "0.0.0.0"
PORT = 8000
MAX_PLAYERS = 10
MSG_SIZE = 2048
MAX_SNAPSHOTS = 60  # Keep snapshots for about 1 second (assuming 60 FPS)

class GameState:
    def __init__(self):
        self.players = {}
        self.tick = 0
        self.snapshots = deque(maxlen=MAX_SNAPSHOTS)  # Stores past game states
        self.pending_commands = {}  # tick -> list of commands

    def take_snapshot(self):
        """Save current game state"""
        snapshot = {
            "tick": self.tick,
            "players": deepcopy(self.players),
            "timestamp": time.time()
        }
        self.snapshots.append(snapshot)
        return snapshot

    def find_snapshot(self, target_tick):
        """Find the closest snapshot for rollback"""
        for snapshot in reversed(self.snapshots):
            if snapshot["tick"] <= target_tick:
                return snapshot
        return None

# Setup server socket
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((ADDR, PORT))
s.listen(MAX_PLAYERS)

game_state = GameState()

def generate_id(player_list: dict, max_players: int):
    """Generate a unique identifier"""
    while True:
        unique_id = str(random.randint(1, max_players))
        if unique_id not in player_list:
            return unique_id

def handle_messages(identifier: str):
    client_info = game_state.players[identifier]
    conn: socket.socket = client_info["socket"]
    username = client_info["username"]
    last_processed_tick = 0

    while True:
        try:
            msg = conn.recv(MSG_SIZE)
        except ConnectionResetError:
            break

        if not msg:
            break

        try:
            # Extract JSON message
            msg_decoded = msg.decode("utf8")
            left_bracket = msg_decoded.index("{")
            right_bracket = msg_decoded.index("}") + 1
            msg_json = json.loads(msg_decoded[left_bracket:right_bracket])
        except (ValueError, json.JSONDecodeError) as e:
            print(f"Message parsing error: {e}")
            continue

        print(f"Received message from {username} (ID {identifier}): {msg_json}")

        # Add client tick information if missing
        if "client_tick" not in msg_json:
            msg_json["client_tick"] = game_state.tick

        # Handle different message types
        if msg_json["object"] == "player":
            # Store command for potential rollback
            if msg_json["client_tick"] not in game_state.pending_commands:
                game_state.pending_commands[msg_json["client_tick"]] = []
            game_state.pending_commands[msg_json["client_tick"]].append({
                "type": "player_update",
                "player_id": identifier,
                "data": msg_json
            })

            # Check if we need to rollback (late-arriving command)
            if msg_json["client_tick"] < game_state.tick:
                print(f"Late command detected! Current tick: {game_state.tick}, Command tick: {msg_json['client_tick']}")
                rollback_and_rerun(msg_json["client_tick"])
            
            # Update player state
            game_state.players[identifier].update({
                "position": msg_json["position"],
                "rotation": msg_json["rotation"],
                "health": msg_json["health"]
            })

        # Broadcast update to all players
        broadcast_state_update()

    # Player disconnected
    handle_player_disconnect(identifier)

def rollback_and_rerun(target_tick):
    """Rollback game state and re-simulate from target tick"""
    print(f"Attempting rollback to tick {target_tick}")
    
    # Find the closest snapshot
    snapshot = game_state.find_snapshot(target_tick)
    if not snapshot:
        print("No suitable snapshot found for rollback")
        return
    
    # Restore game state
    game_state.players = deepcopy(snapshot["players"])
    game_state.tick = snapshot["tick"]
    
    # Replay commands
    for tick in range(target_tick, game_state.tick + 1):
        if tick in game_state.pending_commands:
            for cmd in game_state.pending_commands[tick]:
                apply_command(cmd)
        game_state.tick += 1
    
    print(f"Rollback complete. Current tick: {game_state.tick}")

def apply_command(cmd):
    """Apply a game command to the current state"""
    if cmd["type"] == "player_update":
        player_id = cmd["player_id"]
        if player_id in game_state.players:
            game_state.players[player_id].update({
                "position": cmd["data"]["position"],
                "rotation": cmd["data"]["rotation"],
                "health": cmd["data"]["health"]
            })

def broadcast_state_update():
    """Send current game state to all players"""
    current_state = {
        "tick": game_state.tick,
        "players": {
            pid: {
                "position": info["position"],
                "rotation": info["rotation"],
                "health": info["health"]
            }
            for pid, info in game_state.players.items()
        }
    }
    
    for player_id, player_info in game_state.players.items():
        try:
            player_info["socket"].send(json.dumps({
                "object": "game_state",
                "data": current_state
            }).encode("utf8"))
        except OSError:
            pass

def handle_player_disconnect(identifier):
    """Handle player disconnection"""
    if identifier not in game_state.players:
        return
    
    # Notify other players
    for player_id in game_state.players:
        if player_id != identifier:
            try:
                game_state.players[player_id]["socket"].send(json.dumps({
                    "id": identifier,
                    "object": "player",
                    "left": True
                }).encode("utf8"))
            except OSError:
                pass
    
    # Clean up
    username = game_state.players[identifier]["username"]
    print(f"Player {username} (ID {identifier}) disconnected")
    game_state.players[identifier]["socket"].close()
    del game_state.players[identifier]

def game_loop():
    """Main game simulation loop"""
    while True:
        # Take regular snapshots
        game_state.take_snapshot()
        
        # Process game logic
        game_state.tick += 1
        
        # Broadcast state periodically
        if game_state.tick % 5 == 0:  # Every 5 ticks
            broadcast_state_update()
        
        time.sleep(0.016)  # ~60 FPS

def main():
    print("Server started, listening for new connections...")
    
    # Start game loop in separate thread
    threading.Thread(target=game_loop, daemon=True).start()

    while True:
        # Accept new connection
        conn, addr = s.accept()
        
        # Get username
        try:
            username = conn.recv(MSG_SIZE).decode("utf8").strip()
            if not username:
                username = f"Player{random.randint(1000, 9999)}"
        except:
            username = f"Player{random.randint(1000, 9999)}"
        
        # Generate ID and create player entry
        new_id = generate_id(game_state.players, MAX_PLAYERS)
        game_state.players[new_id] = {
            "socket": conn,
            "username": username,
            "position": [0, 1, 0],
            "rotation": 0,
            "health": 100
        }
        
        # Send ID to client
        conn.send(json.dumps({
            "id": new_id,
            "object": "welcome",
            "username": username
        }).encode("utf8"))
        
        # Notify other players
        broadcast_state_update()
        
        # Start message handler thread
        threading.Thread(
            target=handle_messages,
            args=(new_id,),
            daemon=True
        ).start()
        
        print(f"New connection from {addr}, ID: {new_id}, Username: {username}")

if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        print("Shutting down server...")
    finally:
        s.close()