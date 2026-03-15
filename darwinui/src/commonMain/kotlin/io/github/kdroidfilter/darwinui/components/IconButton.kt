package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.theme.ControlSize
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalControlSize
import io.github.kdroidfilter.darwinui.theme.darwinSpring
import io.github.kdroidfilter.darwinui.theme.darwinTween

/**
 * Visual style for [IconButton], matching macOS Sketch specifications.
 */
enum class IconButtonStyle {
    /** Gray-filled circle — neutral action. */
    Bordered,

    /** Accent-filled circle — primary action. */
    BorderedProminent,

    /** Transparent circle — minimal style, icon only. */
    Borderless,
}

/**
 * Semantic role for [IconButton].
 */
enum class IconButtonRole {
    Default,
    Destructive,
}

// Sketch icon button sizes: Small=28, Medium=34, Large=50
private fun iconButtonSizeFor(controlSize: ControlSize): Dp = when (controlSize) {
    ControlSize.Mini -> 22.dp
    ControlSize.Small -> 28.dp
    ControlSize.Regular -> 34.dp
    ControlSize.Large -> 50.dp
    ControlSize.ExtraLarge -> 60.dp
}

// Icon sizes inside the circular button
private fun iconButtonIconSizeFor(controlSize: ControlSize): Dp = when (controlSize) {
    ControlSize.Mini -> 12.dp
    ControlSize.Small -> 14.dp
    ControlSize.Regular -> 18.dp
    ControlSize.Large -> 24.dp
    ControlSize.ExtraLarge -> 28.dp
}

/**
 * macOS-style circular icon button.
 *
 * Matches the Sketch "Buttons / Content Area / Symbol" specifications:
 * circular shape with configurable style and destructive role.
 *
 * @param icon Icon to display.
 * @param onClick Called when the button is clicked.
 * @param modifier Modifier applied to the component.
 * @param style Visual style of the button.
 * @param role Semantic role — [IconButtonRole.Destructive] uses red coloring.
 * @param enabled Whether the button is interactive.
 */
@Composable
fun IconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: IconButtonStyle = IconButtonStyle.Bordered,
    role: IconButtonRole = IconButtonRole.Default,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val controlSize = LocalControlSize.current
    val isDark = DarwinTheme.colorScheme.isDark
    val accent = DarwinTheme.colorScheme.accent
    val destructive = DarwinTheme.colorScheme.destructive

    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.92f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "icon_btn_scale",
    )

    val colors = resolveIconButtonColors(style, role, isDark, accent, destructive)

    val bgColor by animateColorAsState(
        targetValue = when {
            !enabled -> colors.backgroundDisabled
            isPressed -> colors.backgroundPressed
            else -> colors.background
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "icon_btn_bg",
    )
    val hoverOverlay by animateColorAsState(
        targetValue = when {
            !isHovered || !enabled -> Color.Transparent
            else -> colors.hoverOverlay
        },
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "icon_btn_hover",
    )
    val iconColor = when {
        !enabled -> colors.iconDisabled
        else -> colors.icon
    }

    val buttonSize = iconButtonSizeFor(controlSize)
    val iconSize = iconButtonIconSizeFor(controlSize)

    Box(
        modifier = modifier
            .scale(scale)
            .size(buttonSize)
            .clip(CircleShape)
            .background(bgColor, CircleShape)
            .background(hoverOverlay, CircleShape)
            .hoverable(interactionSource = interactionSource, enabled = enabled)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Button,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            modifier = Modifier.size(iconSize),
            tint = iconColor,
        )
    }
}

private data class IconButtonResolvedColors(
    val background: Color,
    val backgroundPressed: Color,
    val backgroundDisabled: Color,
    val icon: Color,
    val iconDisabled: Color,
    val hoverOverlay: Color,
)

@Composable
private fun resolveIconButtonColors(
    style: IconButtonStyle,
    role: IconButtonRole,
    isDark: Boolean,
    accent: Color,
    destructive: Color,
): IconButtonResolvedColors {
    val isDestructive = role == IconButtonRole.Destructive
    val base = if (isDark) Color.White else Color.Black

    return when (style) {
        IconButtonStyle.Bordered -> {
            // Sketch: Light #7676801f, Dark #ebebf54d
            val bgAlpha = if (isDark) 0.08f else 0.05f
            val bgPressedAlpha = if (isDark) 0.16f else 0.12f
            IconButtonResolvedColors(
                background = if (isDestructive) {
                    destructive.copy(alpha = 0.14f)
                } else {
                    base.copy(alpha = bgAlpha)
                },
                backgroundPressed = if (isDestructive) {
                    destructive.copy(alpha = 0.22f)
                } else {
                    base.copy(alpha = bgPressedAlpha)
                },
                backgroundDisabled = base.copy(alpha = if (isDark) 0.04f else 0.03f),
                icon = if (isDestructive) destructive else base.copy(alpha = 0.85f),
                iconDisabled = base.copy(alpha = 0.25f),
                hoverOverlay = if (isDestructive) {
                    destructive.copy(alpha = 0.06f)
                } else {
                    base.copy(alpha = 0.04f)
                },
            )
        }

        IconButtonStyle.BorderedProminent -> {
            val fillColor = if (isDestructive) destructive else accent
            IconButtonResolvedColors(
                background = fillColor,
                backgroundPressed = fillColor,
                backgroundDisabled = fillColor.copy(alpha = 0.40f),
                icon = Color.White,
                iconDisabled = Color.White.copy(alpha = 0.50f),
                hoverOverlay = Color.White.copy(alpha = 0.10f),
            )
        }

        IconButtonStyle.Borderless -> {
            val tintColor = if (isDestructive) destructive else accent
            IconButtonResolvedColors(
                background = Color.Transparent,
                backgroundPressed = base.copy(alpha = if (isDark) 0.10f else 0.06f),
                backgroundDisabled = Color.Transparent,
                icon = tintColor,
                iconDisabled = tintColor.copy(alpha = 0.40f),
                hoverOverlay = base.copy(alpha = 0.04f),
            )
        }
    }
}
