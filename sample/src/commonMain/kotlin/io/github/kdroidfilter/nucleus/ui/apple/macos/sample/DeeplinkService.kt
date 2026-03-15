package io.github.kdroidfilter.nucleus.ui.apple.macos.sample

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DeeplinkService {
    private val state = MutableStateFlow("")
    val deepLink: StateFlow<String> get() = state

    fun setDeepLink(link: String) {
        state.value = link
    }

    companion object {
        private val validPages = sidebarEntryDefs.map { it.id }.toSet()

        fun pageToUrl(pageId: String): String? {
            return if (pageId in validPages) "#/$pageId" else null
        }

        fun urlToPage(url: String): String {
            val hash = url.substringAfter("#/", "")
            return if (hash in validPages) hash else "home"
        }
    }
}
