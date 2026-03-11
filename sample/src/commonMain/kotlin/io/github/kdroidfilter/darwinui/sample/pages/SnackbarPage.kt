package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.AlertBanner
import io.github.kdroidfilter.darwinui.components.AlertType
import io.github.kdroidfilter.darwinui.components.PrimaryButton
import io.github.kdroidfilter.darwinui.components.Snackbar
import io.github.kdroidfilter.darwinui.components.SnackbarHost
import io.github.kdroidfilter.darwinui.components.SnackbarHostState
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.components.TextButton
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import kotlinx.coroutines.launch

@Composable
internal fun SnackbarPage() {
    GalleryPage("Snackbar", "Compare Darwin AlertBanner with Material 3 Snackbar.") {
        SectionHeader("Static Messages")
        ComparisonSection(
            darwinContent = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    AlertBanner(
                        type = AlertType.Info,
                        title = "Info",
                        message = "This is an info alert",
                    )
                    AlertBanner(
                        type = AlertType.Success,
                        title = "Success",
                        message = "Operation completed",
                    )
                }
            },
            materialContent = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Snackbar {
                        Text("This is a snackbar message")
                    }
                    Snackbar(
                        action = {
                            TextButton(onClick = {}) { Text("Undo") }
                        },
                    ) {
                        Text("Item deleted")
                    }
                }
            },
        )

        SectionHeader("Interactive")
        ComparisonSection(
            darwinContent = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    AlertBanner(
                        type = AlertType.Warning,
                        title = "Dismissable",
                        message = "You can dismiss this alert",
                        onDismiss = {},
                    )
                }
            },
            materialContent = {
                val hostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()

                Box(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        PrimaryButton(
                            text = "Show snackbar",
                            onClick = {
                                scope.launch {
                                    hostState.showSnackbar("Hello from snackbar!")
                                }
                            },
                        )
                    }
                    SnackbarHost(
                        hostState = hostState,
                        modifier = Modifier.align(Alignment.BottomCenter),
                    )
                }
            },
        )
    }
}
