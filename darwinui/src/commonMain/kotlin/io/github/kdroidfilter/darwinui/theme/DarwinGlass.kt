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
 * Provides the height of the enclosing title bar so descendant composables
 * (e.g. [Sidebar][io.github.kdroidfilter.darwinui.components.Sidebar]) can
 * add an appropriate top inset when positioned behind it.
 *
 * Set by [DarwinScaffold][io.github.kdroidfilter.darwinui.components.DarwinScaffold].
 * Defaults to 0.dp (no title bar).
 */
val LocalTitleBarHeight = compositionLocalOf { 0.dp }

/**
 * Provides the current expanded width of the sidebar column.
 * Set by [DarwinScaffold][io.github.kdroidfilter.darwinui.components.DarwinScaffold]
 * when a resizable sidebar is used. The [Sidebar][io.github.kdroidfilter.darwinui.components.Sidebar]
 * reads this to synchronize its expanded width with the scaffold's drag state.
 *
 * Defaults to [Dp.Unspecified] (sidebar uses its own [width] parameter).
 */
val LocalSidebarWidth = compositionLocalOf { Dp.Unspecified }

/**
 * Callbacks for resizing the sidebar by dragging its right edge.
 * Null when the sidebar is not resizable (i.e. [DarwinColumnWidth.Fixed]).
 */
class SidebarResizeCallbacks(
    val onDrag: (Dp) -> Unit,
    val onReset: () -> Unit,
)

/**
 * Provides resize callbacks for the sidebar's right edge drag handle.
 * Set by [DarwinScaffold][io.github.kdroidfilter.darwinui.components.DarwinScaffold]
 * when [sidebarWidth] is [DarwinColumnWidth.Flexible].
 * Null when the sidebar is not resizable.
 */
val LocalSidebarResize = compositionLocalOf<SidebarResizeCallbacks?> { null }

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
