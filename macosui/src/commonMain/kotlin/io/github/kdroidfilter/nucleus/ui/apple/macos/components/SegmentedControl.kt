package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalContentColor
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalTextStyle
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalWindowActive
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.SegmentedControlColors
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.SegmentedControlVariant
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.SpringPreset
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosSpring
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

// =============================================================================
// SegmentedControlDefaults
// =============================================================================

object SegmentedControlDefaults {

    @Composable
    fun colors(
        track: Color = Color.Unspecified,
        selectedSegment: Color = Color.Unspecified,
        selectedContent: Color = Color.Unspecified,
        unselectedContent: Color = Color.Unspecified,
        pressedOverlay: Color = Color.Unspecified,
        disabledContent: Color = Color.Unspecified,
        separatorColor: Color = Color.Unspecified,
        inactiveSelectedSegment: Color = Color.Unspecified,
        inactiveSelectedContent: Color = Color.Unspecified,
        variant: SegmentedControlVariant = SegmentedControlVariant.ContentArea,
    ): SegmentedControlColors {
        val style = MacosTheme.componentStyling.segmentedControl.colorsFor(variant)
        return SegmentedControlColors(
            track = track.takeOrElse(style.track),
            selectedSegment = selectedSegment.takeOrElse(style.selectedSegment),
            selectedContent = selectedContent.takeOrElse(style.selectedContent),
            unselectedContent = unselectedContent.takeOrElse(style.unselectedContent),
            pressedOverlay = pressedOverlay.takeOrElse(style.pressedOverlay),
            disabledContent = disabledContent.takeOrElse(style.disabledContent),
            separatorColor = separatorColor.takeOrElse(style.separatorColor),
            inactiveSelectedSegment = inactiveSelectedSegment.takeOrElse(style.inactiveSelectedSegment),
            inactiveSelectedContent = inactiveSelectedContent.takeOrElse(style.inactiveSelectedContent),
        )
    }

    @Composable
    fun shape(): Shape {
        val controlSize = LocalControlSize.current
        val metrics = MacosTheme.componentStyling.segmentedControl.metrics
        return RoundedCornerShape(metrics.cornerRadiusFor(controlSize))
    }
}

private fun Color.takeOrElse(other: Color): Color =
    if (this != Color.Unspecified) this else other

// =============================================================================
// SegmentedControl — slot-based API
// =============================================================================

@Composable
fun SegmentedControl(
    segmentCount: Int,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: SegmentedControlColors = SegmentedControlDefaults.colors(),
    shape: Shape = SegmentedControlDefaults.shape(),
    segment: @Composable (index: Int) -> Unit,
) {
    require(segmentCount > 0) { "segmentCount must be > 0" }

    val controlSize = LocalControlSize.current
    val isWindowActive = LocalWindowActive.current
    val metrics = MacosTheme.componentStyling.segmentedControl.metrics
    val density = LocalDensity.current

    val trackHeight = metrics.containerHeightFor(controlSize)

    // Resolve pill color based on active/inactive window state
    val pillColor by animateColorAsState(
        targetValue = resolveSelectedSegmentColor(colors, enabled, isWindowActive),
        animationSpec = macosSpring(SpringPreset.Snappy),
        label = "pillColor",
    )

    // Segment measurements
    val segmentOffsets = remember(segmentCount) { FloatArray(segmentCount) }
    val segmentWidths = remember(segmentCount) { FloatArray(segmentCount) }
    var measurementReady by remember { mutableStateOf(false) }

    // Animated indicator
    val indicatorOffsetAnim = remember { Animatable(0f) }
    val indicatorWidthAnim = remember { Animatable(0f) }
    var hasInitialized by remember { mutableStateOf(false) }
    val springSpec = macosSpring<Float>(SpringPreset.Snappy)

    LaunchedEffect(selectedIndex, measurementReady) {
        if (!measurementReady) return@LaunchedEffect
        val targetOffset = segmentOffsets.getOrElse(selectedIndex) { 0f }
        val targetWidth = segmentWidths.getOrElse(selectedIndex) { 0f }
        if (targetWidth > 0f) {
            if (!hasInitialized) {
                indicatorOffsetAnim.snapTo(targetOffset)
                indicatorWidthAnim.snapTo(targetWidth)
                hasInitialized = true
            } else {
                launch { indicatorOffsetAnim.animateTo(targetOffset, springSpec) }
                launch { indicatorWidthAnim.animateTo(targetWidth, springSpec) }
            }
        }
    }

    val selectedContentColor = if (isWindowActive) colors.selectedContent else colors.inactiveSelectedContent

    // Track
    Box(
        modifier = modifier
            .height(trackHeight)
            .clip(shape)
            .background(colors.track, shape),
        contentAlignment = Alignment.CenterStart,
    ) {
        // Sliding pill indicator
        if (indicatorWidthAnim.value > 0f) {
            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = indicatorOffsetAnim.value.roundToInt(),
                            y = 0,
                        )
                    }
                    .size(
                        width = with(density) { indicatorWidthAnim.value.toDp() },
                        height = trackHeight,
                    )
                    .background(pillColor, shape),
            )
        }

        // Segments row
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            for (index in 0 until segmentCount) {
                if (index > 0) {
                    SegmentDivider(
                        visible = index != selectedIndex && index - 1 != selectedIndex,
                        color = colors.separatorColor,
                        controlSize = controlSize,
                        trackHeight = trackHeight,
                    )
                }

                Segment(
                    index = index,
                    isSelected = index == selectedIndex,
                    enabled = enabled,
                    controlSize = controlSize,
                    shape = shape,
                    selectedTextColor = selectedContentColor,
                    unselectedTextColor = if (enabled) colors.unselectedContent else colors.disabledContent,
                    pressedOverlay = colors.pressedOverlay,
                    onMeasured = { idx, offset, width ->
                        segmentOffsets[idx] = offset
                        segmentWidths[idx] = width
                        if (idx == segmentCount - 1) {
                            measurementReady = true
                        }
                    },
                    onClick = { onSelectedIndexChange(index) },
                    content = { segment(index) },
                )
            }
        }
    }
}

private fun resolveSelectedSegmentColor(
    colors: SegmentedControlColors,
    enabled: Boolean,
    isWindowActive: Boolean,
): Color = when {
    !enabled -> colors.selectedSegment.copy(alpha = colors.selectedSegment.alpha * 0.5f)
    isWindowActive -> colors.selectedSegment
    else -> colors.inactiveSelectedSegment
}

// =============================================================================
// SegmentedControl — convenience text-only API
// =============================================================================

@Composable
fun SegmentedControl(
    options: List<String>,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: SegmentedControlColors = SegmentedControlDefaults.colors(),
    shape: Shape = SegmentedControlDefaults.shape(),
) {
    SegmentedControl(
        segmentCount = options.size,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = onSelectedIndexChange,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        shape = shape,
    ) { index ->
        Text(options[index])
    }
}

// =============================================================================
// Internal: Segment
// =============================================================================

private fun fontSizeFor(controlSize: ControlSize) = when (controlSize) {
    ControlSize.Mini -> 10.sp
    ControlSize.Small -> 11.sp
    ControlSize.Regular -> 13.sp
    ControlSize.Large -> 13.sp
    ControlSize.ExtraLarge -> 13.sp
}

@Composable
private fun Segment(
    index: Int,
    isSelected: Boolean,
    enabled: Boolean,
    controlSize: ControlSize,
    shape: Shape,
    selectedTextColor: Color,
    unselectedTextColor: Color,
    pressedOverlay: Color,
    onMeasured: (index: Int, offset: Float, width: Float) -> Unit,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val pressAlpha by animateFloatAsState(
        targetValue = if (isPressed && enabled) 1f else 0f,
        animationSpec = macosSpring(SpringPreset.Snappy),
        label = "segmentPress",
    )

    val hoverAlpha by animateFloatAsState(
        targetValue = when {
            !enabled || isSelected || !isHovered -> 0f
            else -> 0.03f
        },
        animationSpec = macosSpring(SpringPreset.Snappy),
        label = "segmentHover",
    )

    val textColor = if (isSelected && enabled) selectedTextColor else unselectedTextColor
    val textStyle = TextStyle(
        color = textColor,
        fontWeight = FontWeight.SemiBold,
        fontSize = fontSizeFor(controlSize),
    )

    val metrics = MacosTheme.componentStyling.segmentedControl.metrics
    val minWidth = metrics.segmentMinWidthFor(controlSize)
    val horizontalPadding = metrics.segmentHorizontalPaddingFor(controlSize)

    Box(
        modifier = Modifier
            .widthIn(min = minWidth)
            .fillMaxHeight()
            .onGloballyPositioned { coordinates ->
                onMeasured(
                    index,
                    coordinates.positionInParent().x,
                    coordinates.size.width.toFloat(),
                )
            }
            .clip(shape)
            .then(
                if (pressAlpha > 0f) {
                    Modifier.background(pressedOverlay.copy(alpha = pressedOverlay.alpha * pressAlpha))
                } else if (hoverAlpha > 0f) {
                    Modifier.background(Color.Black.copy(alpha = hoverAlpha))
                } else {
                    Modifier
                },
            )
            .hoverable(interactionSource, enabled)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Tab,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides textColor,
            LocalTextStyle provides textStyle,
        ) {
            Box(Modifier.padding(horizontal = horizontalPadding)) {
                content()
            }
        }
    }
}

// =============================================================================
// Internal: Segment divider
// =============================================================================

@Composable
private fun SegmentDivider(
    visible: Boolean,
    color: Color,
    controlSize: ControlSize,
    trackHeight: Dp,
) {
    val metrics = MacosTheme.componentStyling.segmentedControl.metrics
    val verticalPadding = metrics.separatorVerticalPaddingFor(controlSize)
    val separatorHeight = trackHeight - verticalPadding * 2

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = macosSpring(SpringPreset.Snappy),
        label = "dividerAlpha",
    )
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(separatorHeight)
            .alpha(alpha)
            .background(color, RoundedCornerShape(0.5.dp)),
    )
}

// =============================================================================
// Preview
// =============================================================================

@Preview
@Composable
private fun SegmentedControlPreview() {
    MacosTheme {
        var selected by remember { mutableStateOf(0) }
        SegmentedControl(
            options = listOf("Day", "Week", "Month"),
            selectedIndex = selected,
            onSelectedIndexChange = { selected = it },
        )
    }
}
