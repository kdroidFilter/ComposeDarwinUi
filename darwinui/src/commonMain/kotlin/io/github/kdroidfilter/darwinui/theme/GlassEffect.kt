package io.github.kdroidfilter.darwinui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.glassEffect(
    enabled: Boolean = true,
    shape: Shape = DarwinTheme.shapes.large,
    borderWidth: Dp = 1.dp,
): Modifier {
    if (!enabled) return this

    val colors = DarwinTheme.colors
    return this
        .clip(shape)
        .background(colors.glassBackground, shape)
        .border(borderWidth, colors.glassBorder, shape)
}

/**
 * Returns the glass background color if glass is enabled,
 * otherwise returns the provided fallback color.
 */
@Composable
fun glassOrDefault(glass: Boolean, fallback: Color): Color {
    return if (glass) DarwinTheme.colors.glassBackground else fallback
}

/**
 * Returns the glass border color if glass is enabled,
 * otherwise returns the provided fallback color.
 */
@Composable
fun glassBorderOrDefault(glass: Boolean, fallback: Color): Color {
    return if (glass) DarwinTheme.colors.glassBorder else fallback
}
