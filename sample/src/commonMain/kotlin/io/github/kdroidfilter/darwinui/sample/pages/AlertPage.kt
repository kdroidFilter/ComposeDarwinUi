package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.AlertBanner
import io.github.kdroidfilter.darwinui.components.AlertDialog
import io.github.kdroidfilter.darwinui.components.AlertType
import io.github.kdroidfilter.darwinui.components.DestructiveButton
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.PreviewContainer
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@Composable
private fun AlertPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        AlertBanner(message = "This is an informational alert.", title = "Information", type = AlertType.Info)
        AlertBanner(message = "Operation completed successfully!", title = "Success", type = AlertType.Success)
        AlertBanner(message = "Please review before proceeding.", title = "Warning", type = AlertType.Warning)
        AlertBanner(message = "An error occurred.", title = "Error", type = AlertType.Error)
    }
}

@GalleryExample("Alert", "Info")
@Composable
fun AlertInfoExample() {
    AlertBanner(
        message = "This is an informational alert.",
        title = "Information",
        type = AlertType.Info,
    )
}

@GalleryExample("Alert", "Success")
@Composable
fun AlertSuccessExample() {
    AlertBanner(
        message = "Operation completed successfully!",
        title = "Success",
        type = AlertType.Success,
    )
}

@GalleryExample("Alert", "Warning")
@Composable
fun AlertWarningExample() {
    AlertBanner(
        message = "Please review before proceeding.",
        title = "Warning",
        type = AlertType.Warning,
    )
}

@GalleryExample("Alert", "Error")
@Composable
fun AlertErrorExample() {
    AlertBanner(
        message = "An error occurred while processing.",
        title = "Error",
        type = AlertType.Error,
        onDismiss = {},
    )
}

@GalleryExample("Alert", "Alert Dialog")
@Composable
fun AlertDialogExample() {
    var showAlertDialog by remember { mutableStateOf(false) }
    DestructiveButton(text = "Show Alert Dialog", onClick = { showAlertDialog = true })
    if (showAlertDialog) {
        AlertDialog(
            open = true,
            onDismissRequest = { showAlertDialog = false },
            title = "Delete item?",
            message = "This action cannot be undone. Are you sure you want to delete this item?",
            type = AlertType.Error,
            confirmText = "Delete",
            cancelText = "Cancel",
            onConfirm = { showAlertDialog = false },
            onCancel = { showAlertDialog = false },
        )
    }
}

@Composable
internal fun AlertPage() {
    GalleryPage("Alert", "Displays a callout for user attention.") {
        PreviewContainer { AlertPreview() }

        SectionHeader("Examples")
        ExampleCard(title = "Info", sourceCode = GallerySources.AlertInfoExample) { AlertInfoExample() }
        ExampleCard(title = "Success", sourceCode = GallerySources.AlertSuccessExample) { AlertSuccessExample() }
        ExampleCard(title = "Warning", sourceCode = GallerySources.AlertWarningExample) { AlertWarningExample() }
        ExampleCard(title = "Error", sourceCode = GallerySources.AlertErrorExample) { AlertErrorExample() }
        ExampleCard(
            title = "Alert Dialog",
            description = "Modal confirmation dialog",
            sourceCode = GallerySources.AlertDialogExample,
        ) { AlertDialogExample() }
    }
}
