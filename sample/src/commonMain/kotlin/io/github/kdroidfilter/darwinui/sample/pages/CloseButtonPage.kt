package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.closebutton.DarwinCloseButton
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("CloseButton", "Sizes")
@Composable
fun CloseButtonSizesExample() {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { DarwinCloseButton(onClick = {}, size = 10.dp); DarwinText("10dp", color = DarwinTheme.colors.textTertiary, style = DarwinTheme.typography.bodySmall) }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { DarwinCloseButton(onClick = {}, size = 12.dp); DarwinText("12dp (default)", color = DarwinTheme.colors.textTertiary, style = DarwinTheme.typography.bodySmall) }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { DarwinCloseButton(onClick = {}, size = 16.dp); DarwinText("16dp", color = DarwinTheme.colors.textTertiary, style = DarwinTheme.typography.bodySmall) }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { DarwinCloseButton(onClick = {}, size = 20.dp); DarwinText("20dp", color = DarwinTheme.colors.textTertiary, style = DarwinTheme.typography.bodySmall) }
    }
}

@Composable
internal fun CloseButtonPage() {
    GalleryPage("Close Button", "A macOS-style red close button with hover X icon.") {
        SectionHeader("Examples")
        ExampleCard(title = "Sizes", sourceCode = GallerySources.CloseButtonSizesExample) { CloseButtonSizesExample() }
    }
}
