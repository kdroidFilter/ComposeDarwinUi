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
import io.github.kdroidfilter.darwinui.components.PrimaryButton
import io.github.kdroidfilter.darwinui.components.ContentDialog
import io.github.kdroidfilter.darwinui.components.ContentDialogButton
import io.github.kdroidfilter.darwinui.components.Text
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
        PrimaryButton(text = "Open Dialog", onClick = { showDialog = true })
    }
    ContentDialog(
        title = "Confirm Action",
        visible = showDialog,
        content = {
            Text("Are you sure you want to proceed? This action cannot be undone.")
        },
        primaryButtonText = "Confirm",
        closeButtonText = "Cancel",
        onButtonClick = { showDialog = false },
    )
}

@Composable
internal fun DialogPage() {
    GalleryPage("Dialog", "A modal dialog that interrupts the user with important content.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.DialogDefaultExample) { DialogDefaultExample() }
    }
}
