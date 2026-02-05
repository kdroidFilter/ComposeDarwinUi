package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.ContextMenu
import io.github.kdroidfilter.darwinui.components.ContextMenuItem
import io.github.kdroidfilter.darwinui.components.ContextMenuSeparator
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@GalleryExample("ContextMenu", "Default")
@Composable
fun ContextMenuDefaultExample() {
    ContextMenu(
        trigger = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = DarwinTheme.colors.border,
                        shape = DarwinTheme.shapes.large,
                    )
                    .clip(DarwinTheme.shapes.large)
                    .padding(32.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Right-click here to open context menu",
                    style = DarwinTheme.typography.bodyMedium,
                    color = DarwinTheme.colors.mutedForeground,
                )
            }
        },
    ) {
        ContextMenuItem(onSelect = {}) { Text("Cut") }
        ContextMenuItem(onSelect = {}) { Text("Copy") }
        ContextMenuItem(onSelect = {}) { Text("Paste") }
        ContextMenuSeparator()
        ContextMenuItem(onSelect = {}, destructive = true) { Text("Delete") }
    }
}

@Composable
internal fun ContextMenuPage() {
    GalleryPage("Context Menu", "A menu triggered by right-clicking on an element.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.ContextMenuDefaultExample) { ContextMenuDefaultExample() }
    }
}
