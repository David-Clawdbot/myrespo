package com.nuanshidong.ui.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 云朵按钮组件
 * @param text 按钮文字
 * @param onClick 点击回调
 * @param modifier 修饰符
 * @param enabled 是否启用
 */
@Composable
fun CloudButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.primary
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                enabled = enabled,
                onClick = onClick
            )
            .shadow(
                elevation = if (enabled) 4.dp else 2.dp,
                shape = SimpleCloudShape(cornerRadius = 24.dp),
                spotColor = if (enabled) {
                    backgroundColor.copy(alpha = 0.4f)
                } else {
                    Color.Transparent
                }
            )
            .background(
                color = if (enabled) {
                    backgroundColor
                } else {
                    backgroundColor.copy(alpha = 0.5f)
                },
                shape = SimpleCloudShape(24.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier.padding(
                PaddingValues(
                    horizontal = 32.dp,
                    vertical = 16.dp
                )
            ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = if (enabled) Color.White else Color.White.copy(alpha = 0.6f),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * 大型云朵按钮
 * 用于主要操作
 */
@Composable
fun LargeCloudButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    CloudButton(
        text = text,
        onClick = onClick,
        modifier = modifier.padding(
            PaddingValues(
                horizontal = 24.dp,
                vertical = 8.dp
            )
        ),
        enabled = enabled
    )
}

/**
 * 小型云朵按钮
 * 用于次要操作
 */
@Composable
fun SmallCloudButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    CloudButton(
        text = text,
        onClick = onClick,
        modifier = modifier.padding(
            PaddingValues(
                horizontal = 16.dp,
                vertical = 4.dp
            )
        ),
        enabled = enabled,
        backgroundColor = MaterialTheme.colorScheme.secondary
    )
}
