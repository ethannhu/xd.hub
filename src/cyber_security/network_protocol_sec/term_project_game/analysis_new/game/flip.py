from PIL import Image
import os

# 确保 'assets' 文件夹存在
assets_dir = 'game/assets'
if not os.path.exists(assets_dir):
    os.makedirs(assets_dir)

# 打开图片
image_path = os.path.join(assets_dir, "xdu.jpeg")
image = Image.open(image_path)

# 水平翻转图片
image_flipped = image.transpose(Image.FLIP_LEFT_RIGHT)

# 保存反转后的图片
flipped_image_path = os.path.join(assets_dir, 'xdu_flipped.jpeg')
image_flipped.save(flipped_image_path)

print(f"图片已保存为：{flipped_image_path}")
