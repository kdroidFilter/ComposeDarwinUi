package io.github.kdroidfilter.darwinui.theme

import androidx.compose.runtime.Composable

/**
 * Platform-specific context menu override.
 *
 * On desktop (JVM), this provides a Darwin-styled context menu via
 * [LocalContextMenuRepresentation], replacing the default Compose context menu
 * for text fields and other components that use the platform context menu system.
 *
 * On other platforms, this is a no-op passthrough.
 */
@Composable
internal expect fun PlatformContextMenuOverride(content: @Composable () -> Unit)
