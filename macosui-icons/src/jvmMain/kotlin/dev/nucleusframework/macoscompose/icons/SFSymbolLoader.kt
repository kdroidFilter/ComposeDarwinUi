package dev.nucleusframework.macoscompose.icons

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image
import java.util.concurrent.ConcurrentHashMap

private const val MIN_SIZE_PX = 4
private const val MAX_SIZE_PX = 1024

internal object SFSymbolLoader {
    private val cache = ConcurrentHashMap<String, ImageBitmap>()
    private val existing = ConcurrentHashMap<String, Boolean>()

    fun exists(name: String): Boolean {
        if (name.isEmpty() || !NativeSFSymbolBridge.isLoaded) return false
        return existing.getOrPut(name) { NativeSFSymbolBridge.nativeExists(name) }
    }

    fun get(name: String, sizePx: Int): ImageBitmap? {
        if (!exists(name)) return null
        val size = sizePx.coerceIn(MIN_SIZE_PX, MAX_SIZE_PX)
        val key = "$name@$size"
        cache[key]?.let { return it }
        val png = NativeSFSymbolBridge.nativeLoadSymbol(name, size) ?: return null
        if (png.isEmpty()) return null
        val bitmap = Image.makeFromEncoded(png).toImageBitmap()
        cache[key] = bitmap
        return bitmap
    }

    private fun Image.toImageBitmap(): ImageBitmap {
        val bitmap = Bitmap()
        bitmap.allocPixels(imageInfo)
        readPixels(bitmap, 0, 0)
        return bitmap.asComposeImageBitmap()
    }
}
