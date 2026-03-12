package com.nuanshidong.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nuanshidong.ui.components.YarnBallAvatar
import com.nuanshidong.ui.theme.*
import com.nuanshidong.viewmodel.ChatViewModel
import com.nuanshidong.viewmodel.ChatViewModelFactory
import kotlinx.coroutines.launch

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
    // TODO: 从配置或环境变量获取AI配置
    val aiProvider = "doubao" // 或 "tongyi", "openai"
    val apiKey = "" // TODO: 从配置文件读取

    val viewModel: ChatViewModel = remember {
        ChatViewModelFactory(aiProvider, apiKey, emotion).create()
    }

    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var messageText by remember { mutableStateOf("") }

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
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // 初始欢迎消息
            if (uiState.messages.isEmpty()) {
                item {
                    ChatBubble(
                        message = com.nuanshidong.viewmodel.ChatMessage(
                            sender = "ai",
                            text = getWelcomeMessage(emotion)
                        )
                    )
                }
            }

            // 用户和AI消息
            items(uiState.messages) { message ->
                ChatBubble(message = message)
            }

            // 加载指示器
            if (uiState.isLoading) {
                item {
                    TypingIndicator()
                }
            }
        }

        // 自动滚动到底部
        LaunchedEffect(uiState.messages.size, uiState.isLoading) {
            listState.animateScrollToItem(listState.layoutInfo.totalItemsCount)
        }

        // 输入框
        ChatInputField(
            value = messageText,
            onValueChange = { messageText = it },
            onSend = {
                if (messageText.isNotBlank()) {
                    viewModel.sendMessage(messageText)
                    messageText = ""
                }
            }
        )

        // 错误提示
        if (uiState.error != null) {
            ErrorBanner(
                message = uiState.error ?: "发生错误",
                onDismiss = { viewModel.clearError() }
            )
        }
    }
}

/**
 * 打字指示器
 */
@Composable
private fun TypingIndicator() {
    Row(
        modifier = Modifier.padding(start = 48.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0..2) {
            val delay = i * 100
            val alpha by rememberInfiniteTransition(label = "typing")
                .animateFloat(
                    initialValue = 0.3f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(600, delayMillis = delay),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "alpha$delay"
                )
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = alpha),
                        shape = RoundedCornerShape(4.dp)
                    )
            )
        }
    }
}

/**
 * 错误横幅
 */
@Composable
private fun ErrorBanner(
    message: String,
    onDismiss: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onErrorContainer,
                style = MaterialTheme.typography.bodySmall
            )
            TextButton(onClick = onDismiss) {
                Text("关闭", fontSize = 12.sp)
            }
        )
    }
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
private fun ChatBubble(message: com.nuanshidong.viewmodel.ChatMessage) {
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

// 删除旧的ChatMessage数据类和getAIResponse函数，现在使用ViewModel中的实现
