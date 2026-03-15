package io.github.kdroidfilter.nucleus.ui.apple.macos.sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.browser.window

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
@Composable
internal actual fun BrowserNavigation(currentPage: String) {
    LaunchedEffect(currentPage) {
        val url = DeeplinkService.pageToUrl(currentPage)
        if (url != null && window.location.hash != url) {
            window.history.replaceState(null, "", url)
        }
    }
}
