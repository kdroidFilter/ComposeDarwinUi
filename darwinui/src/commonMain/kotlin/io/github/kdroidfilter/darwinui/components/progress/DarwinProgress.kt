package io.github.kdroidfilter.darwinui.components.progress

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.theme.*

// ==================== Enums ====================

/**
 * Size variants for the linear progress bar.
 *
 * Maps to React Tailwind classes: sm → h-1, md → h-2, lg → h-3.
 */
enum class DarwinProgressSize(val height: Dp) {
    Sm(4.dp),   // h-1 = 4dp
    Md(8.dp),   // h-2 = 8dp
    Lg(12.dp),  // h-3 = 12dp
}

/**
 * Color variants for both linear and circular progress indicators.
 *
 * Pixel-perfect match with the React progress.tsx `variantClasses`:
 * - default → `bg-blue-500`
 * - success → `bg-emerald-500`
 * - warning → `bg-amber-500`
 * - danger  → `bg-red-500`
 * - gradient → `bg-linear-to-r from-blue-500 via-violet-500 to-red-500`
 */
enum class DarwinProgressVariant {
    Default,
    Success,
    Warning,
    Danger,
    Gradient,
}

// ==================== Color Helpers ====================

/**
 * Resolves the solid fill color for a given variant.
 * Pixel-perfect match with React Tailwind color classes.
 */
private fun variantColor(variant: DarwinProgressVariant): Color = when (variant) {
    DarwinProgressVariant.Default -> Blue500       // bg-blue-500
    DarwinProgressVariant.Success -> Emerald500    // bg-emerald-500
    DarwinProgressVariant.Warning -> Amber500      // bg-amber-500
    DarwinProgressVariant.Danger -> Red500         // bg-red-500
    DarwinProgressVariant.Gradient -> Blue500      // fallback; gradient handled separately
}

/**
 * Three-stop gradient brush matching React:
 * `bg-linear-to-r from-blue-500 via-violet-500 to-red-500`
 */
private fun gradientBrush(width: Float): Brush = Brush.linearGradient(
    colors = listOf(Blue500, Violet500, Red500),
    start = Offset.Zero,
    end = Offset(width, 0f),
)

// ==================== Linear Progress ====================

/**
 * Darwin UI Linear Progress — a horizontal progress bar.
 *
 * Pixel-perfect match with the React darwin-ui Progress component:
 * - Track: `bg-black/10 dark:bg-white/10` (or glass: `bg-white/40 dark:bg-zinc-900/40`)
 * - Shape: `rounded-full`
 * - Indeterminate: `w-1/3` bar sliding x -100% → 400% over 1.5s easeInOut
 * - Determinate: width 0 → percentage% over 0.4s easeOut
 * - showValue: `mt-1 text-right text-xs text-zinc-600 dark:text-zinc-400` (BELOW the bar)
 *
 * @param value Current progress value (0 to [max]).
 * @param max Maximum progress value.
 * @param size Size variant controlling bar height.
 * @param variant Color variant for the filled portion.
 * @param indeterminate When true, plays an infinite sliding animation ignoring [value].
 * @param showValue When true, displays the percentage below and right-aligned.
 * @param glass When true, uses a semi-transparent glass background for the track.
 * @param modifier Modifier applied to the root layout.
 */
@Composable
fun DarwinLinearProgress(
    value: Float = 0f,
    max: Float = 100f,
    size: DarwinProgressSize = DarwinProgressSize.Md,
    variant: DarwinProgressVariant = DarwinProgressVariant.Default,
    indeterminate: Boolean = false,
    showValue: Boolean = false,
    glass: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val isDark = DarwinTheme.colors.isDark

    // Track color: bg-black/10 dark:bg-white/10 | glass: bg-white/40 dark:bg-zinc-900/40
    val trackColor = if (glass) {
        if (isDark) Zinc900.copy(alpha = 0.40f) else Color.White.copy(alpha = 0.40f)
    } else {
        if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)
    }

    val fillColor = variantColor(variant)
    val useGradient = variant == DarwinProgressVariant.Gradient

    // Determinate: animate fill fraction (React: duration 0.4s, ease "easeOut")
    // CSS ease-out = cubic-bezier(0, 0, 0.58, 1)
    val targetFraction = if (indeterminate) 0f else (value / max).coerceIn(0f, 1f)
    val animatedFraction by animateFloatAsState(
        targetValue = targetFraction,
        animationSpec = tween(
            durationMillis = 400,
            easing = CubicBezierEasing(0f, 0f, 0.58f, 1f), // CSS ease-out
        ),
    )

    // Indeterminate: w-1/3 bar, x: ["-100%", "400%"], 1.5s easeInOut
    // Bar width = 1/3 of track. Translate -100% to 400% of bar width.
    // CSS translate(-100%) = -barWidth, translate(400%) = 4*barWidth
    // So bar left edge goes from -barWidth to 4*barWidth.
    // Normalized: animate from -1 to 4 (fractions of barWidth)
    val infiniteTransition = rememberInfiniteTransition(label = "progress_indeterminate")
    val indeterminateOffset by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1500,
                easing = FastOutSlowInEasing, // CSS easeInOut
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "indeterminate_offset",
    )

    val percentage = ((value / max) * 100f).coerceIn(0f, 100f).toInt()

    // React: showValue is a <div> below the bar with mt-1 text-right
    Column(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(size.height),
        ) {
            val cornerRadius = CornerRadius(this.size.height / 2f) // rounded-full

            // Background track
            drawRoundRect(
                color = trackColor,
                cornerRadius = cornerRadius,
                size = this.size,
            )

            if (indeterminate) {
                // Bar is 1/3 of track width
                val barWidth = this.size.width / 3f
                // startX = indeterminateOffset * barWidth
                val startX = indeterminateOffset * barWidth
                val clippedStart = startX.coerceAtLeast(0f)
                val clippedEnd = (startX + barWidth).coerceAtMost(this.size.width)
                val clippedWidth = (clippedEnd - clippedStart).coerceAtLeast(0f)

                if (clippedWidth > 0f) {
                    if (useGradient) {
                        drawRoundRect(
                            brush = gradientBrush(clippedWidth),
                            topLeft = Offset(clippedStart, 0f),
                            size = Size(clippedWidth, this.size.height),
                            cornerRadius = cornerRadius,
                        )
                    } else {
                        drawRoundRect(
                            color = fillColor,
                            topLeft = Offset(clippedStart, 0f),
                            size = Size(clippedWidth, this.size.height),
                            cornerRadius = cornerRadius,
                        )
                    }
                }
            } else {
                // Determinate fill
                val fillWidth = animatedFraction * this.size.width
                if (fillWidth > 0f) {
                    if (useGradient) {
                        drawRoundRect(
                            brush = gradientBrush(fillWidth),
                            cornerRadius = cornerRadius,
                            size = Size(fillWidth, this.size.height),
                        )
                    } else {
                        drawRoundRect(
                            color = fillColor,
                            cornerRadius = cornerRadius,
                            size = Size(fillWidth, this.size.height),
                        )
                    }
                }
            }
        }

        // Percentage label: mt-1 text-right text-xs text-zinc-600 dark:text-zinc-400
        if (showValue && !indeterminate) {
            Spacer(modifier = Modifier.height(4.dp)) // mt-1 = 4dp
            DarwinText(
                text = "$percentage%",
                style = DarwinTheme.typography.labelSmall, // text-xs
                color = if (isDark) Zinc400 else Zinc600,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

// ==================== Circular Progress ====================

/**
 * Darwin UI Circular Progress — a circular arc progress indicator.
 *
 * Pixel-perfect match with the React darwin-ui CircularProgress component:
 * - Track: `text-black/10 dark:text-white/10`
 * - Default size: 40px, strokeWidth: 4px
 * - SVG with `-rotate-90` (starts from top)
 * - Variants: default=blue-500, success=emerald-500, warning=amber-500, danger=red-500
 * - Indeterminate: strokeDashoffset [C, -C] + rotate [0, 360] over 2s linear
 * - Determinate: strokeDashoffset from C to offset over 0.4s easeOut
 * - showValue: `text-xs font-medium text-zinc-700 dark:text-zinc-300`
 *
 * @param value Current progress value (0 to [max]).
 * @param max Maximum progress value.
 * @param size Diameter of the circular indicator (React default: 40).
 * @param strokeWidth Width of the arc stroke (React default: 4).
 * @param variant Color variant for the progress arc.
 * @param indeterminate When true, plays an infinite animation ignoring [value].
 * @param showValue When true, displays the percentage in the center of the circle.
 * @param modifier Modifier applied to the container.
 */
@Composable
fun DarwinCircularProgress(
    value: Float = 0f,
    max: Float = 100f,
    size: Dp = 40.dp, // React default: size=40
    strokeWidth: Dp = 4.dp, // React default: strokeWidth=4
    variant: DarwinProgressVariant = DarwinProgressVariant.Default,
    indeterminate: Boolean = false,
    showValue: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val isDark = DarwinTheme.colors.isDark

    // Track color: text-black/10 dark:text-white/10
    val trackColor = if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)

    // Circular does not support gradient variant in React
    val fillColor = variantColor(variant)

    // Determinate: animate sweep (React: duration 0.4s, ease "easeOut")
    val targetFraction = if (indeterminate) 0f else (value / max).coerceIn(0f, 1f)
    val animatedFraction by animateFloatAsState(
        targetValue = targetFraction,
        animationSpec = tween(
            durationMillis = 400,
            easing = CubicBezierEasing(0f, 0f, 0.58f, 1f), // CSS ease-out
        ),
    )

    // Indeterminate: React animates strokeDashoffset [C, -C] and rotate [0, 360]
    // over 2s linear. This creates a drawing/erasing arc that also rotates.
    // We model this with a single 0→1 progress that maps to startAngle + sweepAngle.
    val infiniteTransition = rememberInfiniteTransition(label = "circular_indeterminate")
    val indeterminateProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "circular_progress",
    )

    val percentage = ((value / max) * 100f).coerceIn(0f, 100f).toInt()

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val stroke = Stroke(
                width = strokeWidth.toPx(),
                cap = StrokeCap.Round,
            )
            val arcSize = Size(
                width = this.size.width - stroke.width,
                height = this.size.height - stroke.width,
            )
            val topLeft = Offset(stroke.width / 2f, stroke.width / 2f)

            // Background circle track
            drawArc(
                color = trackColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = stroke,
            )

            if (indeterminate) {
                // Models the React SVG animation exactly:
                //   SVG parent: -rotate-90 (path starts at top)
                //   strokeDasharray: C, strokeDashoffset: [C, -C]
                //   rotate: [0, 360], duration: 2s linear
                //
                // Phase 1 (t<0.5): head races forward, tail follows slowly → arc grows
                // Phase 2 (t≥0.5): tail races forward, head slows down → arc shrinks
                val t = indeterminateProgress
                val startAngle: Float
                val sweepAngle: Float
                if (t < 0.5f) {
                    // Tail: -90+360t, Head: -90+1080t → sweep = 720t
                    startAngle = -90f + 360f * t
                    sweepAngle = 720f * t
                } else {
                    // Tail: -450+1080t, Head: 270+360t → sweep = 720(1-t)
                    startAngle = -450f + 1080f * t
                    sweepAngle = 720f * (1f - t)
                }

                drawArc(
                    color = fillColor,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle.coerceIn(0f, 360f),
                    useCenter = false,
                    topLeft = topLeft,
                    size = arcSize,
                    style = stroke,
                )
            } else {
                // Determinate arc: starts from top (-90°)
                val sweepAngle = animatedFraction * 360f
                if (sweepAngle > 0f) {
                    drawArc(
                        color = fillColor,
                        startAngle = -90f,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        topLeft = topLeft,
                        size = arcSize,
                        style = stroke,
                    )
                }
            }
        }

        // Center label: text-xs font-medium text-zinc-700 dark:text-zinc-300
        if (showValue && !indeterminate) {
            DarwinText(
                text = "$percentage%",
                style = DarwinTheme.typography.labelSmall,
                color = if (isDark) Zinc300 else Zinc700,
                textAlign = TextAlign.Center,
            )
        }
    }
}
