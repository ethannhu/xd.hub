import os
import sys
import socket
import threading
import json
import ursina
from network import Network

from floor import Floor
from map import Map
from player import Player
from enemy import Enemy
from bullet import Bullet

# === 客户端预测用到的全局变量 ===
predicted_states = {}  # tick -> {"position": Vec3, "rotation": float}
input_history = {}     # tick -> {"move": Vec3, "rot": float}
confirmed_tick = 0     # 上一次服务器确认tick
current_tick = 0       # 客户端tick编号

# === 退出菜单类 ===
class ExitMenu:
    def __init__(self):
        self.menu_bg = ursina.Entity(
            parent=ursina.camera.ui,
            model='quad',
            color=ursina.color.black66,
            scale=(0.6, 0.7),
            position=(0, 0),
            z=-1
        )

        self.title = ursina.Text(
            parent=ursina.camera.ui,
            text='Pause Menu',
            scale=2,
            position=(0, 0.2),
            origin=(0, 0)
        )

        self.exit_button = ursina.Button(
            parent=ursina.camera.ui,
            text='Quit Game',
            color=ursina.color.red,
            scale=(0.3, 0.1),
            position=(0, -0.1),
            on_click=self.quit_game
        )

        self.continue_button = ursina.Button(
            parent=ursina.camera.ui,
            text='Continue',
            color=ursina.color.green,
            scale=(0.3, 0.1),
            position=(0, 0.1),
            on_click=self.continue_game
        )

        self.visible = False
        self.set_visibility(False)

    def set_visibility(self, visible):
        self.visible = visible
        self.menu_bg.enabled = visible
        self.title.enabled = visible
        self.exit_button.enabled = visible
        self.continue_button.enabled = visible

    def toggle(self):
        self.set_visibility(not self.visible)
        ursina.mouse.locked = not self.visible

    def quit_game(self):
        sys.exit()

    def continue_game(self):
        self.toggle()


username = input("Enter your username: ")

while True:
    server_addr = input("Enter server IP: ")
    server_port = input("Enter server port: ")

    try:
        server_port = int(server_port)
    except ValueError:
        print("\nThe port you entered was not a number, try again with a valid port...")
        continue

    n = Network(server_addr, server_port, username)
    n.settimeout(5)

    error_occurred = False

    try:
        n.connect()
    except ConnectionRefusedError:
        print("\nConnection refused! This can be because server hasn't started or has reached it's player limit.")
        error_occurred = True
    except socket.timeout:
        print("\nServer took too long to respond, please try again...")
        error_occurred = True
    except socket.gaierror:
        print("\nThe IP address you entered is invalid, please try again with a valid address...")
        error_occurred = True
    finally:
        n.settimeout(None)

    if not error_occurred:
        break

app = ursina.Ursina()
ursina.window.borderless = False
ursina.window.title = "XDU FPS"
ursina.window.exit_button.visible = False

exit_menu = ExitMenu()

floor = Floor()
map = Map()

sky = ursina.Entity(
    model="sphere",
    texture=os.path.join("assets", "xdu_flipped.jpeg"),
    scale=9999,
    double_sided=True
)

player = Player(ursina.Vec3(0, 1, 0))
enemies = []

def receive():
    global confirmed_tick, current_tick
    while True:
        try:
            info = n.receive_info()
        except Exception as e:
            print(e)
            continue

        if not info:
            print("Server has stopped! Exiting...")
            sys.exit()

        if info["object"] == "player":
            enemy_id = info["id"]

            if info.get("joined"):
                new_enemy = Enemy(ursina.Vec3(*info["position"]), enemy_id, info["username"])
                new_enemy.health = info["health"]
                enemies.append(new_enemy)
                continue

            if enemy_id == n.id:
                # === 客户端预测：接收到服务器确认状态 ===
                server_tick = info.get("tick", 0)
                server_pos = ursina.Vec3(*info["position"])
                server_rot = info["rotation"]

                predicted = predicted_states.get(server_tick)
                if predicted:
                    delta_pos = (predicted["position"] - server_pos).length()
                    delta_rot = abs(predicted["rotation"] - server_rot)

                    if delta_pos > 0.1 or delta_rot > 5:
                        print(f"⚠️ 修正预测误差 at tick {server_tick} → rollback")
                        player.position = server_pos
                        player.rotation_y = server_rot

                        tick = server_tick + 1
                        while tick < current_tick:
                            cmd = input_history.get(tick)
                            if cmd:
                                move = cmd["move"]
                                player.position += player.forward * move.z * 0.1
                                player.position += player.right * move.x * 0.1
                                player.rotation_y = cmd["rot"]
                            tick += 1

                confirmed_tick = server_tick
                continue

            # 非本地敌人处理
            enemy = next((e for e in enemies if e.id == enemy_id), None)
            if not enemy:
                continue

            if info.get("left"):
                enemies.remove(enemy)
                ursina.destroy(enemy)
                continue

            enemy.world_position = ursina.Vec3(*info["position"])
            enemy.rotation_y = info["rotation"]

        elif info["object"] == "bullet":
            b_pos = ursina.Vec3(*info["position"])
            b_dir = info["direction"]
            b_x_dir = info["x_direction"]
            b_damage = info["damage"]
            new_bullet = Bullet(b_pos, b_dir, b_x_dir, n, b_damage, slave=True)
            ursina.destroy(new_bullet, delay=2)

        elif info["object"] == "health_update":
            enemy_id = info["id"]
            target = player if enemy_id == n.id else next((e for e in enemies if e.id == enemy_id), None)
            if target:
                target.health = info["health"]


def update():
    global current_tick
    if player.health <= 0 or exit_menu.visible:
        return

    # 1. 采集输入
    move_input = ursina.Vec3(0, 0, 0)
    if ursina.held_keys["w"]: move_input.z += 1
    if ursina.held_keys["s"]: move_input.z -= 1
    if ursina.held_keys["a"]: move_input.x -= 1
    if ursina.held_keys["d"]: move_input.x += 1
    rot_y = player.rotation_y

    # 2. 应用预测
    player.position += player.forward * move_input.z * 0.1
    player.position += player.right * move_input.x * 0.1

    # 3. 保存输入历史 & 预测状态
    input_history[current_tick] = {"move": move_input, "rot": rot_y}
    predicted_states[current_tick] = {"position": player.world_position, "rotation": player.rotation_y}

    # 4. 发送指令
    n.send_player({
        "object": "player",
        "position": player.world_position,
        "rotation": player.rotation_y,
        "health": player.health,
        "client_tick": current_tick
    })

    current_tick += 1


def input(key):
    if key == "left mouse down" and player.health > 0 and not exit_menu.visible:
        b_pos = player.position + ursina.Vec3(0, 2, 0)
        bullet = Bullet(b_pos, player.world_rotation_y, -player.camera_pivot.world_rotation_x, n)
        n.send_bullet(bullet)
        ursina.destroy(bullet, delay=2)
    if key == "escape":
        exit_menu.toggle()


def main():
    threading.Thread(target=receive, daemon=True).start()
    app.run()


if __name__ == "__main__":
    main()