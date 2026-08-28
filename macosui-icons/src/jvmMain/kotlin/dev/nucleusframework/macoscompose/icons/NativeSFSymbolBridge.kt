package dev.nucleusframework.macoscompose.icons

import dev.nucleusframework.core.runtime.NativeLibraryLoader

private const val LIBRARY_NAME = "nucleus_sfsymbols"

internal object NativeSFSymbolBridge {
    private val loaded = NativeLibraryLoader.load(LIBRARY_NAME, NativeSFSymbolBridge::class.java)

    val isLoaded: Boolean get() = loaded

    @JvmStatic
    external fun nativeExists(name: String): Boolean

    @JvmStatic
    external fun nativeLoadSymbol(name: String, sizePx: Int): ByteArray?
}
