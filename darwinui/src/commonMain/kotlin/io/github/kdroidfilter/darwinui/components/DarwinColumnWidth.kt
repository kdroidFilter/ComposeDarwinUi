package io.github.kdroidfilter.darwinui.components

import androidx.compose.ui.unit.Dp

/**
 * Describes the width behavior of a column in [DarwinScaffold].
 *
 * [Fixed] columns have a constant width with no user resizing.
 * [Flexible] columns show a draggable divider and can be resized
 * between [Flexible.min] and [Flexible.max].
 */
sealed class DarwinColumnWidth {
    /** Column with a constant, non-resizable width. */
    data class Fixed(val width: Dp) : DarwinColumnWidth()

    /**
     * Column that can be resized by dragging its divider.
     *
     * @param min Minimum width during drag.
     * @param ideal Default width (restored on double-click).
     * @param max Maximum width during drag.
     */
    data class Flexible(
        val min: Dp,
        val ideal: Dp,
        val max: Dp,
    ) : DarwinColumnWidth()
}

/** Returns the effective default width: [DarwinColumnWidth.Fixed.width] or [DarwinColumnWidth.Flexible.ideal]. */
internal fun DarwinColumnWidth.idealOrFixed(): Dp = when (this) {
    is DarwinColumnWidth.Fixed -> width
    is DarwinColumnWidth.Flexible -> ideal
}
