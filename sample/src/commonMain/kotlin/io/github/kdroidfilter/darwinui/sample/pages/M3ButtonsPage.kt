package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.MacNativeAccentButton
import io.github.kdroidfilter.darwinui.components.MacNativeDestructiveButton
import io.github.kdroidfilter.darwinui.components.MacNativeSecondaryButton
import io.github.kdroidfilter.darwinui.components.PulldownButton
import io.github.kdroidfilter.darwinui.components.PushButton
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import androidx.compose.material3.Text as M3Text

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun M3ButtonsPage() {
    GalleryPage("Buttons", "Darwin native macOS buttons vs Material 3 button components.") {
        SectionHeader("Variants")
        ComparisonSection(
            darwinContent = {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    PushButton(text = "Push", onClick = {})
                    MacNativeAccentButton(text = "Accent", onClick = {}, fillWidth = false)
                    MacNativeSecondaryButton(text = "Secondary", onClick = {}, fillWidth = false)
                    MacNativeDestructiveButton(text = "Destructive", onClick = {}, fillWidth = false)
                    PulldownButton(text = "Pulldown", onClick = {})
                }
            },
            materialContent = {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Button(onClick = {}) { M3Text("Filled") }
                    OutlinedButton(onClick = {}) { M3Text("Outlined") }
                    TextButton(onClick = {}) { M3Text("Text") }
                    ElevatedButton(onClick = {}) { M3Text("Elevated") }
                    FilledTonalButton(onClick = {}) { M3Text("Filled Tonal") }
                }
            },
            sourceCode = """
                PushButton(text = "Push", onClick = {})
                MacNativeAccentButton(text = "Accent", onClick = {})
                MacNativeSecondaryButton(text = "Secondary", onClick = {})
                MacNativeDestructiveButton(text = "Destructive", onClick = {})
                PulldownButton(text = "Pulldown", onClick = {})
            """.trimIndent(),
        )

        SectionHeader("Disabled")
        ComparisonSection(
            darwinContent = {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    PushButton(text = "Push", onClick = {}, enabled = false)
                    MacNativeAccentButton(text = "Accent", onClick = {}, enabled = false, fillWidth = false)
                    MacNativeSecondaryButton(text = "Secondary", onClick = {}, enabled = false, fillWidth = false)
                }
            },
            materialContent = {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Button(onClick = {}, enabled = false) { M3Text("Filled") }
                    OutlinedButton(onClick = {}, enabled = false) { M3Text("Outlined") }
                    TextButton(onClick = {}, enabled = false) { M3Text("Text") }
                    ElevatedButton(onClick = {}, enabled = false) { M3Text("Elevated") }
                    FilledTonalButton(onClick = {}, enabled = false) { M3Text("Filled Tonal") }
                }
            },
            sourceCode = """
                PushButton(text = "Push", onClick = {}, enabled = false)
                MacNativeAccentButton(text = "Accent", onClick = {}, enabled = false)
            """.trimIndent(),
        )
    }
}
