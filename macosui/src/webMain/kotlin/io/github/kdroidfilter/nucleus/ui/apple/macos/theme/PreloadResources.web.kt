package io.github.kdroidfilter.nucleus.ui.apple.macos.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import composemacosui.macosui.generated.resources.Res
import composemacosui.macosui.generated.resources.manrope_bold
import composemacosui.macosui.generated.resources.manrope_medium
import composemacosui.macosui.generated.resources.manrope_regular
import composemacosui.macosui.generated.resources.manrope_semibold
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.preloadFont

@OptIn(ExperimentalResourceApi::class)
@Composable
fun preloadMacosResources(): Boolean {
    val regular by preloadFont(Res.font.manrope_regular)
    val medium by preloadFont(Res.font.manrope_medium)
    val semibold by preloadFont(Res.font.manrope_semibold)
    val bold by preloadFont(Res.font.manrope_bold)
    return regular != null && medium != null && semibold != null && bold != null
}
