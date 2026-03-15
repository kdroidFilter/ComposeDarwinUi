package io.github.kdroidfilter.nucleus.ui.apple.macos.sample

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.window

private val deeplink = DeeplinkService()

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    deeplink.setDeepLink(window.location.hash)
    window.addEventListener("hashchange", {
        deeplink.setDeepLink(window.location.hash)
    })
    ComposeViewport { App(deeplink) }
}
