package io.github.kdroidfilter.nucleus.ui.apple.macos.sample

import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import composemacosui.sample.generated.resources.jetbrains_mono_bold
import composemacosui.sample.generated.resources.jetbrains_mono_regular
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.preloadMacosResources
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.preloadFont
import composemacosui.sample.generated.resources.Res as SampleRes

@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
fun main() {
    ComposeViewport {
        val macosReady = preloadMacosResources()
        val jbMonoRegular by preloadFont(SampleRes.font.jetbrains_mono_regular)
        val jbMonoBold by preloadFont(SampleRes.font.jetbrains_mono_bold)

        val mdGettingStarted by produceState<ByteArray?>(null) {
            value = SampleRes.readBytes("files/getting_started.md")
        }
        val mdLicense by produceState<ByteArray?>(null) {
            value = SampleRes.readBytes("files/license.md")
        }

        val allReady = macosReady &&
            jbMonoRegular != null && jbMonoBold != null &&
            mdGettingStarted != null && mdLicense != null

        if (allReady) {
            App()
        }
    }
}
