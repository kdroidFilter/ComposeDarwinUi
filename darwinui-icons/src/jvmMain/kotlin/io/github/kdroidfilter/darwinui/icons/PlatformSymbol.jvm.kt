package io.github.kdroidfilter.darwinui.icons

import androidx.compose.ui.graphics.ImageBitmap

private val isApplePlatform: Boolean =
    System.getProperty("os.name").orEmpty().lowercase().let { "mac" in it || "darwin" in it }

actual fun loadPlatformSymbol(name: String): ImageBitmap? {
    if (!isApplePlatform) return null
    return SFSymbolLoader.get(name)
}
