package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.darwinSpring

// ===========================================================================
// NavigationRailItemColors
// ===========================================================================

@Immutable
data class NavigationRailItemColors(
    val selectedIconColor: Color,
    val selectedTextColor: Color,
    val unselectedIconColor: Color,
    val unselectedTextColor: Color,
    val disabledIconColor: Color,
    val disabledTextColor: Color,
)

// ===========================================================================
// NavigationRailItemDefaults
// ===========================================================================

object NavigationRailItemDefaults {
    @Composable
    fun colors(
        selectedIconColor: Color = DarwinTheme.colors.accent,
        selectedTextColor: Color = DarwinTheme.colors.accent,
        unselectedIconColor: Color = DarwinTheme.colors.textTertiary,
        unselectedTextColor: Color = DarwinTheme.colors.textTertiary,
        disabledIconColor: Color = DarwinTheme.colors.textTertiary.copy(alpha = 0.38f),
        disabledTextColor: Color = DarwinTheme.colors.textTertiary.copy(alpha = 0.38f),
    ) = NavigationRailItemColors(
        selectedIconColor, selectedTextColor,
        unselectedIconColor, unselectedTextColor,
        disabledIconColor, disabledTextColor,
    )
}

// ===========================================================================
// NavigationRailDefaults
// ===========================================================================

object NavigationRailDefaults {
    val containerColor: Color @Composable get() = DarwinTheme.colors.card
    val contentColor: Color @Composable get() = DarwinTheme.colors.textTertiary
    val MinWidth: Dp = 72.dp
}

// ===========================================================================
// NavigationRail — iOS/macOS-style vertical tab rail
// ===========================================================================

@Composable
fun NavigationRail(
    modifier: Modifier = Modifier,
    containerColor: Color = NavigationRailDefaults.containerColor,
    contentColor: Color = NavigationRailDefaults.contentColor,
    header: (@Composable ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = DarwinTheme.colors
    val separatorColor = if (colors.isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.08f)

    CompositionLocalProvider(LocalDarwinContentColor provides contentColor) {
        Column(
            modifier = modifier
                .fillMaxHeight()
                .width(NavigationRailDefaults.MinWidth)
                .background(containerColor)
                .drawBehind {
                    // Right separator line
                    drawLine(
                        color = separatorColor,
                        start = Offset(size.width, 0f),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.dp.toPx(),
                    )
                }
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            if (header != null) {
                Column(
                    modifier = Modifier.padding(bottom = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = header,
                )
            }
            content()
        }
    }
}

// ===========================================================================
// NavigationRailItem — iOS/macOS-style: accent icon+label, no pill
// ===========================================================================

@Composable
fun ColumnScope.NavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: (@Composable () -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    colors: NavigationRailItemColors = NavigationRailItemDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val iconColor by animateColorAsState(
        targetValue = when {
            !enabled -> colors.disabledIconColor
            selected -> colors.selectedIconColor
            else -> colors.unselectedIconColor
        },
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "navRailIconColor",
    )
    val textColor by animateColorAsState(
        targetValue = when {
            !enabled -> colors.disabledTextColor
            selected -> colors.selectedTextColor
            else -> colors.unselectedTextColor
        },
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "navRailTextColor",
    )

    val scale by animateFloatAsState(
        targetValue = if (selected) 1.08f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "navRailScale",
    )

    Column(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Tab,
                onClick = onClick,
            )
            .padding(vertical = 8.dp, horizontal = 4.dp)
            .graphicsLayer { scaleX = scale; scaleY = scale },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        Box(
            modifier = Modifier.size(24.dp),
            contentAlignment = Alignment.Center,
        ) {
            CompositionLocalProvider(LocalDarwinContentColor provides iconColor) { icon() }
        }

        if (label != null && (alwaysShowLabel || selected)) {
            CompositionLocalProvider(
                LocalDarwinContentColor provides textColor,
                LocalDarwinTextStyle provides DarwinTheme.typography.labelSmall.copy(color = textColor),
            ) { label() }
        }
    }
}
