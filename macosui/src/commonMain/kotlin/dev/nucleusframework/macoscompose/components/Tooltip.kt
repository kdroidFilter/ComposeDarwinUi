package dev.nucleusframework.macoscompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import dev.nucleusframework.macoscompose.components.Text
import dev.nucleusframework.macoscompose.theme.MacosTheme
import dev.nucleusframework.macoscompose.theme.Zinc100
import dev.nucleusframework.macoscompose.theme.Zinc900
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A hover-triggered tooltip component for macOS UI.
 *
 * Displays a small informational popup near the trigger element when the user
 * hovers over it (or long-presses on touch devices). The tooltip appears after
 * a configurable delay and dismisses when the pointer exits the trigger area.
 *
 * The tooltip uses an inverted color scheme relative to the current theme:
 * dark background with light text in light mode, and light background with
 * dark text in dark mode.
 *
 * @param text The text content to display in the tooltip.
 * @param delayMillis The delay in milliseconds before showing the tooltip after
 *   hover begins. Defaults to 500ms.
 * @param modifier Modifier applied to the trigger wrapper.
 * @param content The trigger element that activates the tooltip on hover.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Tooltip(
    text: String,
    delayMillis: Long = 500L,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val colors = MacosTheme.colorScheme
    val typography = MacosTheme.typography
    val shapes = MacosTheme.shapes

    var showTooltip by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    val positionProvider = remember(density) { TooltipPositionProvider(density) }

    val tooltipBackground = if (colors.isDark) Zinc100 else Zinc900
    val tooltipTextColor = if (colors.isDark) Zinc900 else Zinc100

    Box(
        modifier = modifier
            .pointerInput(delayMillis) {
                coroutineScope {
                    var showJob: Job? = null
                    awaitPointerEventScope {
                        val pass = PointerEventPass.Main
                        while (true) {
                            val event = awaitPointerEvent(pass)
                            val inputType = event.changes.firstOrNull()?.type
                            if (inputType == PointerType.Mouse) {
                                when (event.type) {
                                    PointerEventType.Enter -> {
                                        showJob?.cancel()
                                        showJob = launch {
                                            delay(delayMillis)
                                            showTooltip = true
                                        }
                                    }
                                    PointerEventType.Exit -> {
                                        showJob?.cancel()
                                        showJob = null
                                        showTooltip = false
                                    }
                                }
                            }
                        }
                    }
                }
            },
    ) {
        content()

        if (showTooltip) {
            Popup(
                popupPositionProvider = positionProvider,
                properties = PopupProperties(
                    focusable = false,
                ),
            ) {
                Box(
                    modifier = Modifier
                        .shadow(
                            elevation = 4.dp,
                            shape = shapes.small,
                        )
                        .clip(shapes.small)
                        .background(tooltipBackground, shapes.small)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                ) {
                    BasicText(
                        text = text,
                        style = typography.caption1.merge(
                            TextStyle(color = tooltipTextColor)
                        ),
                    )
                }
            }
        }
    }
}

/**
 * Places the tooltip fully outside the anchor so it cannot sit under the cursor
 * and retrigger hover Enter/Exit on desktop.
 */
private class TooltipPositionProvider(
    private val density: Density,
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize,
    ): IntOffset {
        val gap = with(density) { 8.dp.roundToPx() }
        val maxX = (windowSize.width - popupContentSize.width).coerceAtLeast(0)
        val x = (anchorBounds.left + (anchorBounds.width - popupContentSize.width) / 2)
            .coerceIn(0, maxX)
        val yBelow = anchorBounds.bottom + gap
        val y = if (yBelow + popupContentSize.height <= windowSize.height) {
            yBelow
        } else {
            (anchorBounds.top - gap - popupContentSize.height).coerceAtLeast(0)
        }
        return IntOffset(x, y)
    }
}

@Preview
@Composable
private fun TooltipPreview() {
    MacosTheme {
        Tooltip(text = "Helpful tooltip") {
            Text("Hover me")
        }
    }
}
