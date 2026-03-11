package com.nuanshidong.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.AnimationVectorConverter
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.nuanshidong.ui.theme.Primary
import kotlin.math.sin

/**
 * 毛线团"小暖"头像组件
 * 占位图版本 - 使用Compose绘制
 * @param isTalking 是否在说话状态
 * @param modifier 修饰符
 */
@Composable
fun YarnBallAvatar(
    isTalking: Boolean = false,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "yarn_ball")

    // 说话时的呼吸效果（不是旋转）
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val scaleValue = if (isTalking) scale else 1f

    // 毛线团身体 - 柔粉色圆形
    androidx.compose.foundation.layout.Box(
        modifier = modifier
            .size(80.dp)
            .graphicsLayer {
                this.scaleX = scaleValue
                this.scaleY = scaleValue
            }
            .clip(CircleShape)
            .background(Primary.copy(alpha = 0.9f)),
        contentAlignment = Alignment.Center
    ) {
        // 简单的面部特征
        YarnBallFace(
            isTalking = isTalking
        )
    }
}

/**
 * 毛线团面部特征
 */
@Composable
private fun YarnBallFace(
    isTalking: Boolean = false
) {
    // 眼睛
    androidx.compose.foundation.layout.Box(
        modifier = Modifier.size(60.dp),
        contentAlignment = Alignment.Center
    ) {
        // 左眼
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .size(12.dp)
                .graphicsLayer {
                    translationX = -15.dp.toPx()
                    translationY = -8.dp.toPx()
                }
                .clip(CircleShape)
                .background(Color(0xFF5C3C3C))
        )

        // 右眼
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .size(12.dp)
                .graphicsLayer {
                    translationX = 15.dp.toPx()
                    translationY = -8.dp.toPx()
                }
                .clip(CircleShape)
                .background(Color(0xFF5C3C3C))
        )

        // 嘴巴
        if (isTalking) {
            // 说话状态 - 小圆圈
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .size(16.dp)
                    .graphicsLayer {
                        translationY = 15.dp.toPx()
                    }
                    .clip(CircleShape)
                    .background(Color(0xFFFFFFFF.copy(alpha = 0.8f))
            )
        } else {
            // 静止状态 - 弧形微笑
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .size(20.dp)
                    .graphicsLayer {
                        translationY = 12.dp.toPx()
                    }
                    .clip(CircleShape)
                    .background(Color(0xFFFFFFFF.copy(alpha = 0.3f))
            )
        }
    }
}

/**
 * 小型毛线团头像
 * 用于对话列表或侧边栏
 */
@Composable
fun SmallYarnBallAvatar(
    modifier: Modifier = Modifier
) {
    androidx.compose.foundation.layout.Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Primary.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier.size(8.dp)
            .clip(CircleShape)
            .background(Color(0xFF5C3C3C))
        )
    }
}
