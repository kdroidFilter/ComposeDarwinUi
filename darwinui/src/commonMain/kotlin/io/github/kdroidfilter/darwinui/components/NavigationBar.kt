package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.darwinSpring
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle

// ===========================================================================
// NavigationBarItemColors
// ===========================================================================

@Immutable
class NavigationBarItemColors(
    val selectedIconColor: Color,
    val selectedTextColor: Color,
    val unselectedIconColor: Color,
    val unselectedTextColor: Color,
    val disabledIconColor: Color,
    val disabledTextColor: Color,
)

// ===========================================================================
// NavigationBarItemDefaults
// ===========================================================================

object NavigationBarItemDefaults {
    @Composable
    fun colors(
        selectedIconColor: Color = DarwinTheme.colors.accent,
        selectedTextColor: Color = DarwinTheme.colors.accent,
        unselectedIconColor: Color = DarwinTheme.colors.textTertiary,
        unselectedTextColor: Color = DarwinTheme.colors.textTertiary,
        disabledIconColor: Color = DarwinTheme.colors.textTertiary.copy(alpha = 0.38f),
        disabledTextColor: Color = DarwinTheme.colors.textTertiary.copy(alpha = 0.38f),
    ) = NavigationBarItemColors(
        selectedIconColor, selectedTextColor,
        unselectedIconColor, unselectedTextColor,
        disabledIconColor, disabledTextColor,
    )
}

object NavigationBarDefaults {
    val containerColor: Color @Composable get() = DarwinTheme.colors.card
    val TonalElevation: Dp = 0.dp
}

// ===========================================================================
// NavigationBar — iOS/macOS-style tab bar
// ===========================================================================

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = NavigationBarDefaults.containerColor,
    contentColor: Color = NavigationBarDefaults.containerColor,
    tonalElevation: Dp = NavigationBarDefaults.TonalElevation,
    content: @Composable RowScope.() -> Unit,
) {
    val colors = DarwinTheme.colors
    val separatorColor = if (colors.isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.08f)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(62.dp)
            .background(containerColor)
            .drawBehind {
                // Top separator line
                drawLine(
                    color = separatorColor,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx(),
                )
            },
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

// ===========================================================================
// NavigationBarItem — iOS-style: accent icon+label, no pill
// ===========================================================================

@Composable
fun RowScope.NavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: (@Composable () -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    colors: NavigationBarItemColors = NavigationBarItemDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val iconColor by animateColorAsState(
        targetValue = when {
            !enabled -> colors.disabledIconColor
            selected -> colors.selectedIconColor
            else -> colors.unselectedIconColor
        },
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "navBarIconColor",
    )
    val textColor by animateColorAsState(
        targetValue = when {
            !enabled -> colors.disabledTextColor
            selected -> colors.selectedTextColor
            else -> colors.unselectedTextColor
        },
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "navBarTextColor",
    )

    // Subtle scale on selected
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.08f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "navBarScale",
    )

    Column(
        modifier = modifier
            .weight(1f)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Tab,
                onClick = onClick,
            )
            .padding(vertical = 8.dp)
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

@Preview
@Composable
private fun NavigationBarPreview() {
    DarwinTheme {
        NavigationBar {
            NavigationBarItem(selected = true, onClick = {}, icon = {}, label = { Text("Home") })
            NavigationBarItem(selected = false, onClick = {}, icon = {}, label = { Text("Search") })
        }
    }
}
