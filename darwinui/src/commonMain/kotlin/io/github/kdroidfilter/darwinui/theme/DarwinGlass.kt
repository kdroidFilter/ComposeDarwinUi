package io.github.kdroidfilter.darwinui.theme

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.fletchmckee.liquid.LiquidState
import io.github.fletchmckee.liquid.liquid

/**
 * Provides a [LiquidState] for backdrop glass effects via [Modifier.darwinGlass].
 * Null when liquid glass is disabled in [DarwinTheme].
 */
val LocalDarwinLiquidState = compositionLocalOf<LiquidState?> { null }

/**
 * Provides a [LiquidState] for toolbar-level glass effects on buttons.
 * Set by [DarwinScaffold][io.github.kdroidfilter.darwinui.components.DarwinScaffold]
 * inside the title bar area so toolbar buttons can render with frosted glass.
 * Null when outside a scaffold title bar or when glass is unavailable.
 */
val LocalToolbarGlassState = compositionLocalOf<LiquidState?> { null }

/**
 * Applies a liquid glass (frosted backdrop blur) effect to overlay composables.
 *
 * When [LocalDarwinLiquidState] is available ([DarwinTheme] created with `liquidGlass = true`),
 * samples the captured background, applies frost blur + tint and clips to [shape].
 *
 * Falls back to a plain clipped [fallbackColor] background when glass is unavailable.
 *
 * @param shape The clipping/effect shape.
 * @param fallbackColor Background used when blur is unavailable.
 * @param frost Blur radius for the frost effect (default 18.dp).
 */
@Composable
fun Modifier.darwinGlass(
    shape: Shape,
    fallbackColor: Color,
    frost: Dp = 18.dp,
): Modifier {
    val liquidState = LocalDarwinLiquidState.current
    val isDark = LocalDarwinColors.current.isDark
    return if (liquidState != null) {
        val tint = if (isDark) Color.Black.copy(alpha = 0.30f)
                   else Color.White.copy(alpha = 0.40f)
        this.liquid(liquidState) {
            this.frost = frost
            this.shape = shape
            this.tint = tint
            saturation = 1.15f
        }
    } else {
        this
            .clip(shape)
            .background(fallbackColor, shape)
    }
}
