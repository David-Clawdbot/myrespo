package com.nuanshidong

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nuanshidong.config.AppConfig
import com.nuanshidong.ui.components.CloudButton
import com.nuanshidong.ui.components.LargeCloudButton
import com.nuanshidong.ui.components.SmallCloudButton
import com.nuanshidong.ui.components.YarnBallAvatar
import com.nuanshidong.ui.screens.ChatScreen
import com.nuanshidong.ui.screens.MeditationScreen
import com.nuanshidong.ui.theme.BackgroundMorning
import com.nuanshidong.ui.theme.BackgroundNight
import com.nuanshidong.ui.theme.BackgroundNoon
import com.nuanshidong.ui.theme.NuanshidongTheme
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 初始化应用配置
        AppConfig.init()

        setContent {
            NuanshidongTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = getTimeBasedBackground()
                ) {
                    MainScreen()
                }
            }
        }
    }
}

/**
 * 获取基于时间的背景色
 */
private fun getTimeBasedBackground(): Color {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 6..11 -> BackgroundMorning
        in 12..17 -> BackgroundNoon
        in 18..20 -> MaterialTheme.colorScheme.background
        else -> BackgroundNight
    }
}

/**
 * 屏幕类型
 */
sealed class Screen {
    data object Home : Screen()
    data class Chat(val emotion: String) : Screen()
    data class Meditation(val emotion: String) : Screen()
}

@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    var selectedEmotion by remember { mutableStateOf("") }
    var isTalking by remember { mutableStateOf(false) }

    // 根据当前屏幕显示不同内容
    when (currentScreen) {
        is Screen.Home -> {
            HomeScreen(
                selectedEmotion = selectedEmotion,
                onEmotionSelected = { emotion ->
                    selectedEmotion = emotion
                },
                onNavigateToChat = {
                    currentScreen = Screen.Chat(selectedEmotion)
                },
                onNavigateToMeditation = {
                    currentScreen = Screen.Meditation(selectedEmotion)
                }
            )
        }
        is Screen.Chat -> {
            ChatScreen(
                emotion = (currentScreen as Screen.Chat).emotion,
                onBack = {
                    currentScreen = Screen.Home
                }
            )
        }
        is Screen.Meditation -> {
            MeditationScreen(
                emotion = (currentScreen as Screen.Meditation).emotion,
                onBack = {
                    currentScreen = Screen.Home
                }
            )
        }
    }
}

@Composable
fun HomeScreen(
    selectedEmotion: String,
    onEmotionSelected: (String) -> Unit,
    onNavigateToChat: () -> Unit,
    onNavigateToMeditation: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 标题和毛线团头像
        TitleSection()

        Spacer(modifier = Modifier.height(32.dp))

        // 欢迎文字
        Text(
            text = "你好，我是小暖 🧸",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "今天感觉怎么样？",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 情绪选择按钮
        EmotionSelectionGrid(
            onEmotionSelected = onEmotionSelected
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 功能按钮组
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 开始聊天按钮
            LargeCloudButton(
                text = "开始聊天",
                onClick = onNavigateToChat,
                enabled = selectedEmotion.isNotEmpty()
            )

            // 冥想放松按钮
            LargeCloudButton(
                text = "冥想放松",
                onClick = onNavigateToMeditation,
                enabled = selectedEmotion.isNotEmpty()
            )
        }
    }
}

/**
 * 标题区域 - 毛线团头像
 */
@Composable
fun TitleSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 毛线团头像
        YarnBallAvatar(
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // 标题
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "暖心树洞",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

/**
 * 情绪选择网格
 */
@Composable
fun EmotionSelectionGrid(
    onEmotionSelected: (String) -> Unit
) {
    val emotions = listOf(
        "开心" to "😊",
        "平静" to "😌",
        "焦虑" to "😰",
        "难过" to "😢",
        "愤怒" to "😠",
        "困惑" to "😕"
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        emotions.chunked(2).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                row.forEach { (emotion, emoji) ->
                    EmotionButton(
                        emotion = emotion,
                        emoji = emoji,
                        onClick = { onEmotionSelected(emotion) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

/**
 * 情绪按钮
 */
@Composable
fun EmotionButton(
    emotion: String,
    emoji: String,
    onClick: () -> Unit
) {
    SmallCloudButton(
        text = "$emoji $emotion",
        onClick = onClick
    )
}
