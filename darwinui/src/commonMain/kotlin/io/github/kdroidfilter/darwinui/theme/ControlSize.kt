package io.github.kdroidfilter.darwinui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Control size presets inspired by SwiftUI's `.controlSize()`.
 *
 * Propagated via [LocalControlSize] and the [ControlSize] wrapper composable.
 * Components read the current size and delegate to their `Metrics.*For(controlSize)` resolvers.
 */
enum class ControlSize {
    Mini,
    Small,
    Regular,
    Large,
    ExtraLarge,
}

val LocalControlSize = compositionLocalOf { ControlSize.Regular }

/**
 * Propagates a [ControlSize] to all descendant Darwin UI components.
 *
 * Equivalent to SwiftUI's `.controlSize()` modifier.
 *
 * ```kotlin
 * ControlSize(ControlSize.Small) {
 *     Button(...)   // small
 *     TextField(...) // small
 * }
 * ```
 */
@Composable
fun ControlSize(size: ControlSize, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalControlSize provides size) { content() }
}

/**
 * Returns the appropriate label [TextStyle] for this control size.
 */
@Composable
@ReadOnlyComposable
fun ControlSize.labelStyle(): TextStyle = when (this) {
    ControlSize.Mini -> DarwinTheme.typography.caption2
    ControlSize.Small -> DarwinTheme.typography.caption1
    ControlSize.Regular -> DarwinTheme.typography.footnote
    ControlSize.Large -> DarwinTheme.typography.subheadline
    ControlSize.ExtraLarge -> DarwinTheme.typography.callout
}

/**
 * Returns the inline icon size for this control size, matching the SF Pro
 * glyph size at the corresponding font size.
 */
fun ControlSize.iconSize(): Dp = when (this) {
    ControlSize.Mini -> 10.dp
    ControlSize.Small -> 11.dp
    ControlSize.Regular -> 13.dp
    ControlSize.Large -> 13.dp
    ControlSize.ExtraLarge -> 13.dp
}

/**
 * Returns the gap between an inline icon and its adjacent text label.
 */
fun ControlSize.iconGap(): Dp = when (this) {
    ControlSize.Mini -> 2.dp
    ControlSize.Small -> 3.dp
    ControlSize.Regular -> 4.dp
    ControlSize.Large -> 4.dp
    ControlSize.ExtraLarge -> 4.dp
}
