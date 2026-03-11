package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import io.github.kdroidfilter.darwinui.components.TimeInput as DarwinTimeInput
import io.github.kdroidfilter.darwinui.components.TimePicker as DarwinTimePicker
import io.github.kdroidfilter.darwinui.components.rememberTimePickerState as rememberDarwinTimePickerState
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TimePickerPage() {
    GalleryPage("Time Picker", "Darwin TimePicker vs Material 3 TimePicker.") {
        SectionHeader("Wheel / Dial")
        ComparisonSection(
            darwinContent = {
                val state = rememberDarwinTimePickerState(initialHour = 10, initialMinute = 30)
                DarwinTimePicker(state = state)
            },
            materialContent = {
                val state = rememberTimePickerState(initialHour = 10, initialMinute = 30)
                TimePicker(state = state)
            },
            sourceCode = """
                val state = rememberTimePickerState(initialHour = 10, initialMinute = 30)
                TimePicker(state = state)
            """.trimIndent(),
        )

        SectionHeader("Input")
        ComparisonSection(
            darwinContent = {
                val state = rememberDarwinTimePickerState(initialHour = 10, initialMinute = 30)
                DarwinTimeInput(state = state)
            },
            materialContent = {
                val state = rememberTimePickerState(initialHour = 10, initialMinute = 30)
                TimeInput(state = state)
            },
            sourceCode = """
                val state = rememberTimePickerState(initialHour = 10, initialMinute = 30)
                TimeInput(state = state)
            """.trimIndent(),
        )
    }
}
