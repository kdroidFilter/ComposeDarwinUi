package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.kdroidfilter.darwinui.components.MultiChoiceSegmentedButton as DarwinMultiChoiceSegmentedButton
import io.github.kdroidfilter.darwinui.components.MultiChoiceSegmentedButtonRow as DarwinMultiChoiceSegmentedButtonRow
import io.github.kdroidfilter.darwinui.components.SegmentedButton as DarwinSegmentedButton
import io.github.kdroidfilter.darwinui.components.SegmentedButtonDefaults as DarwinSegmentedButtonDefaults
import io.github.kdroidfilter.darwinui.components.SingleChoiceSegmentedButtonRow as DarwinSingleChoiceSegmentedButtonRow
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import androidx.compose.material3.Text as M3Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SegmentedButtonPage() {
    GalleryPage("Segmented Button", "Darwin SegmentedButton vs Material 3 SegmentedButton.") {
        val singleOptions = listOf("Day", "Week", "Month")
        val multiOptions = listOf("Bold", "Italic", "Underline")

        SectionHeader("Single Choice")
        ComparisonSection(
            darwinContent = {
                var selected by remember { mutableStateOf(0) }
                DarwinSingleChoiceSegmentedButtonRow {
                    singleOptions.forEachIndexed { index, label ->
                        DarwinSegmentedButton(
                            selected = selected == index,
                            onClick = { selected = index },
                            shape = DarwinSegmentedButtonDefaults.itemShape(index, singleOptions.size),
                        ) {
                            Text(label)
                        }
                    }
                }
            },
            materialContent = {
                var selected by remember { mutableStateOf(0) }
                SingleChoiceSegmentedButtonRow {
                    singleOptions.forEachIndexed { index, label ->
                        SegmentedButton(
                            selected = selected == index,
                            onClick = { selected = index },
                            shape = SegmentedButtonDefaults.itemShape(index, singleOptions.size),
                        ) {
                            M3Text(label)
                        }
                    }
                }
            },
            sourceCode = """
                var selected by remember { mutableStateOf(0) }
                SingleChoiceSegmentedButtonRow {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            selected = selected == index,
                            onClick = { selected = index },
                            shape = SegmentedButtonDefaults.itemShape(index, options.size),
                        ) { Text(label) }
                    }
                }
            """.trimIndent(),
        )

        SectionHeader("Multi Choice")
        ComparisonSection(
            darwinContent = {
                val checked = remember { mutableStateListOf(false, false, false) }
                DarwinMultiChoiceSegmentedButtonRow {
                    multiOptions.forEachIndexed { index, label ->
                        DarwinMultiChoiceSegmentedButton(
                            checked = checked[index],
                            onCheckedChange = { checked[index] = it },
                            shape = DarwinSegmentedButtonDefaults.itemShape(index, multiOptions.size),
                        ) {
                            Text(label)
                        }
                    }
                }
            },
            materialContent = {
                val checked = remember { mutableStateListOf(false, false, false) }
                MultiChoiceSegmentedButtonRow {
                    multiOptions.forEachIndexed { index, label ->
                        SegmentedButton(
                            checked = checked[index],
                            onCheckedChange = { checked[index] = it },
                            shape = SegmentedButtonDefaults.itemShape(index, multiOptions.size),
                        ) {
                            M3Text(label)
                        }
                    }
                }
            },
            sourceCode = """
                val checked = remember { mutableStateListOf(false, false, false) }
                MultiChoiceSegmentedButtonRow {
                    options.forEachIndexed { index, label ->
                        MultiChoiceSegmentedButton(
                            checked = checked[index],
                            onCheckedChange = { checked[index] = it },
                            shape = SegmentedButtonDefaults.itemShape(index, options.size),
                        ) { Text(label) }
                    }
                }
            """.trimIndent(),
        )
    }
}
