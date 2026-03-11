# 暖心树洞 🧸

一款基于 Android 的心理健康陪伴应用，提供情绪倾诉、AI 对话和冥想放松功能。

## ✨ 功能特性

### 🎯 核心功能
- **情绪选择** - 6种情绪状态（开心、平静、焦虑、难过、愤怒、困惑）
- **AI 对话** - 基于情绪的个性化陪伴对话
- **冥想放松** - 呼吸动画 + 5分钟冥想引导
- **时间主题** - 根据时段自动切换背景色（早晨、中午、傍晚、夜晚）

### 🎨 设计特点
- **莫兰迪色系** - 柔和舒缓的色彩搭配
- **云朵按钮** - 自定义圆角按钮设计
- **毛线团形象** - 可爱的"小暖"角色
- **流畅动画** - 呼吸效果、说话动画

## 🚀 快速开始

### 环境要求
- Android Studio Hedgehog (2023.1.1) 或更高版本
- JDK 17 或更高版本
- Android SDK API 24 (Android 7.0) 或更高
- Gradle 8.0 或更高版本

### 运行项目

1. **克隆仓库**
```bash
git clone https://github.com/your-username/nuanshidong.git
cd nuanshidong
```

2. **打开项目**
- 用 Android Studio 打开项目文件夹

3. **同步 Gradle**
- Android Studio 会自动提示同步 Gradle，点击 "Sync Now"

4. **运行应用**
- 连接 Android 设备或启动模拟器
- 点击运行按钮或使用快捷键 `Shift + F10` (Mac: `Control + R`)

## 📦 项目结构

```
app/src/main/java/com/nuanshidong/
├── MainActivity.kt                 # 主界面
├── ui/
│   ├── components/
│   │   ├── YarnBallAvatar.kt      # 毛线团头像
│   │   ├── CloudButton.kt         # 云朵按钮
│   │   └── CloudShape.kt          # 云朵形状
│   ├── screens/
│   │   ├── ChatScreen.kt          # 聊天界面
│   │   └── MeditationScreen.kt    # 冥想界面
│   └── theme/
│       ├── Color.kt               # 莫兰迪色系
│       ├── Theme.kt               # 主题配置
│       └── Type.kt                # 字体样式
└── res/
    └── values/
        ├── colors.xml
        ├── strings.xml
        └── themes.xml
```

## 🎨 主题色彩

使用莫兰迪色系（Morandi Colors）：
- `SoftPink` (#E8C4C4) - 柔粉色 - 主色
- `DustyBlue` (#9BB7D4) - 灰蓝色 - 次要色
- `SageGreen` (#9DB4B0) - 鼠尾草绿 - 辅助色
- `Cream` (#F5E6D3) - 奶油色 - 背景色
- `BackgroundMorning` (#FFF5E6) - 晨光背景
- `BackgroundNoon` (#F0F4F8) - 午后背景
- `BackgroundNight` (#EBF0F5) - 夜晚背景

## 🛠️ 技术栈

- **语言**: Kotlin
- **UI 框架**: Jetpack Compose (Material 3)
- **架构**: MVVM（准备中）
- **最低 SDK**: API 24 (Android 7.0)
- **目标 SDK**: API 34 (Android 14)

## 📝 开发计划

### v0.1 (当前版本)
- ✅ 基础 UI 界面
- ✅ 情绪选择功能
- ✅ 聊天界面框架
- ✅ 冥想界面框架
- ⏳ 占位图素材

### v0.2 (计划中)
- [ ] 接入真实 AI 服务
- [ ] 冥想音频播放
- [ ] 数据持久化
- [ ] 用户偏好设置

### v0.3 (未来)
- [ ] 云端数据同步
- [ ] 每日情绪追踪
- [ ] 冥想记录
- [ ] 社区功能

## 🤝 贡献指南

欢迎贡献！请遵循以下步骤：

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 🙏 致谢

- [Jetpack Compose](https://developer.android.com/jetpack/compose) - 现代化 UI 框架
- [Material Design 3](https://m3.material.io/) - 设计规范
- [莫兰迪色系](https://colorhunt.co/palette/284763) - 灵感来源

---

**开发者**: David
**版本**: v0.1.0
**最后更新**: 2026-03-11

如有问题或建议，欢迎提交 Issue 或 Pull Request！ 💙
