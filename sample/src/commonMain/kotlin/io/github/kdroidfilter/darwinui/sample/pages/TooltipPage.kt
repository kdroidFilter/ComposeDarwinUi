package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.PushButton
import io.github.kdroidfilter.darwinui.components.Tooltip
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@GalleryExample("Tooltip", "Default")
@Composable
fun TooltipDefaultExample() {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Tooltip(text = "This is a tooltip!") { PushButton(text = "Hover me", onClick = {}) }
        Tooltip(text = "Another tooltip with more info") {
            PushButton(text = "More info", onClick = {})
        }
    }
}

@Composable
internal fun TooltipPage() {
    GalleryPage("Tooltip", "A popup that displays information related to an element on hover.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.TooltipDefaultExample) { TooltipDefaultExample() }
    }
}
