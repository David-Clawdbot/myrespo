# AI服务配置指南

## 概述

暖心树洞支持接入多种AI服务，提供情绪感知的智能对话功能。

## 支持的AI服务

### 1. 豆包（火山引擎）
- 官网：https://www.volcengine.com/product/ark
- API文档：https://www.volcengine.com/docs/82379/1263482
- 推荐模型：`doubao-pro-32k`
- 特点：中文理解能力强，价格实惠

### 2. 通义千问（阿里云）
- 官网：https://tongyi.aliyun.com/
- API文档：https://help.aliyun.com/zh/dashscope/developer-reference/api-details
- 推荐模型：`qwen-max`（最强）/ `qwen-plus`（性价比）
- 特点：中文模型表现优秀，免费额度

### 3. OpenAI
- 官网：https://openai.com/
- API文档：https://platform.openai.com/docs/api-reference
- 推荐模型：`gpt-3.5-turbo`（性价比）/ `gpt-4`（最强）
- 特点：功能最全面，但需要国外支付

## 配置步骤

### 方法一：修改代码配置

1. 打开文件：`app/src/main/java/com/nuanshidong/config/AIConfig.kt`

2. 修改 `AppConfig.init()` 中的配置：

```kotlin
aiConfig = AIConfig(
    provider = "tongyi", // 或 "doubao", "openai"
    apiKey = "your-api-key-here", // 填入你的API密钥
    temperature = 0.7f,
    maxTokens = 2000
)
```

3. 重新编译应用

### 方法二：使用预设配置（推荐）

在 `AIPresets` 对象中选择一个预设：

```kotlin
// 使用豆包
AppConfig.updateAIConfig(AIPresets.doubao.copy(apiKey = "your-api-key"))

// 使用通义千问
AppConfig.updateAIConfig(AIPresets.tongyi.copy(apiKey = "your-api-key"))

// 使用OpenAI
AppConfig.updateAIConfig(AIPresets.openai.copy(apiKey = "your-api-key"))
```

## 获取API密钥

### 豆包（火山引擎）

1. 访问：https://console.volcengine.com/ark
2. 注册/登录账号
3. 创建API Key
4. 充值（新用户有免费额度）

### 通义千问（阿里云）

1. 访问：https://dashscope.console.aliyun.com/
2. 注册/登录阿里云账号
3. 开通DashScope服务（免费）
4. 创建API Key（免费额度：百万tokens/月）

### OpenAI

1. 访问：https://platform.openai.com/
2. 注册/登录账号
3. 进入 API Keys 页面创建密钥
4. 需要绑定国外信用卡

## 配置参数说明

| 参数 | 类型 | 说明 | 默认值 |
|------|------|------|--------|
| provider | String | AI提供商 | - |
| apiKey | String | API密钥 | - |
| endpoint | String? | 自定义端点（可选） | null |
| model | String? | 模型名称 | 各服务推荐模型 |
| temperature | Float | 温度（0-1），控制创造性 | 0.7 |
| maxTokens | Int | 最大token数 | 2000 |

### 温度参数说明
- **0.1-0.3**: 非常严谨，适合事实问答
- **0.4-0.7**: 平衡，适合日常对话（推荐）
- **0.8-1.0**: 富有创造性，适合创意写作

## 推荐配置

### 追求性价比
```kotlin
AIConfig(
    provider = "tongyi",
    apiKey = "your-key",
    model = "qwen-plus",
    temperature = 0.7f,
    maxTokens = 2000
)
```
- 通义千问plus版本，免费额度高

### 追求质量
```kotlin
AIConfig(
    provider = "doubao",
    apiKey = "your-key",
    model = "doubao-pro-32k",
    temperature = 0.7f,
    maxTokens = 2000
)
```
- 豆包pro版本，中文理解强

### 追求免费
```kotlin
AIConfig(
    provider = "tongyi",
    apiKey = "your-key",
    model = "qwen-turbo",
    temperature = 0.7f,
    maxTokens = 1000
)
```
- 通义千问turbo版本，响应快

## 测试配置

配置完成后，启动应用并测试：

1. 选择任意情绪
2. 点击"开始聊天"
3. 发送测试消息："你好"
4. 观察AI是否正常回复

## 常见问题

### Q: API密钥如何保护？
A: 目前密钥保存在代码中，生产环境应该：
- 使用环境变量
- 使用密钥管理服务（如阿里云KMS）
- 不要在GitHub等公开平台提交密钥

### Q: 如何切换AI服务？
A: 修改配置中的provider和apiKey即可，代码会自动适配

### Q: 如何节省API费用？
A:
1. 使用maxTokens限制回复长度
2. 适当降低temperature减少重试
3. 选择合适的模型（不一定要用最强的）
4. 利用各服务的免费额度

### Q: AI回复延迟高怎么办？
A:
1. 检查网络连接
2. 选择更快的模型（如qwen-turbo）
3. 减少maxTokens
4. 考虑实现流式响应

## 后续优化计划

- [ ] 添加设置界面，让用户在APP内配置
- [ ] 支持多个AI服务切换
- [ ] 实现流式响应（打字机效果）
- [ ] 添加使用统计和成本追踪
- [ ] 支持本地缓存减少API调用

---

**文档版本**: v1.0
**更新时间**: 2026-03-12
