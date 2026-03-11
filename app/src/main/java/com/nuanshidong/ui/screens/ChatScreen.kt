package com.nuanshidong.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nuanshidong.ui.components.YarnBallAvatar
import com.nuanshidong.ui.theme.*

/**
 * 聊天界面
 * @param emotion 用户选择的情绪
 * @param onBack 返回回调
 */
@Composable
fun ChatScreen(
    emotion: String,
    onBack: () -> Unit
) {
    var messageText by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf<List<ChatMessage>>(emptyList()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // 顶部导航
        ChatTopBar(
            emotion = emotion,
            onBack = onBack
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 消息列表
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // 初始欢迎消息
            if (messages.isEmpty()) {
                item {
                    ChatBubble(
                        message = ChatMessage(
                            sender = "ai",
                            text = getWelcomeMessage(emotion)
                        )
                    )
                }
            }

            // 用户和AI消息
            items(messages) { message ->
                ChatBubble(message = message)
            }
        }

        // 输入框
        ChatInputField(
            value = messageText,
            onValueChange = { messageText = it },
            onSend = {
                if (messageText.isNotBlank()) {
                    // 用户消息
                    messages = messages + ChatMessage(sender = "user", text = messageText)

                    // AI回复（占位）
                    val aiResponse = getAIResponse(emotion, messageText)
                    messages = messages + ChatMessage(sender = "ai", text = aiResponse)

                    messageText = ""
                }
            }
        )
    }
}

/**
 * 聊天顶部栏
 */
@Composable
private fun ChatTopBar(
    emotion: String,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = onBack) {
            Text("← 返回", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = "与小暖聊天",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "当前情绪: $emotion",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        }
    }
}

/**
 * 聊天气泡
 */
@Composable
private fun ChatBubble(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.sender == "user") {
            Arrangement.End
        } else {
            Arrangement.Start
        }
    ) {
        if (message.sender == "ai") {
            YarnBallAvatar(
                modifier = Modifier.size(40.dp),
                isTalking = true
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Box(
            modifier = Modifier
                .background(
                    color = if (message.sender == "user") {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surface
                    },
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
                .widthIn(max = 280.dp)
        ) {
            Text(
                text = message.text,
                color = if (message.sender == "user") {
                    Color.White
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
                fontSize = 15.sp
            )
        }

        if (message.sender == "user") {
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

/**
 * 聊天输入框
 */
@Composable
private fun ChatInputField(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            placeholder = { Text("说出你的想法...", fontSize = 14.sp) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = onSend,
            enabled = value.isNotBlank(),
            modifier = Modifier.size(40.dp)
        ) {
            Text("发送", fontSize = 12.sp)
        }
    }
}

/**
 * 欢迎消息
 */
private fun getWelcomeMessage(emotion: String): String {
    return when (emotion) {
        "开心" -> "太好了！今天有什么开心的事情想分享吗？😊"
        "平静" -> "平静的状态很珍贵。今天有什么想聊聊的吗？😌"
        "焦虑" -> "没关系，焦虑是正常的。和我说说，我在这里听你说。🤗"
        "难过" -> "抱抱你。不用假装坚强，说出来会好受一些的。💙"
        "愤怒" -> "生气也没关系。我们一起找到释放的方法。🌿"
        "困惑" -> "困惑是成长的开始。慢慢说，我们一起梳理。🤔"
        else -> "你好，我是小暖。今天感觉怎么样？"
    }
}

/**
 * AI占位回复（后续接入真实AI）
 */
private fun getAIResponse(emotion: String, userMessage: String): String {
    // 这里是占位逻辑，后续会接入真实AI服务
    val responses = listOf(
        "我理解你的感受。能多说说吗？",
        "听起来这对你很重要。",
        "我在认真听你说。继续。",
        "这种感觉确实不容易。",
        "谢谢你愿意和我分享这些。",
        "你的感受是合理的。"
    )
    return responses.random()
}

/**
 * 聊天消息数据类
 */
data class ChatMessage(
    val sender: String, // "user" 或 "ai"
    val text: String
)
