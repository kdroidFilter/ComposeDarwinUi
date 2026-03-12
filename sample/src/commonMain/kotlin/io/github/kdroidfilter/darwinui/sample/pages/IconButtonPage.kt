package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.HelpButton
import io.github.kdroidfilter.darwinui.components.IconButton
import io.github.kdroidfilter.darwinui.components.PushButton
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideSettings
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@GalleryExample("IconButton", "Help")
@Composable
fun IconButtonHelpExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PushButton(text = "Avancé...", onClick = {})
        HelpButton(onClick = {})
    }
}

@GalleryExample("IconButton", "Icon")
@Composable
fun IconButtonIconExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = {}) {
            Icon(
                imageVector = LucideSettings,
                modifier = Modifier.size(14.dp),
            )
        }
        IconButton(onClick = {}, enabled = false) {
            Icon(
                imageVector = LucideSettings,
                modifier = Modifier.size(14.dp),
            )
        }
    }
}

@Composable
internal fun IconButtonPage() {
    GalleryPage("Icon Button", "macOS-native circular icon buttons with idiomatic Compose APIs.") {
        SectionHeader("Examples")
        ExampleCard(
            title = "Help Button",
            description = "Circular button with ? label, matching NSButton .helpButton bezel style",
            sourceCode = GallerySources.IconButtonHelpExample,
        ) { IconButtonHelpExample() }
        ExampleCard(
            title = "Icon Button",
            description = "Circular button wrapping any icon content",
            sourceCode = GallerySources.IconButtonIconExample,
        ) { IconButtonIconExample() }
    }
}
