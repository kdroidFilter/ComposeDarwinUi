package io.github.kdroidfilter.nucleus.ui.apple.macos.sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import io.github.kdroidfilter.nucleus.darkmodedetector.isSystemInDarkMode

@Composable
internal actual fun BrowserNavigation(backStack: SnapshotStateList<AppNavKey>) = Unit

@Composable
internal actual fun isSystemDarkMode(): Boolean = isSystemInDarkMode()
