import cv2
import numpy as np
import argparse
import os

def binarize_image_custom(input_path,output_path,  r, t):
    img = cv2.imread(input_path, cv2.IMREAD_GRAYSCALE)
    if img is None:
        raise FileNotFoundError(f"无法加载图像: {input_path}")

    h,w = img.shape
    for y in range(h):
        for x in range(w):
            mean = local_mean(img, r,x,y)
            if  mean <= t:
                img[y, x] = 0
            else:
                img[y,x] = 255
            # print(f'local mean for {(y,x)}: {mean}')
    cv2.imwrite(output_path, img)
    print(f"已保存二值图像到: {output_path}")

def local_mean(img: np.ndarray, r:int, x:int, y:int):
    h,w = img.shape
    y_min = max(0, y-r)
    y_max = min(h, y+r+1)
    x_min = max(0, x-r)
    x_max = min(w, x + r+1)
    region = img[y_min:y_max, x_min:x_max]
    return np.mean(region)

def main():
    parser = argparse.ArgumentParser(description="对 BMP 图像进行自定义二值化处理")
    parser.add_argument('--input', type=str, required=True, help="输入 BMP 图像路径")
    parser.add_argument('--output', type=str, required=True, help="输出 BMP 图像路径")
    parser.add_argument('--r', type=int, default=1, help="邻域大小（用于平滑处理）")
    parser.add_argument('--t', type=int, default=128, help="固定阈值（你也可以忽略它）")
    args = parser.parse_args()
    binarize_image_custom(args.input, args.output, args.r, args.t)

if __name__ == "__main__":
    main()
