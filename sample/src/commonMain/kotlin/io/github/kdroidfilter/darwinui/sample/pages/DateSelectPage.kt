package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.DateSelect
import io.github.kdroidfilter.darwinui.components.DateConfig
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.CodeBlock
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@GalleryExample("DateSelect", "Default")
@Composable
fun DateSelectDefaultExample() {
    var selectedConfig by remember { mutableStateOf<DateConfig?>(null) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.widthIn(max = 384.dp).fillMaxWidth()) {
        DateSelect(
            label = "Select a date",
            onChange = { selectedConfig = it },
        )
        if (selectedConfig != null) {
            Text(
                text = "Selected: ${selectedConfig!!.selectedDate ?: "—"}",
                style = DarwinTheme.typography.bodySmall,
                color = DarwinTheme.colors.textSecondary,
            )
        }
    }
}

@Composable
internal fun DateSelectPage() {
    GalleryPage("Date Select", "A sophisticated date/time selector with single and recurring event support.") {
        SectionHeader("Usage")
        CodeBlock("""DateSelect(
    label = "Select a date",
    onChange = { config -> /* handle config */ },
)""")

        SectionHeader("Examples")
        ExampleCard(
            title = "Default",
            description = "Date select with label and change callback",
            sourceCode = GallerySources.DateSelectDefaultExample,
        ) { DateSelectDefaultExample() }
    }
}
