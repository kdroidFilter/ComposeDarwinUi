package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.Spinner
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.theme.Blue500
import io.github.kdroidfilter.darwinui.theme.Blue600
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.DarwinTypography
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.Purple500
import io.github.kdroidfilter.darwinui.theme.Red500
import io.github.kdroidfilter.darwinui.theme.Zinc100
import io.github.kdroidfilter.darwinui.theme.Zinc200
import io.github.kdroidfilter.darwinui.theme.Zinc300
import io.github.kdroidfilter.darwinui.theme.Zinc400
import io.github.kdroidfilter.darwinui.theme.Zinc500
import io.github.kdroidfilter.darwinui.theme.Zinc800
import io.github.kdroidfilter.darwinui.theme.Zinc900
import io.github.kdroidfilter.darwinui.theme.darwinTween

// ===========================================================================
// ButtonSize
// ===========================================================================

enum class ButtonSize {
    Small,
    Default,
    Large,
    Icon,
}

// ===========================================================================
// Internal — shared colors / dimensions / layout
// ===========================================================================

private data class ButtonColors(
    val background: Color,
    val contentColor: Color,
    val borderColor: Color?,
    val borderWidth: Dp = 1.dp,
)

private data class ButtonDimensions(
    val height: Dp,
    val horizontalPadding: Dp,
    val iconSpacing: Dp,
    val textStyle: TextStyle,
    val indicatorSize: Dp,
    val indicatorStrokeWidth: Dp,
)

/** Controls how hover overlay and link-underline behave. */
private enum class ButtonBehavior { Solid, Ghost, Outline, Link }

@Composable
private fun resolveButtonDimensions(
    size: ButtonSize,
    typography: DarwinTypography,
): ButtonDimensions = when (size) {
    ButtonSize.Small -> ButtonDimensions(
        height = 32.dp, horizontalPadding = 12.dp, iconSpacing = 6.dp,
        textStyle = typography.labelSmall, indicatorSize = 14.dp, indicatorStrokeWidth = 1.5.dp,
    )
    ButtonSize.Default -> ButtonDimensions(
        height = 36.dp, horizontalPadding = 16.dp, iconSpacing = 8.dp,
        textStyle = typography.labelMedium, indicatorSize = 16.dp, indicatorStrokeWidth = 2.dp,
    )
    ButtonSize.Large -> ButtonDimensions(
        height = 40.dp, horizontalPadding = 32.dp, iconSpacing = 8.dp,
        textStyle = typography.labelLarge, indicatorSize = 18.dp, indicatorStrokeWidth = 2.dp,
    )
    ButtonSize.Icon -> ButtonDimensions(
        height = 36.dp, horizontalPadding = 0.dp, iconSpacing = 0.dp,
        textStyle = typography.labelMedium, indicatorSize = 16.dp, indicatorStrokeWidth = 2.dp,
    )
}

/**
 * Shared button layout used by every public button composable.
 * This follows the same pattern as Material3's internal Surface/Button impl:
 * each public composable resolves its own colors and delegates here.
 */
@Composable
private fun ButtonLayout(
    onClick: () -> Unit,
    colors: ButtonColors,
    behavior: ButtonBehavior,
    modifier: Modifier,
    size: ButtonSize,
    enabled: Boolean,
    loading: Boolean,
    loadingText: String?,
    fullWidth: Boolean,
    leftIcon: (@Composable () -> Unit)?,
    rightIcon: (@Composable () -> Unit)?,
    content: @Composable () -> Unit,
) {
    val themeColors = DarwinTheme.colors
    val shapes = DarwinTheme.shapes
    val typography = DarwinTheme.typography
    val dimensions = resolveButtonDimensions(size, typography)
    val shape = shapes.medium

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isInteractive = enabled && !loading

    val scale by animateFloatAsState(
        targetValue = if (isPressed && isInteractive) 0.97f else 1f,
        animationSpec = darwinTween(DarwinDuration.Fast),
        label = "button_scale",
    )
    val alpha by animateFloatAsState(
        targetValue = if (!enabled) 0.5f else 1f,
        animationSpec = darwinTween(DarwinDuration.Normal),
        label = "button_alpha",
    )

    val hoverOverlay: Color = when {
        !isHovered || !isInteractive -> Color.Transparent
        behavior == ButtonBehavior.Ghost -> if (themeColors.isDark) Color.White.copy(alpha = 0.05f) else Color.Black.copy(alpha = 0.05f)
        behavior == ButtonBehavior.Outline -> if (themeColors.isDark) Color.White.copy(alpha = 0.05f) else Color.Black.copy(alpha = 0.04f)
        behavior == ButtonBehavior.Link -> Color.Transparent
        else -> if (themeColors.isDark) Color.White.copy(alpha = 0.08f) else Color.Black.copy(alpha = 0.06f)
    }

    val linkUnderline = behavior == ButtonBehavior.Link && isHovered && isInteractive

    val buttonModifier = modifier
        .then(if (fullWidth) Modifier.fillMaxWidth() else Modifier)
        .scale(scale)
        .alpha(alpha)
        .clip(shape)
        .background(colors.background, shape)
        .then(
            if (colors.borderColor != null) Modifier.border(colors.borderWidth, colors.borderColor, shape)
            else Modifier,
        )
        .background(hoverOverlay, shape)
        .hoverable(interactionSource = interactionSource, enabled = isInteractive)
        .clickable(
            interactionSource = interactionSource,
            indication = null,
            enabled = isInteractive,
            role = Role.Button,
            onClick = onClick,
        )
        .then(
            when (size) {
                ButtonSize.Icon -> Modifier.size(36.dp)
                else -> Modifier
                    .defaultMinSize(minHeight = dimensions.height)
                    .padding(PaddingValues(horizontal = dimensions.horizontalPadding, vertical = 0.dp))
            },
        )

    Box(modifier = buttonModifier, contentAlignment = Alignment.Center) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            if (loading) {
                Spinner(
                    modifier = Modifier.size(dimensions.indicatorSize),
                    color = colors.contentColor,
                    strokeWidth = dimensions.indicatorStrokeWidth,
                )
                if (loadingText != null) {
                    Spacer(modifier = Modifier.width(dimensions.iconSpacing))
                    Text(text = loadingText, style = dimensions.textStyle, color = colors.contentColor)
                }
            } else {
                val textStyle = if (linkUnderline) dimensions.textStyle.copy(textDecoration = TextDecoration.Underline)
                else dimensions.textStyle

                CompositionLocalProvider(
                    LocalDarwinContentColor provides colors.contentColor,
                    LocalDarwinTextStyle provides textStyle,
                ) {
                    if (leftIcon != null) { leftIcon(); Spacer(modifier = Modifier.width(dimensions.iconSpacing)) }
                    content()
                    if (rightIcon != null) { Spacer(modifier = Modifier.width(dimensions.iconSpacing)); rightIcon() }
                }
            }
        }
    }
}

// ===========================================================================
// Button — default
// ===========================================================================

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    fullWidth: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val isDark = DarwinTheme.colors.isDark
    ButtonLayout(
        onClick = onClick,
        colors = ButtonColors(
            background = if (isDark) Color.White.copy(alpha = 0.10f) else Zinc200,
            contentColor = if (isDark) Zinc100 else Zinc900,
            borderColor = if (isDark) Color.White.copy(alpha = 0.10f) else Zinc300,
        ),
        behavior = ButtonBehavior.Solid,
        modifier = modifier, size = size, enabled = enabled, loading = loading,
        loadingText = loadingText, fullWidth = fullWidth, leftIcon = leftIcon, rightIcon = rightIcon,
        content = content,
    )
}

@Composable
fun Button(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    fullWidth: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
) {
    Button(onClick, modifier, size, enabled, loading, loadingText, fullWidth, leftIcon, rightIcon) { Text(text) }
}

// ===========================================================================
// PrimaryButton
// ===========================================================================

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    fullWidth: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    ButtonLayout(
        onClick = onClick,
        colors = ButtonColors(background = Blue500, contentColor = Color.White, borderColor = null),
        behavior = ButtonBehavior.Solid,
        modifier = modifier, size = size, enabled = enabled, loading = loading,
        loadingText = loadingText, fullWidth = fullWidth, leftIcon = leftIcon, rightIcon = rightIcon,
        content = content,
    )
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    fullWidth: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
) {
    PrimaryButton(onClick, modifier, size, enabled, loading, loadingText, fullWidth, leftIcon, rightIcon) { Text(text) }
}

// ===========================================================================
// SecondaryButton
// ===========================================================================

@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    fullWidth: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val isDark = DarwinTheme.colors.isDark
    ButtonLayout(
        onClick = onClick,
        colors = ButtonColors(
            background = if (isDark) Color.White.copy(alpha = 0.05f) else Zinc100,
            contentColor = if (isDark) Zinc300 else Zinc800,
            borderColor = if (isDark) Color.White.copy(alpha = 0.10f) else Zinc200,
        ),
        behavior = ButtonBehavior.Solid,
        modifier = modifier, size = size, enabled = enabled, loading = loading,
        loadingText = loadingText, fullWidth = fullWidth, leftIcon = leftIcon, rightIcon = rightIcon,
        content = content,
    )
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    fullWidth: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
) {
    SecondaryButton(onClick, modifier, size, enabled, loading, loadingText, fullWidth, leftIcon, rightIcon) { Text(text) }
}

// ===========================================================================
// SuccessButton
// ===========================================================================

@Composable
fun SuccessButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    fullWidth: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    ButtonLayout(
        onClick = onClick,
        colors = ButtonColors(background = DarwinTheme.colors.success, contentColor = Color.White, borderColor = null),
        behavior = ButtonBehavior.Solid,
        modifier = modifier, size = size, enabled = enabled, loading = loading,
        loadingText = loadingText, fullWidth = fullWidth, leftIcon = leftIcon, rightIcon = rightIcon,
        content = content,
    )
}

@Composable
fun SuccessButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    fullWidth: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
) {
    SuccessButton(onClick, modifier, size, enabled, loading, loadingText, fullWidth, leftIcon, rightIcon) { Text(text) }
}

// ===========================================================================
// WarningButton
// ===========================================================================

@Composable
fun WarningButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    fullWidth: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    ButtonLayout(
        onClick = onClick,
        colors = ButtonColors(background = DarwinTheme.colors.warning, contentColor = Color.Black, borderColor = null),
        behavior = ButtonBehavior.Solid,
        modifier = modifier, size = size, enabled = enabled, loading = loading,
        loadingText = loadingText, fullWidth = fullWidth, leftIcon = leftIcon, rightIcon = rightIcon,
        content = content,
    )
}

@Composable
fun WarningButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    fullWidth: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
) {
    WarningButton(onClick, modifier, size, enabled, loading, loadingText, fullWidth, leftIcon, rightIcon) { Text(text) }
}

// ===========================================================================
// InfoButton
// ===========================================================================

@Composable
fun InfoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    fullWidth: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    ButtonLayout(
        onClick = onClick,
        colors = ButtonColors(background = DarwinTheme.colors.info, contentColor = Color.White, borderColor = null),
        behavior = ButtonBehavior.Solid,
        modifier = modifier, size = size, enabled = enabled, loading = loading,
        loadingText = loadingText, fullWidth = fullWidth, leftIcon = leftIcon, rightIcon = rightIcon,
        content = content,
    )
}

@Composable
fun InfoButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    fullWidth: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
) {
    InfoButton(onClick, modifier, size, enabled, loading, loadingText, fullWidth, leftIcon, rightIcon) { Text(text) }
}

// ===========================================================================
// DestructiveButton
// ===========================================================================

@Composable
fun DestructiveButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    fullWidth: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    ButtonLayout(
        onClick = onClick,
        colors = ButtonColors(background = Red500, contentColor = Color.White, borderColor = null),
        behavior = ButtonBehavior.Solid,
        modifier = modifier, size = size, enabled = enabled, loading = loading,
        loadingText = loadingText, fullWidth = fullWidth, leftIcon = leftIcon, rightIcon = rightIcon,
        content = content,
    )
}

@Composable
fun DestructiveButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    fullWidth: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
) {
    DestructiveButton(onClick, modifier, size, enabled, loading, loadingText, fullWidth, leftIcon, rightIcon) { Text(text) }
}

// ===========================================================================
// OutlineButton
// ===========================================================================

@Composable
fun OutlineButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    fullWidth: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val isDark = DarwinTheme.colors.isDark
    ButtonLayout(
        onClick = onClick,
        colors = ButtonColors(
            background = Color.Transparent,
            contentColor = if (isDark) Zinc200 else Zinc800,
            borderColor = if (isDark) Zinc500 else Zinc400,
            borderWidth = 2.dp,
        ),
        behavior = ButtonBehavior.Outline,
        modifier = modifier, size = size, enabled = enabled, loading = loading,
        loadingText = loadingText, fullWidth = fullWidth, leftIcon = leftIcon, rightIcon = rightIcon,
        content = content,
    )
}

@Composable
fun OutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    fullWidth: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
) {
    OutlineButton(onClick, modifier, size, enabled, loading, loadingText, fullWidth, leftIcon, rightIcon) { Text(text) }
}

// ===========================================================================
// SubtleButton (ghost)
// ===========================================================================

@Composable
fun SubtleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    fullWidth: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val isDark = DarwinTheme.colors.isDark
    ButtonLayout(
        onClick = onClick,
        colors = ButtonColors(
            background = Color.Transparent,
            contentColor = if (isDark) Zinc300 else Zinc800,
            borderColor = null,
        ),
        behavior = ButtonBehavior.Ghost,
        modifier = modifier, size = size, enabled = enabled, loading = loading,
        loadingText = loadingText, fullWidth = fullWidth, leftIcon = leftIcon, rightIcon = rightIcon,
        content = content,
    )
}

@Composable
fun SubtleButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    fullWidth: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
) {
    SubtleButton(onClick, modifier, size, enabled, loading, loadingText, fullWidth, leftIcon, rightIcon) { Text(text) }
}

// ===========================================================================
// HyperlinkButton (link)
// ===========================================================================

@Composable
fun HyperlinkButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    val isDark = DarwinTheme.colors.isDark
    ButtonLayout(
        onClick = onClick,
        colors = ButtonColors(
            background = Color.Transparent,
            contentColor = if (isDark) Color(0xFF60A5FA) else Blue600,
            borderColor = null,
        ),
        behavior = ButtonBehavior.Link,
        modifier = modifier, size = size, enabled = enabled, loading = false,
        loadingText = null, fullWidth = false, leftIcon = null, rightIcon = null,
        content = content,
    )
}

@Composable
fun HyperlinkButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
) {
    HyperlinkButton(onClick, modifier, size, enabled) { Text(text) }
}

// ===========================================================================
// AccentButton
// ===========================================================================

@Composable
fun AccentButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    fullWidth: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    ButtonLayout(
        onClick = onClick,
        colors = ButtonColors(background = Purple500, contentColor = Color.White, borderColor = null),
        behavior = ButtonBehavior.Solid,
        modifier = modifier, size = size, enabled = enabled, loading = loading,
        loadingText = loadingText, fullWidth = fullWidth, leftIcon = leftIcon, rightIcon = rightIcon,
        content = content,
    )
}

@Composable
fun AccentButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Default,
    enabled: Boolean = true,
    loading: Boolean = false,
    loadingText: String? = null,
    fullWidth: Boolean = false,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
) {
    AccentButton(onClick, modifier, size, enabled, loading, loadingText, fullWidth, leftIcon, rightIcon) { Text(text) }
}

// ===========================================================================
// Preview
// ===========================================================================

@Preview
@Composable
private fun ButtonPreview() {
    DarwinTheme {
        Button(onClick = {}) { Text("Button") }
    }
}
