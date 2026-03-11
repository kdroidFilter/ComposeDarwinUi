package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.SegmentedControl
import io.github.kdroidfilter.darwinui.components.SegmentedControlSize
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import androidx.compose.material3.Text as M3Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SegmentedControlPage() {
    GalleryPage("Segmented Control", "macOS-style segmented control with sliding pill indicator.") {
        val options = listOf("Day", "Week", "Month")

        SectionHeader("Single Choice")
        ComparisonSection(
            darwinContent = {
                var selected by remember { mutableStateOf(0) }
                SegmentedControl(
                    options = options,
                    selectedIndex = selected,
                    onSelectedIndexChange = { selected = it },
                )
            },
            materialContent = {
                var selected by remember { mutableStateOf(0) }
                SingleChoiceSegmentedButtonRow {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            selected = selected == index,
                            onClick = { selected = index },
                            shape = SegmentedButtonDefaults.itemShape(index, options.size),
                        ) {
                            M3Text(label)
                        }
                    }
                }
            },
            sourceCode = """
                var selected by remember { mutableStateOf(0) }
                SegmentedControl(
                    options = listOf("Day", "Week", "Month"),
                    selectedIndex = selected,
                    onSelectedIndexChange = { selected = it },
                )
            """.trimIndent(),
        )

        SectionHeader("Sizes")
        ComparisonSection(
            darwinContent = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    for (size in SegmentedControlSize.entries) {
                        var selected by remember { mutableStateOf(0) }
                        Text(size.name)
                        SegmentedControl(
                            options = options,
                            selectedIndex = selected,
                            onSelectedIndexChange = { selected = it },
                            size = size,
                        )
                    }
                }
            },
            materialContent = {
                Text("No equivalent in Material 3")
            },
            sourceCode = """
                SegmentedControl(
                    options = options,
                    selectedIndex = selected,
                    onSelectedIndexChange = { selected = it },
                    size = SegmentedControlSize.Small, // Regular, Large
                )
            """.trimIndent(),
        )

        SectionHeader("Disabled")
        ComparisonSection(
            darwinContent = {
                SegmentedControl(
                    options = listOf("On", "Off"),
                    selectedIndex = 0,
                    onSelectedIndexChange = {},
                    enabled = false,
                )
            },
            materialContent = {
                SingleChoiceSegmentedButtonRow {
                    listOf("On", "Off").forEachIndexed { index, label ->
                        SegmentedButton(
                            selected = index == 0,
                            onClick = {},
                            shape = SegmentedButtonDefaults.itemShape(index, 2),
                            enabled = false,
                        ) {
                            M3Text(label)
                        }
                    }
                }
            },
            sourceCode = """
                SegmentedControl(
                    options = listOf("On", "Off"),
                    selectedIndex = 0,
                    onSelectedIndexChange = {},
                    enabled = false,
                )
            """.trimIndent(),
        )
    }
}
