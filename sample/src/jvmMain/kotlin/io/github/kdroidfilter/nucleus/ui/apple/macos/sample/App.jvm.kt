package io.github.kdroidfilter.nucleus.ui.apple.macos.sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import io.github.kdroidfilter.nucleus.darkmodedetector.isSystemInDarkMode
import io.github.kdroidfilter.nucleus.systemcolor.systemAccentColor as nucleusSystemAccentColor

@Composable
internal actual fun BrowserNavigation(backStack: SnapshotStateList<AppNavKey>) = Unit

@Composable
internal actual fun isSystemDarkMode(): Boolean = isSystemInDarkMode()

@Composable
internal actual fun systemAccentRawColor(): Color? = nucleusSystemAccentColor()
