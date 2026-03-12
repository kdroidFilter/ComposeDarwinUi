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
import io.github.kdroidfilter.darwinui.components.PushButton
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

@GalleryExample("Alert", "Alert Dialog — Save")
@Composable
fun AlertDialogSaveExample() {
    var showAlertDialog by remember { mutableStateOf(false) }
    PushButton(text = "Show Save Alert", onClick = { showAlertDialog = true })
    if (showAlertDialog) {
        AlertDialog(
            open = true,
            onDismissRequest = { showAlertDialog = false },
            title = "Save the messages, draft, and attachments?",
            message = "This message has not been sent. You can save it later.",
            confirmText = "Save",
            destructiveText = "Don't Save",
            cancelText = "Cancel",
            onConfirm = { showAlertDialog = false },
            onDestructive = { showAlertDialog = false },
            onCancel = { showAlertDialog = false },
        )
    }
}

@GalleryExample("Alert", "Alert Dialog — Destructive")
@Composable
fun AlertDialogDestructiveExample() {
    var showAlertDialog by remember { mutableStateOf(false) }
    PushButton(text = "Show Delete Alert", onClick = { showAlertDialog = true })
    if (showAlertDialog) {
        AlertDialog(
            open = true,
            onDismissRequest = { showAlertDialog = false },
            title = "\"Document\" will be deleted immediately.",
            message = "You can't undo this action.",
            type = AlertType.Error,
            confirmText = "OK",
            destructiveText = "Delete",
            cancelText = "Cancel",
            onConfirm = { showAlertDialog = false },
            onDestructive = { showAlertDialog = false },
            onCancel = { showAlertDialog = false },
        )
    }
}

@GalleryExample("Alert", "Alert Dialog — Simple")
@Composable
fun AlertDialogSimpleExample() {
    var showAlertDialog by remember { mutableStateOf(false) }
    PushButton(text = "Show Simple Alert", onClick = { showAlertDialog = true })
    if (showAlertDialog) {
        AlertDialog(
            open = true,
            onDismissRequest = { showAlertDialog = false },
            title = "The application is not responding.",
            message = "Do you want to wait or force quit?",
            confirmText = "OK",
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

        SectionHeader("Inline Banners")
        ExampleCard(title = "Info", sourceCode = GallerySources.AlertInfoExample) { AlertInfoExample() }
        ExampleCard(title = "Success", sourceCode = GallerySources.AlertSuccessExample) { AlertSuccessExample() }
        ExampleCard(title = "Warning", sourceCode = GallerySources.AlertWarningExample) { AlertWarningExample() }
        ExampleCard(title = "Error", sourceCode = GallerySources.AlertErrorExample) { AlertErrorExample() }

        SectionHeader("macOS-Style Alert Dialogs")
        ExampleCard(
            title = "Save Alert",
            description = "macOS-native save confirmation with 3 buttons",
            sourceCode = GallerySources.AlertDialogSaveExample,
        ) { AlertDialogSaveExample() }
        ExampleCard(
            title = "Destructive Alert",
            description = "macOS-native delete confirmation",
            sourceCode = GallerySources.AlertDialogDestructiveExample,
        ) { AlertDialogDestructiveExample() }
        ExampleCard(
            title = "Simple Alert",
            description = "macOS-native simple alert with 2 buttons",
            sourceCode = GallerySources.AlertDialogSimpleExample,
        ) { AlertDialogSimpleExample() }
    }
}
