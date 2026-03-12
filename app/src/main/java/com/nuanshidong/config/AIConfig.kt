package com.nuanshidong.config

/**
 * AI服务配置
 */
data class AIConfig(
    /**
     * AI提供商
     * - "doubao": 豆包（火山引擎）
     * - "tongyi": 通义千问（阿里云）
     * - "openai": OpenAI
     */
    val provider: String,

    /**
     * API密钥
     */
    val apiKey: String,

    /**
     * API端点（可选，默认使用各服务的官方端点）
     */
    val endpoint: String? = null,

    /**
     * 模型名称（可选，默认使用各服务的推荐模型）
     */
    val model: String? = null,

    /**
     * 温度（0-1，控制回复的创造性）
     */
    val temperature: Float = 0.7f,

    /**
     * 最大token数
     */
    val maxTokens: Int = 2000
)

/**
 * 应用配置管理
 */
object AppConfig {

    /**
     * AI服务配置
     */
    var aiConfig: AIConfig? = null
        private set

    /**
     * 初始化配置
     * TODO: 从SharedPreferences或远程配置加载
     */
    fun init() {
        // 示例配置（实际应该从配置文件或SharedPreferences加载）
        aiConfig = AIConfig(
            provider = "doubao", // 或 "tongyi", "openai"
            apiKey = "", // TODO: 用户需要在这里填入真实的API密钥
            temperature = 0.7f,
            maxTokens = 2000
        )
    }

    /**
     * 更新AI配置
     */
    fun updateAIConfig(config: AIConfig) {
        aiConfig = config
    }

    /**
     * 获取AI配置
     */
    fun getAIConfig(): AIConfig {
        if (aiConfig == null) {
            init()
        }
        return aiConfig!!
    }
}

/**
 * 预设的AI配置模板
 */
object AIPresets {

    /**
     * 豆包配置
     */
    val doubao = AIConfig(
        provider = "doubao",
        apiKey = "", // 用户需要填写
        model = "doubao-pro-32k",
        temperature = 0.7f,
        maxTokens = 2000
    )

    /**
     * 通义千问配置
     */
    val tongyi = AIConfig(
        provider = "tongyi",
        apiKey = "", // 用户需要填写
        model = "qwen-max",
        temperature = 0.7f,
        maxTokens = 2000
    )

    /**
     * OpenAI配置
     */
    val openai = AIConfig(
        provider = "openai",
        apiKey = "", // 用户需要填写
        model = "gpt-3.5-turbo",
        temperature = 0.7f,
        maxTokens = 2000
    )
}
