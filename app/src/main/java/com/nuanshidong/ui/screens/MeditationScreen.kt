package com.nuanshidong.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nuanshidong.ui.theme.*
import kotlinx.coroutines.delay

/**
 * 冥想界面
 * @param emotion 用户选择的情绪
 * @param onBack 返回回调
 */
@Composable
fun MeditationScreen(
    emotion: String,
    onBack: () -> Unit
) {
    var isPlaying by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) } // 0-1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 顶部导航
        MeditationTopBar(
            emotion = emotion,
            onBack = onBack
        )

        Spacer(modifier = Modifier.height(48.dp))

        // 冥想指导文字
        MeditationGuide(
            emotion = emotion,
            isPlaying = isPlaying
        )

        Spacer(modifier = Modifier.height(64.dp))

        // 呼吸动画圆圈
        BreathingCircle(
            isPlaying = isPlaying,
            progress = progress
        )

        Spacer(modifier = Modifier.height(64.dp))

        // 控制按钮
        MeditationControls(
            isPlaying = isPlaying,
            onTogglePlay = {
                isPlaying = !isPlaying
                // TODO: 播放/暂停冥想音频
            },
            progress = progress,
            onProgressChange = { progress = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 提示文字
        Text(
            text = getMeditationTip(emotion),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

/**
 * 冥想顶部栏
 */
@Composable
private fun MeditationTopBar(
    emotion: String,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = onBack) {
            Text("← 返回", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "冥想放松",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

/**
 * 冥想指导文字
 */
@Composable
private fun MeditationGuide(
    emotion: String,
    isPlaying: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = if (isPlaying) "跟着呼吸..." else "准备好开始了吗？",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "深度放松 5 分钟",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
    }
}

/**
 * 呼吸动画圆圈
 */
@Composable
private fun BreathingCircle(
    isPlaying: Boolean,
    progress: Float
) {
    val infiniteTransition = rememberInfiniteTransition(label = "breathing")

    // 呼吸动画：放大缩小
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    // 当前缩放值
    val currentScale = if (isPlaying) scale else 1.0f

    // 颜色渐变
    val color = if (isPlaying) {
        Primary.copy(alpha = 0.6f)
    } else {
        Primary.copy(alpha = 0.3f)
    }

    Box(
        modifier = Modifier.size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        // 外圈光晕
        Box(
            modifier = Modifier
                .size(200.dp * currentScale)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.2f))
        )

        // 中圈
        Box(
            modifier = Modifier
                .size(150.dp * currentScale)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.4f))
        )

        // 内圈
        Box(
            modifier = Modifier
                .size(100.dp * currentScale)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.6f))
        )

        // 中心文字
        if (isPlaying) {
            Text(
                text = "呼吸",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

/**
 * 冥想控制按钮
 */
@Composable
private fun MeditationControls(
    isPlaying: Boolean,
    onTogglePlay: () -> Unit,
    progress: Float,
    onProgressChange: (Float) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // 播放/暂停按钮
        Button(
            onClick = onTogglePlay,
            modifier = Modifier.size(80.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = if (isPlaying) "||" else "▶",
                fontSize = 32.sp,
                color = Color.White
            )
        }

        // 进度条
        Slider(
            value = progress,
            onValueChange = onProgressChange,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(8.dp),
            colors = SliderDefaults.colors(
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            )
        )

        // 时间显示
        Text(
            text = "${formatTime(progress)} / 5:00",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
    }
}

/**
 * 获取冥想提示
 */
private fun getMeditationTip(emotion: String): String {
    return when (emotion) {
        "开心" -> "享受这份喜悦，让它在你的心中流淌。"
        "平静" -> "保持这份宁静，让思绪慢慢沉淀。"
        "焦虑" -> "深呼吸，吸入平静，呼出焦虑。"
        "难过" -> "允许自己感受悲伤，慢慢释放它。"
        "愤怒" -> "放下紧绷，让情绪慢慢平复。"
        "困惑" -> "放下思考，让心回归当下。"
        else -> "放松身心，享受这一刻的宁静。"
    }
}

/**
 * 格式化时间
 */
private fun formatTime(progress: Float): String {
    val totalSeconds = (progress * 300).toInt() // 5分钟 = 300秒
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "$minutes:${String.format("%02d", seconds)}"
}
