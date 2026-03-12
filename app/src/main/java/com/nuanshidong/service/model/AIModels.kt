package com.nuanshidong.service.model

/**
 * AI聊天请求
 */
data class AIRequest(
    /**
     * 消息列表
     */
    val messages: List<Message>,

    /**
     * 系统提示词
     */
    val systemPrompt: String? = null,

    /**
     * 用户情绪（用于个性化回复）
     */
    val emotion: String? = null,

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
 * 消息
 */
data class Message(
    /**
     * 角色: "system", "user", "assistant"
     */
    val role: String,

    /**
     * 消息内容
     */
    val content: String,

    /**
     * 消息时间戳
     */
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * AI聊天响应
 */
data class AIResponse(
    /**
     * 回复内容
     */
    val content: String,

    /**
     * 使用的模型
     */
    val model: String,

    /**
     * Token使用情况
     */
    val usage: Usage? = null,

    /**
     * 是否流式响应
     */
    val isStream: Boolean = false
)

/**
 * Token使用情况
 */
data class Usage(
    val promptTokens: Int,
    val completionTokens: Int,
    val totalTokens: Int
)

/**
 * 聊天历史（用于保存上下文）
 */
data class ChatHistory(
    /**
     * 用户ID或设备ID
     */
    val userId: String,

    /**
     * 聊天会话ID
     */
    val sessionId: String,

    /**
     * 消息列表
     */
    val messages: List<Message>,

    /**
     * 当前情绪
     */
    val currentEmotion: String?,

    /**
     * 创建时间
     */
    val createdAt: Long = System.currentTimeMillis(),

    /**
     * 最后更新时间
     */
    val updatedAt: Long = System.currentTimeMillis()
)
