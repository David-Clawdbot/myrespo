暖心树洞 - 毛线团素材占位说明

==================================================

需要的文件：
1. yarn_normal.png - 静止状态（512x512px或更大）
2. yarn_talking.png - 说话状态（512x512px或更大）

下载指南：查看 '暖心树洞毛线团素材下载指南.md'

临时方案：
在开发期间可以使用简单的占位图：
- yarn_normal.png: 粉红圆形 (512x512px)
- yarn_talking.png: 粉红圆形 + 小点 (512x512px)

生成命令（需要ImageMagick）：
# 静止图
convert -size 512x512 xc:#E8C4C4 -fill black -draw 'circle 256,256 256,256' yarn_normal.png

# 说话图
convert -size 512x512 xc:#E8C4C4 -fill black -draw 'circle 256,256 256,256' -fill white -draw 'circle 256,200 30,30' yarn_talking.png
