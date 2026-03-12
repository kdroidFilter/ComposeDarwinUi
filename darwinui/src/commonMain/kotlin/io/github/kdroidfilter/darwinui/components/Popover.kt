package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.darwinGlass

// Positions the popup above its anchor, left-aligned
private object AboveAnchorPositionProvider : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize,
    ): IntOffset = IntOffset(
        x = anchorBounds.left,
        y = anchorBounds.top - popupContentSize.height,
    )
}

private val TailHeight = 10.dp
private val TailWidth = 28.dp
private val CornerRadius = 12.dp

/**
 * Popover bubble shape — rounded rectangle with a downward-pointing tail at the bottom-left.
 *
 * The tail is positioned immediately after the bottom-left corner arc.
 * [size.height] is expected to include the tail height.
 */
private class PopoverBubbleShape(
    private val cornerRadius: Dp,
    private val tailWidth: Dp,
    private val tailHeight: Dp,
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline = Outline.Generic(buildPath(size, density))

    private fun buildPath(size: Size, density: Density): Path = with(density) {
        val r = cornerRadius.toPx()
        val th = tailHeight.toPx()
        val tw = tailWidth.toPx()

        // Tail is anchored right after the bottom-left corner
        val tailLeft = r
        val tailMid = r + tw / 2f
        val tailRight = r + tw

        val w = size.width
        val bh = size.height - th  // bubble body height

        Path().apply {
            // Top-left corner
            moveTo(r, 0f)

            // Top edge
            lineTo(w - r, 0f)

            // Top-right corner
            arcTo(
                rect = Rect(w - 2 * r, 0f, w, 2 * r),
                startAngleDegrees = -90f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false,
            )

            // Right edge
            lineTo(w, bh - r)

            // Bottom-right corner
            arcTo(
                rect = Rect(w - 2 * r, bh - 2 * r, w, bh),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false,
            )

            // Bottom edge — right portion to tail right
            lineTo(tailRight, bh)

            // Tail: right side curving down to peak
            cubicTo(
                x1 = tailRight,          y1 = bh + th * 0.4f,
                x2 = tailMid + 2f,       y2 = bh + th * 0.95f,
                x3 = tailMid,            y3 = bh + th,
            )

            // Tail: left side curving back up from peak
            cubicTo(
                x1 = tailMid - 2f,       y1 = bh + th * 0.95f,
                x2 = tailLeft,           y2 = bh + th * 0.4f,
                x3 = tailLeft,           y3 = bh,
            )

            // Bottom-left corner — starts at (r, bh), ends at (0, bh - r)
            arcTo(
                rect = Rect(0f, bh - 2 * r, 2 * r, bh),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false,
            )

            // Left edge
            lineTo(0f, r)

            // Top-left corner
            arcTo(
                rect = Rect(0f, 0f, 2 * r, 2 * r),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false,
            )

            close()
        }
    }
}

/**
 * A click-triggered popover component for Darwin UI.
 *
 * Displays an arbitrary content panel anchored to a trigger element. The popover
 * appears when [expanded] is true and dismisses when the user clicks outside of it.
 *
 * @param expanded Whether the popover is currently visible.
 * @param onDismissRequest Callback invoked when the user clicks outside the popover
 *   to dismiss it.
 * @param modifier Modifier applied to the root container.
 * @param trigger The composable element that acts as the popover anchor. Typically
 *   a button that toggles [expanded].
 * @param content The composable content displayed inside the popover panel.
 */
@Composable
fun Popover(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    trigger: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val colors = DarwinTheme.colors

    val fallbackBg = colors.card
    val borderColor = colors.border
    val bubbleShape = remember { PopoverBubbleShape(CornerRadius, TailWidth, TailHeight) }

    Box(modifier = modifier) {
        trigger()

        if (expanded) {
            Popup(
                popupPositionProvider = AboveAnchorPositionProvider,
                onDismissRequest = onDismissRequest,
                properties = PopupProperties(focusable = true),
            ) {
                AnimatedVisibility(
                    visible = expanded,
                    enter = fadeIn(animationSpec = tween(150)) +
                            scaleIn(
                                initialScale = 0.95f,
                                transformOrigin = TransformOrigin(0f, 1f),
                                animationSpec = tween(150),
                            ),
                    exit = fadeOut(animationSpec = tween(100)) +
                            scaleOut(
                                targetScale = 0.95f,
                                transformOrigin = TransformOrigin(0f, 1f),
                                animationSpec = tween(100),
                            ),
                ) {
                    Box(
                        modifier = Modifier
                            .shadow(
                                elevation = 8.dp,
                                shape = bubbleShape,
                                ambientColor = androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.5f),
                                spotColor = androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.15f),
                            )
                            .darwinGlass(shape = bubbleShape, fallbackColor = fallbackBg)
                            .border(
                                width = 1.dp,
                                color = borderColor,
                                shape = bubbleShape,
                            )
                            .widthIn(min = 200.dp)
                            .padding(
                                start = 16.dp,
                                top = 16.dp,
                                end = 16.dp,
                                bottom = 16.dp + TailHeight,
                            ),
                    ) {
                        content()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PopoverPreview() {
    DarwinTheme {
        var expanded by remember { mutableStateOf(false) }
        Popover(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            trigger = {
                PushButton(onClick = { expanded = !expanded }) { Text("Open Popover") }
            },
        ) {
            Text("Popover content")
        }
    }
}
