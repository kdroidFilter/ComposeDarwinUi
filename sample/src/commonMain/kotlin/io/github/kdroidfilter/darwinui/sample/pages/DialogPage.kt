package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.kdroidfilter.darwinui.components.button.DarwinButton
import io.github.kdroidfilter.darwinui.components.button.DarwinButtonVariant
import io.github.kdroidfilter.darwinui.components.dialog.DarwinDialog
import io.github.kdroidfilter.darwinui.components.dialog.DarwinDialogContent
import io.github.kdroidfilter.darwinui.components.dialog.DarwinDialogDescription
import io.github.kdroidfilter.darwinui.components.dialog.DarwinDialogFooter
import io.github.kdroidfilter.darwinui.components.dialog.DarwinDialogHeader
import io.github.kdroidfilter.darwinui.components.dialog.DarwinDialogTitle
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@GalleryExample("Dialog", "Default")
@Composable
fun DialogDefaultExample() {
    var showDialog by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        DarwinButton(text = "Open Dialog", onClick = { showDialog = true }, variant = DarwinButtonVariant.Primary)
    }
    DarwinDialog(open = showDialog, onOpenChange = { showDialog = it }) {
        DarwinDialogContent(showCloseButton = true) {
            DarwinDialogHeader {
                DarwinDialogTitle("Confirm Action")
                DarwinDialogDescription("Are you sure you want to proceed? This action cannot be undone.")
            }
            DarwinDialogFooter {
                DarwinButton(text = "Cancel", onClick = { showDialog = false }, variant = DarwinButtonVariant.Ghost)
                DarwinButton(text = "Confirm", onClick = { showDialog = false }, variant = DarwinButtonVariant.Primary)
            }
        }
    }
}

@Composable
internal fun DialogPage() {
    GalleryPage("Dialog", "A modal dialog that interrupts the user with important content.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.DialogDefaultExample) { DialogDefaultExample() }
    }
}
