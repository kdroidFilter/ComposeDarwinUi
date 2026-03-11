package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.ModalBottomSheet
import io.github.kdroidfilter.darwinui.components.PrimaryButton
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@Composable
internal fun BottomSheetPage() {
    GalleryPage("Bottom Sheet", "A sheet that slides up from the bottom to present additional content.") {
        SectionHeader("Modal")
        ComparisonSection(
            darwinContent = {
                Text(
                    "Darwin uses Dialog or Popover for overlay content — no dedicated bottom sheet.",
                    style = DarwinTheme.typography.bodySmall,
                    color = DarwinTheme.colors.textTertiary,
                )
            },
            materialContent = {
                var showSheet by remember { mutableStateOf(false) }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    PrimaryButton(
                        text = "Open Bottom Sheet",
                        onClick = { showSheet = true },
                    )
                }

                if (showSheet) {
                    ModalBottomSheet(onDismissRequest = { showSheet = false }) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            Text("Bottom Sheet Title")
                            Text("This is some content inside the modal bottom sheet.")
                            PrimaryButton(
                                text = "Dismiss",
                                onClick = { showSheet = false },
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                }
            },
        )
    }
}
