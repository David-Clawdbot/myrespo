package com.nuanshidong.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nuanshidong.service.AIService
import com.nuanshidong.service.AIServiceFactory
import com.nuanshidong.service.model.ChatMessage as ServiceMessage
import com.nuanshidong.service.model.ChatHistory
import com.nuanshidong.service.model.AIRequest
import com.nuanshidong.service.model.AIResponse
import com.nuanshidong.service.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 聊天界面ViewModel
 */
class ChatViewModel(
    private val aiService: AIService,
    private val emotion: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    /**
     * 发送用户消息
     */
    fun sendMessage(userMessage: String) {
        viewModelScope.launch {
            // 添加用户消息
            val userMsg = ChatMessage(
                sender = "user",
                text = userMessage,
                timestamp = System.currentTimeMillis()
            )
            _uiState.value = _uiState.value.copy(
                messages = _uiState.value.messages + userMsg,
                isLoading = true
            )

            // 构建AI请求
            val request = AIRequest(
                messages = buildMessageList(userMessage),
                systemPrompt = getSystemPrompt(emotion),
                emotion = emotion,
                temperature = 0.7f
            )

            try {
                // 调用AI服务
                val response = aiService.chat(request)

                // 添加AI回复
                val aiMsg = ChatMessage(
                    sender = "ai",
                    text = response.content,
                    timestamp = System.currentTimeMillis()
                )
                _uiState.value = _uiState.value.copy(
                    messages = _uiState.value.messages + aiMsg,
                    isLoading = false
                )

                // 保存到历史记录（TODO: 实现持久化）
                saveToHistory(userMsg, aiMsg)

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    /**
     * 清除错误信息
     */
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    /**
     * 构建消息列表（包括系统提示）
     */
    private fun buildMessageList(userMessage: String): List<Message> {
        val messages = mutableListOf<Message>()

        // 添加历史消息（限制最近的10条）
        val historyMessages = _uiState.value.messages
            .takeLast(10)
            .map { msg ->
                Message(
                    role = if (msg.sender == "user") "user" else "assistant",
                    content = msg.text,
                    timestamp = msg.timestamp
                )
            }
        messages.addAll(historyMessages)

        // 添加当前用户消息
        messages.add(
            Message(
                role = "user",
                content = userMessage,
                timestamp = System.currentTimeMillis()
            )
        )

        return messages
    }

    /**
     * 获取系统提示词（基于情绪）
     */
    private fun getSystemPrompt(emotion: String): String {
        return """你是一个温暖、有同理心的心理健康陪伴助手，名字叫"小暖"。

当前用户情绪：$emotion

你的角色定位：
- 倾听者：耐心倾听用户的想法和感受
- 支持者：给予情感支持和鼓励
- 引导者：适当引导用户思考和表达

回复风格：
- 温暖亲切，使用柔和的语言
- 有同理心，理解用户的感受
- 不评判，保持中立和支持
- 适当使用emoji表情，营造轻松氛围
- 回复简洁明了，避免过于冗长

注意事项：
- 不要给出医学诊断或专业心理治疗建议
- 如果用户情绪极度低落或有自残倾向，建议寻求专业帮助
- 保护用户隐私，不记录敏感信息
- 保持积极但不盲目乐观的态度

现在，开始你的回复吧。"""
    }

    /**
     * 保存到历史记录（TODO: 使用Room Database实现持久化）
     */
    private fun saveToHistory(userMsg: ChatMessage, aiMsg: ChatMessage) {
        // TODO: 实现持久化存储
    }
}

/**
 * 聊天UI状态
 */
data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * 聊天消息（UI层使用）
 */
data class ChatMessage(
    val sender: String, // "user" 或 "ai"
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * 聊天ViewModel工厂
 */
class ChatViewModelFactory(
    private val aiProvider: String,
    private val apiKey: String,
    private val emotion: String
) {
    fun create(): ChatViewModel {
        val aiService = AIServiceFactory.create(aiProvider, apiKey)
        return ChatViewModel(aiService, emotion)
    }
}
