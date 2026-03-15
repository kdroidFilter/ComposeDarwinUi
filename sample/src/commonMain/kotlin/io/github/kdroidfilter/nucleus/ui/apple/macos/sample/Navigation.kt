package io.github.kdroidfilter.nucleus.ui.apple.macos.sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey

internal sealed interface AppNavKey : NavKey

internal data object HomeScreen : AppNavKey

internal data class PageScreen(val id: String) : AppNavKey

internal fun AppNavKey.toPageId(): String = when (this) {
    is HomeScreen -> "home"
    is PageScreen -> id
}

internal fun pageIdToNavKey(id: String): AppNavKey = when (id) {
    "home" -> HomeScreen
    else -> PageScreen(id)
}

internal class NavigationState(
    val backStack: SnapshotStateList<AppNavKey>,
    private val forwardStack: SnapshotStateList<AppNavKey>,
) {
    val currentKey: AppNavKey get() = backStack.last()

    val currentPageId: String get() = currentKey.toPageId()

    val canGoBack: Boolean get() = backStack.size > 1

    val canGoForward: Boolean get() = forwardStack.isNotEmpty()

    fun navigateTo(key: AppNavKey) {
        if (backStack.lastOrNull() != key) {
            backStack.add(key)
            forwardStack.clear()
        }
    }

    fun navigateTo(pageId: String) {
        navigateTo(pageIdToNavKey(pageId))
    }

    fun goBack() {
        if (canGoBack) {
            forwardStack.add(backStack.removeLast())
        }
    }

    fun goForward() {
        if (canGoForward) {
            backStack.add(forwardStack.removeLast())
        }
    }
}
