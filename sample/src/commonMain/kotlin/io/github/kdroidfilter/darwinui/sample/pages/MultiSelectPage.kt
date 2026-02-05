package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.kdroidfilter.darwinui.components.MultiSelectComboBox
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@GalleryExample("MultiSelect", "Default")
@Composable
fun MultiSelectDefaultExample() {
    val items = listOf("React", "Vue", "Angular", "Svelte", "SolidJS")
    var selected by remember { mutableStateOf(emptyList<Int>()) }
    MultiSelectComboBox(
        items = items,
        selectedIndices = selected,
        onSelectionChange = { selected = it },
        header = "Technologies",
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@GalleryExample("MultiSelect", "Pre-selected")
@Composable
fun MultiSelectPreselectedExample() {
    val items = listOf("React", "Vue", "Angular", "Svelte", "SolidJS")
    var selected by remember { mutableStateOf(listOf(0, 1)) }
    MultiSelectComboBox(
        items = items,
        selectedIndices = selected,
        onSelectionChange = { selected = it },
        header = "Favorites",
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@Composable
internal fun MultiSelectPage() {
    GalleryPage("Multi Select", "Allows selecting multiple options from a list.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.MultiSelectDefaultExample) { MultiSelectDefaultExample() }
        ExampleCard(title = "Pre-selected", sourceCode = GallerySources.MultiSelectPreselectedExample) { MultiSelectPreselectedExample() }
    }
}
