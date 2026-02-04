package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.button.DarwinButton
import io.github.kdroidfilter.darwinui.components.card.DarwinCard
import io.github.kdroidfilter.darwinui.components.card.DarwinCardContent
import io.github.kdroidfilter.darwinui.components.reveal.DarwinRevealOnce
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@GalleryExample("Reveal", "Reveal Once")
@Composable
fun RevealOnceExample() {
    var showReveal by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DarwinButton(text = if (showReveal) "Reset" else "Show Reveal", onClick = { showReveal = !showReveal })
        if (showReveal) { DarwinRevealOnce { DarwinCard { DarwinCardContent { DarwinText("This content revealed with a fade + slide animation!", color = DarwinTheme.colors.textSecondary) } } } }
    }
}

@Composable
internal fun RevealPage() {
    GalleryPage("Reveal", "Animate content into view with a fade and slide effect.") {
        SectionHeader("Examples")
        ExampleCard(title = "Reveal Once", sourceCode = GallerySources.RevealOnceExample) { RevealOnceExample() }
    }
}
