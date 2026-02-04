package io.github.kdroidfilter.darwinui.components.skeleton

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.Zinc900

// ===========================================================================
// DarwinSkeleton
// ===========================================================================

@Composable
fun DarwinSkeleton(
    modifier: Modifier = Modifier,
    shape: Shape = DarwinTheme.shapes.small, // rounded-lg = 8dp
    glass: Boolean = false,
) {
    val isDark = DarwinTheme.colors.isDark

    val backgroundColor = if (glass) {
        // glass: bg-white/40 dark:bg-zinc-900/40
        if (isDark) Zinc900.copy(alpha = 0.40f) else Color.White.copy(alpha = 0.40f)
    } else {
        // default: bg-black/10 dark:bg-white/10
        if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)
    }

    // CSS animate-pulse: opacity oscillates 1.0 → 0.5 → 1.0 over 2s
    val infiniteTransition = rememberInfiniteTransition(label = "skeleton_pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = androidx.compose.animation.core.LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "skeleton_alpha",
    )

    Box(
        modifier = modifier
            .clip(shape)
            .alpha(alpha)
            .background(backgroundColor, shape)
    )
}

// ===========================================================================
// Convenience composables
// ===========================================================================

private val SkeletonLineWidthFractions = listOf(1f, 0.75f, 0.5f)

/**
 * Shows multiple skeleton lines of varying widths to simulate loading text.
 *
 * @param lines    Number of skeleton lines to display. Defaults to 3.
 * @param modifier Modifier applied to the outer column container.
 * @param glass    When true, applies the glass-morphism effect.
 */
@Composable
fun DarwinSkeletonText(
    lines: Int = 3,
    modifier: Modifier = Modifier,
    glass: Boolean = false,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp), // space-y-2 = 8dp
    ) {
        for (i in 0 until lines) {
            val widthFraction = SkeletonLineWidthFractions[i % SkeletonLineWidthFractions.size]
            DarwinSkeleton(
                modifier = Modifier
                    .fillMaxWidth(widthFraction)
                    .height(16.dp), // h-4 = 16dp
                glass = glass,
            )
        }
    }
}

/**
 * A circular skeleton placeholder, typically used as a loading state for
 * avatars or profile pictures.
 *
 * @param size     Diameter of the circle. Defaults to 48dp (h-12 w-12).
 * @param modifier Modifier applied to the skeleton.
 * @param glass    When true, applies the glass-morphism effect.
 */
@Composable
fun DarwinSkeletonCircle(
    size: Dp = 48.dp, // h-12 w-12 = 48dp
    modifier: Modifier = Modifier,
    glass: Boolean = false,
) {
    DarwinSkeleton(
        modifier = modifier.size(size),
        shape = CircleShape,
        glass = glass,
    )
}
