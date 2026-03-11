package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.draw.clip
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
// NavigationRailItemColors — mirrors M3's NavigationRailItemColors
// ===========================================================================

@Immutable
data class NavigationRailItemColors(
    val selectedIconColor: Color,
    val selectedTextColor: Color,
    val indicatorColor: Color,
    val unselectedIconColor: Color,
    val unselectedTextColor: Color,
    val disabledIconColor: Color,
    val disabledTextColor: Color,
)

// ===========================================================================
// NavigationRailItemDefaults — mirrors M3's NavigationRailItemDefaults
// ===========================================================================

object NavigationRailItemDefaults {
    @Composable
    fun colors(
        selectedIconColor: Color = DarwinTheme.colorScheme.onSecondaryContainer,
        selectedTextColor: Color = DarwinTheme.colorScheme.onSurface,
        indicatorColor: Color = DarwinTheme.colorScheme.secondaryContainer,
        unselectedIconColor: Color = DarwinTheme.colorScheme.onSurfaceVariant,
        unselectedTextColor: Color = DarwinTheme.colorScheme.onSurfaceVariant,
        disabledIconColor: Color = DarwinTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f),
        disabledTextColor: Color = DarwinTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f),
    ) = NavigationRailItemColors(
        selectedIconColor, selectedTextColor, indicatorColor,
        unselectedIconColor, unselectedTextColor,
        disabledIconColor, disabledTextColor,
    )
}

// ===========================================================================
// NavigationRailDefaults — mirrors M3's NavigationRailDefaults
// ===========================================================================

object NavigationRailDefaults {
    val containerColor: Color @Composable get() = DarwinTheme.colorScheme.surfaceContainer
    val contentColor: Color @Composable get() = DarwinTheme.colorScheme.onSurfaceVariant
    val MinWidth: Dp = 80.dp
}

// ===========================================================================
// NavigationRail — mirrors M3's NavigationRail (vertical variant of NavigationBar)
// ===========================================================================

@Composable
fun NavigationRail(
    modifier: Modifier = Modifier,
    containerColor: Color = NavigationRailDefaults.containerColor,
    contentColor: Color = NavigationRailDefaults.contentColor,
    header: (@Composable ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    CompositionLocalProvider(LocalDarwinContentColor provides contentColor) {
        Column(
            modifier = modifier
                .fillMaxHeight()
                .defaultMinSize(minWidth = NavigationRailDefaults.MinWidth)
                .background(containerColor)
                .border(
                    width = 1.dp,
                    color = DarwinTheme.colorScheme.outlineVariant,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            if (header != null) {
                Column(
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = header,
                )
            }
            content()
        }
    }
}

// ===========================================================================
// NavigationRailItem — mirrors M3's NavigationRailItem
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

    // Single float progress 0→1 drives both width and alpha — matches M3 approach
    val indicatorProgress by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = spring(stiffness = 400f, dampingRatio = 1f),
        label = "navRailIndicator",
    )

    val indicatorShape = DarwinTheme.shapes.extraLarge

    Column(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Tab,
                onClick = onClick,
            )
            .padding(vertical = 12.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        // Icon with animated pill indicator behind it
        Box(
            modifier = Modifier.defaultMinSize(minWidth = 56.dp, minHeight = 32.dp),
            contentAlignment = Alignment.Center,
        ) {
            // Animated horizontal pill indicator
            Box(
                modifier = Modifier
                    .width(56.dp * indicatorProgress)
                    .height(32.dp)
                    .graphicsLayer { alpha = indicatorProgress }
                    .clip(indicatorShape)
                    .background(colors.indicatorColor, indicatorShape),
            )
            Box(modifier = Modifier.size(24.dp)) {
                CompositionLocalProvider(LocalDarwinContentColor provides iconColor) { icon() }
            }
        }
        if (label != null && (alwaysShowLabel || selected)) {
            CompositionLocalProvider(
                LocalDarwinContentColor provides textColor,
                LocalDarwinTextStyle provides DarwinTheme.typography.labelMedium.copy(color = textColor),
            ) { label() }
        }
    }
}
