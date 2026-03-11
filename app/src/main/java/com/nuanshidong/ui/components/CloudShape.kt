package com.nuanshidong.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

/**
 * 云朵形状 - Material Design 3 风格
 * 圆润的类似云朵的形状
 */
class CloudShape : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): androidx.compose.ui.graphics.Outline {
        val path = androidx.compose.ui.graphics.Path().apply {
            val width = size.width
            val height = size.height
            val radius = height * 0.5f

            // 左下角圆角
            moveTo(0f, radius)

            // 左边直线
            lineTo(0f, height - radius)

            // 左下角圆弧
            arcTo(
                androidx.compose.ui.geometry.Rect(
                    left = -radius * 0.5f,
                    top = height - radius,
                    right = radius * 0.5f,
                    bottom = height + radius
                ),
                startAngleRadians = 270f,
                sweepAngleRadians = 90f,
                forceMoveTo = false
            )

            // 底部直线
            lineTo(width, height - radius)

            // 右下角圆弧
            arcTo(
                androidx.compose.ui.geometry.Rect(
                    left = width - radius * 0.5f,
                    top = height - radius,
                    right = width + radius * 0.5f,
                    bottom = height + radius
                ),
                startAngleRadians = 270f,
                sweepAngleRadians = 90f,
                forceMoveTo = false
            )

            // 右边直线
            lineTo(width, radius)

            // 右上角圆弧
            arcTo(
                androidx.compose.ui.geometry.Rect(
                    left = width - radius * 0.5f,
                    top = -radius,
                    right = width + radius * 0.5f,
                    bottom = radius
                ),
                startAngleRadians = 90f,
                sweepAngleRadians = 90f,
                forceMoveTo = false
            )

            // 顶部直线
            lineTo(0f, radius)

            // 左上角圆弧
            arcTo(
                androidx.compose.ui.geometry.Rect(
                    left = -radius * 0.5f,
                    top = -radius,
                    right = radius * 0.5f,
                    bottom = radius
                ),
                startAngleRadians = 90f,
                sweepAngleRadians = 90f,
                forceMoveTo = false
            )

            close()
        }
        return androidx.compose.ui.graphics.Outline.Generic(path)
    }
}

/**
 * 简化版云朵形状 - 圆角矩形
 */
class SimpleCloudShape(private val cornerRadius: androidx.compose.ui.unit.Dp) : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): androidx.compose.ui.graphics.Outline {
        return androidx.compose.ui.graphics.Outline.Rounded(
            androidx.compose.ui.geometry.Rect(0f, 0f, size.width, size.height),
            cornerRadius.toPx(density)
        )
    }
}
