package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.GlassMaterialSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalContentColor
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosGlassMaterial

// ===========================================================================
// SurfaceColors
// ===========================================================================

@Immutable
class SurfaceColors(
    val containerColor: Color,
    val contentColor: Color,
) {
    fun copy(
        containerColor: Color = this.containerColor,
        contentColor: Color = this.contentColor,
    ) = SurfaceColors(containerColor, contentColor)
}

// ===========================================================================
// SurfaceDefaults
// ===========================================================================

object SurfaceDefaults {

    @Composable
    fun colors(
        containerColor: Color = MacosTheme.colorScheme.surface,
        contentColor: Color = MacosTheme.colorScheme.onSurface,
    ) = SurfaceColors(containerColor, contentColor)

    @Composable
    fun cardColors(
        containerColor: Color = MacosTheme.colorScheme.card,
        contentColor: Color = MacosTheme.colorScheme.cardForeground,
    ) = SurfaceColors(containerColor, contentColor)

    @Composable
    fun elevatedColors(
        containerColor: Color = MacosTheme.colorScheme.surfaceContainerLow,
        contentColor: Color = MacosTheme.colorScheme.onSurface,
    ) = SurfaceColors(containerColor, contentColor)

    @Composable
    fun outlinedBorder(enabled: Boolean = true): BorderStroke = BorderStroke(
        width = 1.dp,
        color = if (enabled) {
            MacosTheme.colorScheme.outline
        } else {
            MacosTheme.colorScheme.outline.copy(alpha = 0.5f)
        },
    )
}

// ===========================================================================
// Surface — macOS 26 Liquid Glass container
// ===========================================================================

/**
 * A macOS 26 styled container that renders with Liquid Glass material by default.
 * Falls back to solid [colors] when glass is not available.
 *
 * Uses [BoxScope] — does not impose any layout direction.
 *
 * @param modifier Modifier applied to the container.
 * @param contentAlignment Alignment of the content inside the container.
 * @param shape Corner shape of the container.
 * @param materialSize Liquid Glass material tier. Set to `null` to disable glass
 *   and use plain [colors] background instead.
 * @param tintColor Optional accent tint applied over the glass material.
 * @param colors Fallback colors when [materialSize] is `null`.
 * @param border Optional border stroke.
 * @param elevation Shadow elevation (used only when [materialSize] is `null`).
 * @param content The content to display inside the surface.
 */
@Composable
fun Surface(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    shape: Shape = MacosTheme.shapes.large,
    materialSize: GlassMaterialSize? = GlassMaterialSize.Medium,
    tintColor: Color? = null,
    colors: SurfaceColors = SurfaceDefaults.colors(),
    border: BorderStroke? = null,
    elevation: Dp = 0.dp,
    content: @Composable BoxScope.() -> Unit,
) {
    val backgroundModifier = if (materialSize != null) {
        Modifier.macosGlassMaterial(
            shape = shape,
            materialSize = materialSize,
            tintColor = tintColor,
        )
    } else {
        Modifier
            .then(
                if (elevation > 0.dp) {
                    Modifier.shadow(elevation = elevation, shape = shape, clip = false)
                } else {
                    Modifier
                },
            )
            .clip(shape)
            .background(colors.containerColor, shape)
    }

    val borderModifier = if (border != null) {
        Modifier.border(border.width, border.brush, shape)
    } else {
        Modifier
    }

    CompositionLocalProvider(LocalContentColor provides colors.contentColor) {
        Box(
            modifier = modifier
                .then(backgroundModifier)
                .then(borderModifier),
            contentAlignment = contentAlignment,
            content = content,
        )
    }
}

@Preview
@Composable
private fun SurfacePreview() {
    MacosTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            Text("Glass surface", modifier = Modifier.padding(16.dp))
        }
    }
}
