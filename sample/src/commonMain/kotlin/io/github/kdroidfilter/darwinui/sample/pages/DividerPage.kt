package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.HorizontalDivider
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.components.VerticalDivider
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@Composable
internal fun DividerPage() {
    GalleryPage("Divider", "Thin lines that group content in lists and layouts.") {
        SectionHeader("Horizontal")
        ComparisonSection(
            darwinContent = {
                Text(
                    "Darwin uses border or background modifiers for separators.",
                    style = DarwinTheme.typography.bodySmall,
                    color = DarwinTheme.colors.textTertiary,
                )
            },
            materialContent = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text("Section A")
                    HorizontalDivider()
                    Text("Section B")
                    HorizontalDivider()
                    Text("Section C")
                }
            },
        )

        SectionHeader("Vertical")
        ComparisonSection(
            darwinContent = {
                Text(
                    "No dedicated Darwin divider component.",
                    style = DarwinTheme.typography.bodySmall,
                    color = DarwinTheme.colors.textTertiary,
                )
            },
            materialContent = {
                Box(modifier = Modifier.height(48.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(horizontal = 8.dp),
                    ) {
                        Text("Left", modifier = Modifier.padding(vertical = 12.dp))
                        VerticalDivider()
                        Text("Center", modifier = Modifier.padding(vertical = 12.dp))
                        VerticalDivider()
                        Text("Right", modifier = Modifier.padding(vertical = 12.dp))
                    }
                }
            },
        )
    }
}
