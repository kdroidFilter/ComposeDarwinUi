package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.darwinSpring

// ===========================================================================
// SegmentedButtonColors
// ===========================================================================

@Immutable
class SegmentedButtonColors(
    val activeContainerColor: Color,
    val activeContentColor: Color,
    val inactiveContainerColor: Color,
    val inactiveContentColor: Color,
    val disabledActiveContainerColor: Color,
    val disabledActiveContentColor: Color,
    val disabledInactiveContainerColor: Color,
    val disabledInactiveContentColor: Color,
    val activeBorderColor: Color,
    val inactiveBorderColor: Color,
) {
    fun copy(
        activeContainerColor: Color = this.activeContainerColor,
        activeContentColor: Color = this.activeContentColor,
        inactiveContainerColor: Color = this.inactiveContainerColor,
        inactiveContentColor: Color = this.inactiveContentColor,
        disabledActiveContainerColor: Color = this.disabledActiveContainerColor,
        disabledActiveContentColor: Color = this.disabledActiveContentColor,
        disabledInactiveContainerColor: Color = this.disabledInactiveContainerColor,
        disabledInactiveContentColor: Color = this.disabledInactiveContentColor,
        activeBorderColor: Color = this.activeBorderColor,
        inactiveBorderColor: Color = this.inactiveBorderColor,
    ) = SegmentedButtonColors(
        activeContainerColor, activeContentColor,
        inactiveContainerColor, inactiveContentColor,
        disabledActiveContainerColor, disabledActiveContentColor,
        disabledInactiveContainerColor, disabledInactiveContentColor,
        activeBorderColor, inactiveBorderColor,
    )

    internal fun resolvedContainerColor(active: Boolean, enabled: Boolean): Color = when {
        !enabled && active -> disabledActiveContainerColor
        !enabled -> disabledInactiveContainerColor
        active -> activeContainerColor
        else -> inactiveContainerColor
    }

    internal fun resolvedContentColor(active: Boolean, enabled: Boolean): Color = when {
        !enabled && active -> disabledActiveContentColor
        !enabled -> disabledInactiveContentColor
        active -> activeContentColor
        else -> inactiveContentColor
    }
}

// ===========================================================================
// SegmentedButtonDefaults
// ===========================================================================

object SegmentedButtonDefaults {

    @Composable
    fun colors(
        activeContainerColor: Color = DarwinTheme.colorScheme.secondaryContainer,
        activeContentColor: Color = DarwinTheme.colorScheme.onSecondaryContainer,
        inactiveContainerColor: Color = Color.Transparent,
        inactiveContentColor: Color = DarwinTheme.colorScheme.onSurface,
        disabledActiveContainerColor: Color = activeContainerColor.copy(alpha = 0.5f),
        disabledActiveContentColor: Color = activeContentColor.copy(alpha = 0.5f),
        disabledInactiveContainerColor: Color = inactiveContainerColor,
        disabledInactiveContentColor: Color = inactiveContentColor.copy(alpha = 0.5f),
        activeBorderColor: Color = DarwinTheme.colorScheme.outline,
        inactiveBorderColor: Color = DarwinTheme.colorScheme.outline,
    ) = SegmentedButtonColors(
        activeContainerColor, activeContentColor,
        inactiveContainerColor, inactiveContentColor,
        disabledActiveContainerColor, disabledActiveContentColor,
        disabledInactiveContainerColor, disabledInactiveContentColor,
        activeBorderColor, inactiveBorderColor,
    )

    /**
     * Returns the appropriate [Shape] for a segmented button item based on its position.
     *
     * - First item: rounded left corners, square right corners.
     * - Last item: square left corners, rounded right corners.
     * - Middle items: no rounding.
     */
    fun itemShape(index: Int, count: Int): Shape = when {
        count == 1 -> RoundedCornerShape(50.dp)
        index == 0 -> RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp, topEnd = 0.dp, bottomEnd = 0.dp)
        index == count - 1 -> RoundedCornerShape(topStart = 0.dp, bottomStart = 0.dp, topEnd = 50.dp, bottomEnd = 50.dp)
        else -> RoundedCornerShape(0.dp)
    }

    /** Default checkmark icon shown when a segment is selected. */
    @Composable
    fun Icon(
        modifier: Modifier = Modifier.size(16.dp),
    ) {
        val color = LocalDarwinContentColor.current
        Canvas(modifier = modifier) {
            val w = size.width
            val h = size.height
            val startX = w * 0.15f
            val startY = h * 0.52f
            val midX = w * 0.40f
            val midY = h * 0.75f
            val endX = w * 0.85f
            val endY = h * 0.25f
            val strokeWidth = w * 0.12f
            val path = Path().apply {
                moveTo(startX, startY)
                lineTo(midX, midY)
                lineTo(endX, endY)
            }
            drawPath(
                path,
                color = color,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round),
            )
        }
    }
}

// ===========================================================================
// SegmentedButtonRowScope
// ===========================================================================

/** Scope for items placed inside [SingleChoiceSegmentedButtonRow] or [MultiChoiceSegmentedButtonRow]. */
interface SegmentedButtonRowScope {
    val rowScope: RowScope
}

private class SegmentedButtonRowScopeImpl(override val rowScope: RowScope) : SegmentedButtonRowScope

// ===========================================================================
// SingleChoiceSegmentedButtonRow
// ===========================================================================

/**
 * A horizontal row that lays out segmented button items with equal width.
 * Items share a continuous outlined border; there is no gap between adjacent segments.
 */
@Composable
fun SingleChoiceSegmentedButtonRow(
    modifier: Modifier = Modifier,
    space: Dp = 0.dp,
    content: @Composable SegmentedButtonRowScope.() -> Unit,
) {
    Row(
        modifier = modifier.width(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(space),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val scope = SegmentedButtonRowScopeImpl(this)
        scope.content()
    }
}

// ===========================================================================
// SegmentedButton (single-choice)
// ===========================================================================

/**
 * A single item for use inside [SingleChoiceSegmentedButtonRow].
 *
 * @param selected Whether this segment is currently selected.
 * @param onClick Callback invoked when the segment is clicked.
 * @param shape Shape of this segment — use [SegmentedButtonDefaults.itemShape].
 * @param modifier Modifier applied to the segment.
 * @param enabled Whether the segment accepts interactions.
 * @param colors Colors for the various states.
 * @param border Border stroke drawn around the segment.
 * @param icon Optional leading icon; defaults to a checkmark when selected.
 * @param label Content label for the segment.
 */
@Composable
fun SegmentedButtonRowScope.SegmentedButton(
    selected: Boolean,
    onClick: () -> Unit,
    shape: Shape,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: SegmentedButtonColors = SegmentedButtonDefaults.colors(),
    border: BorderStroke = BorderStroke(
        1.dp,
        if (selected) colors.activeBorderColor else colors.inactiveBorderColor,
    ),
    icon: @Composable () -> Unit = { if (selected) SegmentedButtonDefaults.Icon() },
    label: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val containerColor by animateColorAsState(
        targetValue = colors.resolvedContainerColor(selected, enabled),
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "segmentedContainerColor",
    )
    val contentColor by animateColorAsState(
        targetValue = colors.resolvedContentColor(selected, enabled),
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "segmentedContentColor",
    )
    val hoverOverlay by animateColorAsState(
        targetValue = when {
            !enabled || !isHovered -> Color.Transparent
            DarwinTheme.colorScheme.isDark -> Color.White.copy(alpha = 0.06f)
            else -> Color.Black.copy(alpha = 0.04f)
        },
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "segmentedHoverOverlay",
    )
    val pressOverlay by animateColorAsState(
        targetValue = when {
            !enabled || !isPressed -> Color.Transparent
            DarwinTheme.colorScheme.isDark -> Color.White.copy(alpha = 0.10f)
            else -> Color.Black.copy(alpha = 0.06f)
        },
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "segmentedPressOverlay",
    )

    CompositionLocalProvider(
        LocalDarwinContentColor provides contentColor,
        LocalDarwinTextStyle provides DarwinTheme.typography.labelLarge.copy(color = contentColor),
    ) {
        with(rowScope) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .weight(1f)
                    .alpha(if (enabled) 1f else 0.5f)
                    .clip(shape)
                    .background(containerColor, shape)
                    .border(border.width, border.brush, shape)
                    .background(hoverOverlay, shape)
                    .background(pressOverlay, shape)
                    .hoverable(interactionSource, enabled)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        enabled = enabled,
                        role = Role.RadioButton,
                        onClick = onClick,
                    )
                    .defaultMinSize(minHeight = 36.dp)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                ) {
                    icon()
                    label()
                }
            }
        }
    }
}

// ===========================================================================
// MultiChoiceSegmentedButtonRow
// ===========================================================================

/**
 * A horizontal row that lays out multi-choice segmented button items with equal width.
 * Each item can be independently checked or unchecked.
 */
@Composable
fun MultiChoiceSegmentedButtonRow(
    modifier: Modifier = Modifier,
    space: Dp = 0.dp,
    content: @Composable SegmentedButtonRowScope.() -> Unit,
) {
    Row(
        modifier = modifier.width(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(space),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val scope = SegmentedButtonRowScopeImpl(this)
        scope.content()
    }
}

// ===========================================================================
// MultiChoiceSegmentedButton
// ===========================================================================

/**
 * A single item for use inside [MultiChoiceSegmentedButtonRow].
 *
 * @param checked Whether this segment is currently checked.
 * @param onCheckedChange Callback invoked when the checked state should change.
 * @param shape Shape of this segment — use [SegmentedButtonDefaults.itemShape].
 * @param modifier Modifier applied to the segment.
 * @param enabled Whether the segment accepts interactions.
 * @param colors Colors for the various states.
 * @param border Border stroke drawn around the segment.
 * @param icon Optional leading icon; defaults to a checkmark when checked.
 * @param label Content label for the segment.
 */
@Composable
fun SegmentedButtonRowScope.MultiChoiceSegmentedButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    shape: Shape,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: SegmentedButtonColors = SegmentedButtonDefaults.colors(),
    border: BorderStroke = BorderStroke(
        1.dp,
        if (checked) colors.activeBorderColor else colors.inactiveBorderColor,
    ),
    icon: @Composable () -> Unit = { if (checked) SegmentedButtonDefaults.Icon() },
    label: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val containerColor by animateColorAsState(
        targetValue = colors.resolvedContainerColor(checked, enabled),
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "multiSegmentedContainerColor",
    )
    val contentColor by animateColorAsState(
        targetValue = colors.resolvedContentColor(checked, enabled),
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "multiSegmentedContentColor",
    )
    val hoverOverlay by animateColorAsState(
        targetValue = when {
            !enabled || !isHovered -> Color.Transparent
            DarwinTheme.colorScheme.isDark -> Color.White.copy(alpha = 0.06f)
            else -> Color.Black.copy(alpha = 0.04f)
        },
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "multiSegmentedHoverOverlay",
    )
    val pressOverlay by animateColorAsState(
        targetValue = when {
            !enabled || !isPressed -> Color.Transparent
            DarwinTheme.colorScheme.isDark -> Color.White.copy(alpha = 0.10f)
            else -> Color.Black.copy(alpha = 0.06f)
        },
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "multiSegmentedPressOverlay",
    )

    CompositionLocalProvider(
        LocalDarwinContentColor provides contentColor,
        LocalDarwinTextStyle provides DarwinTheme.typography.labelLarge.copy(color = contentColor),
    ) {
        with(rowScope) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .weight(1f)
                    .alpha(if (enabled) 1f else 0.5f)
                    .clip(shape)
                    .background(containerColor, shape)
                    .border(border.width, border.brush, shape)
                    .background(hoverOverlay, shape)
                    .background(pressOverlay, shape)
                    .hoverable(interactionSource, enabled)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        enabled = enabled,
                        role = Role.Checkbox,
                        onClick = { onCheckedChange(!checked) },
                    )
                    .defaultMinSize(minHeight = 36.dp)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                ) {
                    icon()
                    label()
                }
            }
        }
    }
}
