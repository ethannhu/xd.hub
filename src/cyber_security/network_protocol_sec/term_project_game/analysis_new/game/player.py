import ursina
from ursina.prefabs.first_person_controller import FirstPersonController

class Player(FirstPersonController):
    def __init__(self, position: ursina.Vec3):
        super().__init__(
            position=position,
            model="cube",
            jump_height=2.5,
            jump_duration=0.4,
            origin_y=-2,
            collider="box",
            speed=7
        )
        self.cursor.color = ursina.color.rgba(255, 0, 0, 122)

        self.health = 100  # 先定义 health 属性
        self.death_message_shown = False

        self.gun = ursina.Entity(
            parent=ursina.camera.ui,
            position=ursina.Vec2(0.6, -0.45),
            scale=ursina.Vec3(0.1, 0.2, 0.65),
            rotation=ursina.Vec3(-20, -20, -5),
            model="cube",
            texture="white_cube",
            color=ursina.color.color(0, 0, 0.4)
        )

        # 血条设置
        self.healthbar_pos = ursina.Vec2(0, 0.45)
        self.healthbar_size = ursina.Vec2(0.8, 0.04)
        
        # 血条背景(红色)
        self.healthbar_bg = ursina.Entity(
            parent=ursina.camera.ui,
            model="quad",
            color=ursina.color.rgb(255, 0, 0),
            position=self.healthbar_pos,
            scale=self.healthbar_size
        )
        
        # 当前血量条(绿色)
        self.healthbar = ursina.Entity(
            parent=ursina.camera.ui,
            model="quad",
            color=ursina.color.rgb(0, 255, 0),
            position=self.healthbar_pos,
            scale=self.healthbar_size
        )
        
        # 血量数值显示
        self.health_text = ursina.Text(
            parent=ursina.camera.ui,
            text=f"{self.health}/100",  # 现在可以安全访问 self.health 了
            position=(self.healthbar_pos.x + self.healthbar_size.x/2 + 0.05, self.healthbar_pos.y+ 0.01),
            scale=1.5,
            color=ursina.color.white
        )

    def death(self):
        self.death_message_shown = True

        ursina.destroy(self.gun)
        self.rotation = 0
        self.camera_pivot.world_rotation_x = -45
        self.world_position = ursina.Vec3(0, 7, -35)
        self.cursor.color = ursina.color.rgb(0, 0, 0, a=0)

        ursina.Text(
            text="You are dead!",
            origin=ursina.Vec2(0, 0),
            scale=3
        )

    def update(self):
        # 更新血条长度（固定右侧，左侧向右收缩）
        health_percentage = self.health / 100
        self.healthbar.scale_x = health_percentage * self.healthbar_size.x
        # 调整血条位置，使其右侧保持固定
        self.healthbar.x = self.healthbar_pos.x - (1 - health_percentage) * self.healthbar_size.x / 2
        
        # 更新血量数值显示
        self.health_text.text = f"{int(self.health)}/100"
        
        # 根据血量改变颜色(可选)
        if self.health > 60:
            self.health_text.color = ursina.color.green
            self.healthbar.color = ursina.color.green
        elif self.health > 30:
            self.health_text.color = ursina.color.yellow
            self.healthbar.color = ursina.color.yellow
        else:
            self.health_text.color = ursina.color.red
            self.healthbar.color = ursina.color.red

        if self.health <= 0:
            if not self.death_message_shown:
                self.death()
        else:
            super().update()