package io.github.kdroidfilter.nucleus.ui.apple.macos.sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.terrakok.navigation3.browser.ChronologicalBrowserNavigation
import com.github.terrakok.navigation3.browser.buildBrowserHistoryFragment
import com.github.terrakok.navigation3.browser.getBrowserHistoryFragmentName
import com.github.terrakok.navigation3.browser.getBrowserHistoryFragmentParameters

@Composable
internal actual fun BrowserNavigation(backStack: SnapshotStateList<AppNavKey>) {
    ChronologicalBrowserNavigation(
        backStack = backStack,
        saveKey = { key ->
            buildBrowserHistoryFragment(key.toPageId())
        },
        restoreKey = { fragment ->
            val name = getBrowserHistoryFragmentName(fragment) ?: return@ChronologicalBrowserNavigation null
            if (name == "home" || sidebarEntryDefs.any { it.id == name }) {
                pageIdToNavKey(name)
            } else {
                null
            }
        },
    )
}
