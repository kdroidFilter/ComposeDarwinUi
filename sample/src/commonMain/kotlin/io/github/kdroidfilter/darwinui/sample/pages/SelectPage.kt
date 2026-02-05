package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.kdroidfilter.darwinui.components.ComboBox
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@GalleryExample("Select", "Default")
@Composable
fun SelectDefaultExample() {
    val items = listOf("React", "Vue", "Angular", "Svelte")
    var selected by remember { mutableStateOf<Int?>(null) }
    ComboBox(
        items = items,
        selected = selected,
        onSelectionChange = { index, _ -> selected = index },
        header = "Framework",
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@GalleryExample("Select", "Pre-selected")
@Composable
fun SelectPreselectedExample() {
    val items = listOf("React", "Vue", "Angular", "Svelte")
    var selected by remember { mutableStateOf<Int?>(0) }
    ComboBox(
        items = items,
        selected = selected,
        onSelectionChange = { index, _ -> selected = index },
        header = "Default Framework",
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@Composable
internal fun SelectPage() {
    GalleryPage("Select", "Displays a list of options for the user to pick from.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.SelectDefaultExample) { SelectDefaultExample() }
        ExampleCard(title = "Pre-selected", sourceCode = GallerySources.SelectPreselectedExample) { SelectPreselectedExample() }
    }
}
