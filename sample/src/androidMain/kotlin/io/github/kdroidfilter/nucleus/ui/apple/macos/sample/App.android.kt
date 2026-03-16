package io.github.kdroidfilter.nucleus.ui.apple.macos.sample

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList

@Composable
internal actual fun BrowserNavigation(backStack: SnapshotStateList<AppNavKey>) = Unit

@Composable
internal actual fun isSystemDarkMode(): Boolean = isSystemInDarkTheme()
