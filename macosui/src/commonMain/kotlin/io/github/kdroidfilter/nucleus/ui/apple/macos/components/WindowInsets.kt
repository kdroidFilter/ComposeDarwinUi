package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Horizontal inset for the native window controls (traffic lights on macOS).
 *
 * When provided by a platform window composable (e.g. `MacosWindow`), the
 * [Scaffold] uses this value to pad its built-in sidebar toggle so it doesn't
 * overlap with the native window controls.
 *
 * Defaults to [Dp.Unspecified] (no inset).
 */
val LocalWindowControlInset = compositionLocalOf { Dp.Unspecified }
