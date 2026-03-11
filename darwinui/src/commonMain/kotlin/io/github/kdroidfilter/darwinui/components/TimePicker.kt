package io.github.kdroidfilter.darwinui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

// =============================================================================
// TimePickerState
// =============================================================================

/**
 * State holder for [TimePicker] and [TimeInput].
 *
 * @param initialHour   Initial hour value (0–23).
 * @param initialMinute Initial minute value (0–59).
 * @param is24hour      When true, hours range 0–23; when false, 1–12 with AM/PM.
 */
class TimePickerState(
    initialHour: Int = 0,
    initialMinute: Int = 0,
    val is24hour: Boolean = true,
) {
    var hour: Int by mutableStateOf(initialHour.coerceIn(0, 23))
    var minute: Int by mutableStateOf(initialMinute.coerceIn(0, 59))
}

@Composable
fun rememberTimePickerState(
    initialHour: Int = 0,
    initialMinute: Int = 0,
    is24hour: Boolean = true,
): TimePickerState = remember { TimePickerState(initialHour, initialMinute, is24hour) }

// =============================================================================
// TimePickerColors
// =============================================================================

@Immutable
class TimePickerColors(
    val containerColor: Color,
    val timeSelectorContainerColor: Color,
    val timeSelectorContentColor: Color,
    val periodSelectorBorderColor: Color,
)

// =============================================================================
// TimePickerLayoutType
// =============================================================================

enum class TimePickerLayoutType {
    Vertical,
    Horizontal,
}

// =============================================================================
// TimePickerDefaults
// =============================================================================

object TimePickerDefaults {
    val containerColor: Color
        @Composable get() = DarwinTheme.colorScheme.surfaceContainerHigh

    val timeSelectorContainerColor: Color
        @Composable get() = DarwinTheme.colorScheme.primaryContainer

    val timeSelectorContentColor: Color
        @Composable get() = DarwinTheme.colorScheme.onPrimaryContainer

    @Composable
    fun colors(
        containerColor: Color = this.containerColor,
        timeSelectorContainerColor: Color = this.timeSelectorContainerColor,
        timeSelectorContentColor: Color = this.timeSelectorContentColor,
        periodSelectorBorderColor: Color = DarwinTheme.colorScheme.outline,
    ): TimePickerColors = TimePickerColors(
        containerColor = containerColor,
        timeSelectorContainerColor = timeSelectorContainerColor,
        timeSelectorContentColor = timeSelectorContentColor,
        periodSelectorBorderColor = periodSelectorBorderColor,
    )
}

// =============================================================================
// Internal constants
// =============================================================================

private val ItemHeight = 40.dp
private const val VisibleItems = 5
private const val ScrollListSize = 1000

// Maximum rotationX angle for items at the edge of the wheel (in degrees)
private const val MaxRotationX = 60f

// =============================================================================
// WheelColumn — reusable scroll-wheel column with iOS barrel distortion
// =============================================================================

/**
 * A single infinite-scroll wheel column with an iOS-style barrel/cylinder effect.
 *
 * @param count          Number of distinct values (e.g. 24 for hours).
 * @param selectedIndex  Currently selected value index (0-based).
 * @param onIndexSelected Callback invoked after scroll snaps to a new index.
 * @param label          Maps a value index to its display string.
 * @param colors         Theme colors for the selection highlight.
 * @param distortion     Barrel distortion strength: 0f = flat list, 1f = full iOS cylinder effect.
 */
@Composable
private fun WheelColumn(
    count: Int,
    selectedIndex: Int,
    onIndexSelected: (Int) -> Unit,
    label: (Int) -> String,
    colors: TimePickerColors,
    modifier: Modifier = Modifier,
    distortion: Float = 1f,
) {
    val initialFirstVisible = (ScrollListSize / 2) - (ScrollListSize / 2 % count) + selectedIndex - (VisibleItems / 2)
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialFirstVisible.coerceAtLeast(0))
    val density = LocalDensity.current
    val itemHeightPx = with(density) { ItemHeight.toPx() }
    val halfVisiblePx = itemHeightPx * VisibleItems / 2f

    // Snap to nearest item when scrolling stops
    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }
            .distinctUntilChanged()
            .filter { !it }
            .collect {
                val centerIndex = listState.firstVisibleItemIndex + VisibleItems / 2
                val snappedValue = centerIndex % count
                listState.animateScrollToItem(centerIndex - VisibleItems / 2)
                onIndexSelected(snappedValue)
            }
    }

    val highlightShape = RoundedCornerShape(10.dp)
    val clampedDistortion = distortion.coerceIn(0f, 1f)

    Box(
        modifier = modifier
            .height(ItemHeight * VisibleItems)
            .wrapContentWidth(),
        contentAlignment = Alignment.Center,
    ) {
        // Center selection highlight
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .height(ItemHeight)
                .fillMaxWidth()
                .clip(highlightShape)
                .background(colors.timeSelectorContainerColor, highlightShape),
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.height(ItemHeight * VisibleItems),
            userScrollEnabled = true,
        ) {
            items(ScrollListSize) { globalIndex ->
                val value = globalIndex % count

                // Pixel offset of this item's center from the viewport center
                val scrollOffset = listState.firstVisibleItemScrollOffset.toFloat()
                val itemTopPx = (globalIndex - listState.firstVisibleItemIndex) * itemHeightPx - scrollOffset
                val itemCenterPx = itemTopPx + itemHeightPx / 2f
                // Normalized distance: -1 at top edge, 0 at center, +1 at bottom edge
                val normalizedOffset = ((itemCenterPx - halfVisiblePx) / halfVisiblePx).coerceIn(-1f, 1f)
                val absOffset = kotlin.math.abs(normalizedOffset)

                // Barrel transforms, scaled by distortion
                val rotationX = normalizedOffset * MaxRotationX * clampedDistortion
                val scale = 1f - absOffset * 0.35f * clampedDistortion
                val alpha = (1f - absOffset * 0.7f * clampedDistortion).coerceIn(0.1f, 1f)
                // Compress items toward center to simulate cylinder curvature
                val translationY = normalizedOffset * absOffset * itemHeightPx * 0.2f * clampedDistortion

                val isCenter = absOffset < 0.3f

                Box(
                    modifier = Modifier
                        .height(ItemHeight)
                        .fillMaxWidth()
                        .graphicsLayer {
                            this.rotationX = -rotationX
                            this.scaleX = scale
                            this.scaleY = scale
                            this.alpha = alpha
                            this.translationY = translationY
                            this.cameraDistance = 12f * density.density
                            this.transformOrigin = TransformOrigin.Center
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = label(value),
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = if (isCenter) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isCenter) colors.timeSelectorContentColor else DarwinTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                        ),
                    )
                }
            }
        }
    }
}

// =============================================================================
// TimePicker — public scroll-wheel composable
// =============================================================================

/**
 * iOS-style scroll-wheel time picker with barrel distortion effect.
 *
 * @param state       Time picker state holding the current hour and minute.
 * @param modifier    Modifier for the outer container.
 * @param colors      Theme colors for the picker.
 * @param layoutType  Layout type (accepted for M3 API parity).
 * @param distortion  Barrel distortion strength: 0f = flat list, 1f = full iOS cylinder effect.
 */
@Composable
fun TimePicker(
    state: TimePickerState,
    modifier: Modifier = Modifier,
    colors: TimePickerColors = TimePickerDefaults.colors(),
    layoutType: TimePickerLayoutType = TimePickerLayoutType.Vertical,
    distortion: Float = 1f,
) {
    val containerShape = RoundedCornerShape(16.dp)

    val hourCount = if (state.is24hour) 24 else 12
    // For 12h mode, display values are 1–12 but stored indices are 0–11 (index 0 = "12")
    val hourLabel: (Int) -> String = if (state.is24hour) {
        { it.toString().padStart(2, '0') }
    } else {
        { if (it == 0) "12" else it.toString().padStart(2, '0') }
    }

    // Derive scroll index from state.hour
    val hourScrollIndex = if (state.is24hour) {
        state.hour
    } else {
        state.hour % 12 // 0 represents 12 in 12h mode
    }

    // Track AM/PM separately in 12h mode
    var isPm by remember { mutableStateOf(state.hour >= 12) }

    Box(
        modifier = modifier
            .clip(containerShape)
            .background(colors.containerColor, containerShape)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            // Hour wheel
            WheelColumn(
                count = hourCount,
                selectedIndex = hourScrollIndex,
                onIndexSelected = { idx ->
                    state.hour = if (state.is24hour) {
                        idx
                    } else {
                        val base = if (idx == 0) 12 else idx
                        if (isPm) (base % 12) + 12 else base % 12
                    }
                },
                label = hourLabel,
                colors = colors,
                modifier = Modifier.width(72.dp),
                distortion = distortion,
            )

            // Separator
            Text(
                text = ":",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarwinTheme.colorScheme.onSurface,
                ),
                modifier = Modifier.padding(horizontal = 4.dp),
            )

            // Minute wheel
            WheelColumn(
                count = 60,
                selectedIndex = state.minute,
                onIndexSelected = { state.minute = it },
                label = { it.toString().padStart(2, '0') },
                colors = colors,
                modifier = Modifier.width(72.dp),
                distortion = distortion,
            )

            // AM/PM column for 12h mode
            if (!state.is24hour) {
                PeriodColumn(
                    isPm = isPm,
                    onPeriodSelected = { pm ->
                        isPm = pm
                        // Recompute hour when period flips
                        val h12 = state.hour % 12
                        state.hour = if (pm) h12 + 12 else h12
                    },
                    colors = colors,
                    modifier = Modifier.width(60.dp),
                )
            }
        }
    }
}

// =============================================================================
// PeriodColumn — AM/PM selector used in 12h mode
// =============================================================================

@Composable
private fun PeriodColumn(
    isPm: Boolean,
    onPeriodSelected: (Boolean) -> Unit,
    colors: TimePickerColors,
    modifier: Modifier = Modifier,
) {
    val periodShape = RoundedCornerShape(10.dp)

    Column(
        modifier = modifier
            .height(ItemHeight * VisibleItems)
            .clip(periodShape)
            .border(1.dp, colors.periodSelectorBorderColor, periodShape),
        verticalArrangement = Arrangement.Center,
    ) {
        listOf("AM" to false, "PM" to true).forEach { (label, pm) ->
            val selected = isPm == pm
            val interactionSource = remember { MutableInteractionSource() }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(periodShape)
                    .background(
                        if (selected) colors.timeSelectorContainerColor else Color.Transparent,
                        periodShape,
                    )
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource,
                    ) { onPeriodSelected(pm) },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = label,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (selected) colors.timeSelectorContentColor else DarwinTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                    ),
                )
            }
        }
    }
}

// =============================================================================
// TimeInput — keyboard-based time entry
// =============================================================================

/**
 * Text-field based time input with two styled boxes (HH and MM) separated by `:`.
 * Each field validates and clamps input to valid ranges.
 */
@Composable
fun TimeInput(
    state: TimePickerState,
    modifier: Modifier = Modifier,
    colors: TimePickerColors = TimePickerDefaults.colors(),
) {
    val colorScheme = DarwinTheme.colorScheme
    val fieldShape = RoundedCornerShape(10.dp)

    var hourText by remember { mutableStateOf(state.hour.toString().padStart(2, '0')) }
    var minuteText by remember { mutableStateOf(state.minute.toString().padStart(2, '0')) }

    // Keep text in sync if state changes externally
    LaunchedEffect(state.hour) {
        val formatted = state.hour.toString().padStart(2, '0')
        if (hourText != formatted) hourText = formatted
    }
    LaunchedEffect(state.minute) {
        val formatted = state.minute.toString().padStart(2, '0')
        if (minuteText != formatted) minuteText = formatted
    }

    val fieldTextStyle = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.SemiBold,
        color = colors.timeSelectorContentColor,
        textAlign = TextAlign.Center,
    )

    val fieldModifier = Modifier
        .width(96.dp)
        .height(72.dp)
        .clip(fieldShape)
        .background(colors.timeSelectorContainerColor, fieldShape)

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // Hour field
        BasicTextField(
            value = hourText,
            onValueChange = { raw ->
                val digits = raw.filter { it.isDigit() }.take(2)
                hourText = digits
                val h = digits.toIntOrNull()
                if (h != null) {
                    val maxHour = if (state.is24hour) 23 else 12
                    state.hour = h.coerceIn(0, maxHour)
                }
            },
            modifier = fieldModifier,
            textStyle = fieldTextStyle,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            cursorBrush = SolidColor(colorScheme.primary),
            decorationBox = { inner ->
                Box(contentAlignment = Alignment.Center) {
                    inner()
                }
            },
        )

        // Separator
        Text(
            text = ":",
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = colorScheme.onSurface,
            ),
        )

        // Minute field
        BasicTextField(
            value = minuteText,
            onValueChange = { raw ->
                val digits = raw.filter { it.isDigit() }.take(2)
                minuteText = digits
                val m = digits.toIntOrNull()
                if (m != null) {
                    state.minute = m.coerceIn(0, 59)
                }
            },
            modifier = fieldModifier,
            textStyle = fieldTextStyle,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            cursorBrush = SolidColor(colorScheme.primary),
            decorationBox = { inner ->
                Box(contentAlignment = Alignment.Center) {
                    inner()
                }
            },
        )

        // AM/PM selector for 12h mode
        if (!state.is24hour) {
            var isPm by remember { mutableStateOf(state.hour >= 12) }
            val periodShape = RoundedCornerShape(10.dp)

            Column(
                modifier = Modifier
                    .height(72.dp)
                    .width(52.dp)
                    .clip(periodShape)
                    .border(1.dp, colors.periodSelectorBorderColor, periodShape),
                verticalArrangement = Arrangement.Center,
            ) {
                listOf("AM" to false, "PM" to true).forEach { (label, pm) ->
                    val selected = isPm == pm
                    val interactionSource = remember { MutableInteractionSource() }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(
                                if (selected) colors.timeSelectorContainerColor else Color.Transparent,
                                periodShape,
                            )
                            .clickable(
                                indication = null,
                                interactionSource = interactionSource,
                            ) {
                                isPm = pm
                                val h12 = state.hour % 12
                                state.hour = if (pm) h12 + 12 else h12
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = label,
                            style = TextStyle(
                                fontSize = 11.sp,
                                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (selected) colors.timeSelectorContentColor else DarwinTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center,
                            ),
                        )
                    }
                }
            }
        }
    }
}
