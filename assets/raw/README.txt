暖心树洞 - 冥想音频占位说明

==================================================

需要的文件：
1. meditation_sleep_01.mp3 - 5分钟睡前冥想

下载指南：查看 '暖心树洞冥想音频下载指南.md'

临时方案：
在开发期间可以使用静音或简单的白噪音：
# 使用ffmpeg生成5分钟静音
ffmpeg -f lavfi -i anullsrc=r=44100:cl=mono -t 300 meditation_sleep_01.mp3

# 或使用在线白噪音
下载链接：
- Pixabay Audio: https://pixabay.com/music/search/meditation%205%20min/
- Freesound: https://freesound.org/search/?q=meditation
