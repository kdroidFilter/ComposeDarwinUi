package io.github.kdroidfilter.nucleus.ui.apple.macos.sample

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color

@Composable
internal actual fun BrowserNavigation(backStack: SnapshotStateList<AppNavKey>) = Unit

@Composable
internal actual fun isSystemDarkMode(): Boolean = isSystemInDarkTheme()

@Composable
internal actual fun systemAccentRawColor(): Color? = null
