package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.ContentDialog
import io.github.kdroidfilter.darwinui.components.ContentDialogButton
import io.github.kdroidfilter.darwinui.components.PushButton
import io.github.kdroidfilter.darwinui.components.SmallDialog
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@GalleryExample("Dialog", "Default")
@Composable
fun DialogDefaultExample() {
    var showDialog by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        PushButton(text = "Open Dialog", onClick = { showDialog = true })
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

@GalleryExample("Dialog", "Small Dialog")
@Composable
fun SmallDialogExample() {
    var showDialog by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        PushButton(text = "Open Small Dialog", onClick = { showDialog = true })
    }
    SmallDialog(
        visible = showDialog,
        onDismissRequest = { showDialog = false },
        title = "Save Changes",
        confirmText = "Save",
        onConfirm = { showDialog = false },
        cancelText = "Cancel",
        onCancel = { showDialog = false },
    ) {
        Text(
            text = "Review your changes before saving. You can cancel to go back.",
            style = DarwinTheme.typography.bodySmall,
            color = DarwinTheme.colors.textSecondary,
        )
    }
}

@GalleryExample("Dialog", "Small Dialog with Destructive")
@Composable
fun SmallDialogDestructiveExample() {
    var showDialog by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        PushButton(text = "Open Delete Dialog", onClick = { showDialog = true })
    }
    SmallDialog(
        visible = showDialog,
        onDismissRequest = { showDialog = false },
        title = "Manage Document",
        confirmText = "Save",
        onConfirm = { showDialog = false },
        cancelText = "Cancel",
        onCancel = { showDialog = false },
        destructiveText = "Delete",
        onDestructive = { showDialog = false },
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text("Name", style = DarwinTheme.typography.bodySmall, color = DarwinTheme.colors.textTertiary)
                Text("Untitled Document", style = DarwinTheme.typography.bodySmall)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text("Location", style = DarwinTheme.typography.bodySmall, color = DarwinTheme.colors.textTertiary)
                Text("Desktop", style = DarwinTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
internal fun DialogPage() {
    GalleryPage("Dialog", "A modal dialog that interrupts the user with important content.") {
        SectionHeader("Content Dialog")
        ExampleCard(title = "Default", sourceCode = GallerySources.DialogDefaultExample) { DialogDefaultExample() }

        SectionHeader("Small Dialog")
        ExampleCard(
            title = "Simple",
            description = "macOS-native small dialog with cancel and confirm",
            sourceCode = GallerySources.SmallDialogExample,
        ) { SmallDialogExample() }
        ExampleCard(
            title = "With Destructive",
            description = "Small dialog with destructive action on the left",
            sourceCode = GallerySources.SmallDialogDestructiveExample,
        ) { SmallDialogDestructiveExample() }
    }
}
