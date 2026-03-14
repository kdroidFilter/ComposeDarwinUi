package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.LinearProgress
import io.github.kdroidfilter.darwinui.components.ProgressRing
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.CodeBlock
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.PreviewContainer
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.ControlSize
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@Composable
private fun ProgressPreview() {
    Column(
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        LinearProgress(value = 65f, max = 100f)
        LinearProgress(value = 65f, max = 100f, enabled = false)
        LinearProgress(indeterminate = true)
        LinearProgress(indeterminate = true, enabled = false)
        ControlSize(ControlSize.Large) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                ProgressRing(progress = 0.75f)
                ProgressRing(progress = 0.45f, color = DarwinTheme.colorScheme.success)
                ProgressRing()
            }
        }
    }
}

@GalleryExample("Progress", "Sizes")
@Composable
fun ProgressSizesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        for (size in ControlSize.entries) {
            ControlSize(size) {
                Row(
                    modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = size.name,
                        style = DarwinTheme.typography.caption1,
                        color = DarwinTheme.colorScheme.textSecondary,
                        modifier = Modifier.widthIn(min = 72.dp),
                    )
                    LinearProgress(value = 65f, max = 100f, modifier = Modifier.weight(1f))
                    ProgressRing(progress = 0.65f)
                }
            }
        }
    }
}

@GalleryExample("Progress", "Determinate")
@Composable
fun ProgressDeterminateExample() {
    Column(
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        LinearProgress(value = 65f, max = 100f)
        LinearProgress(value = 65f, max = 100f, enabled = false)
    }
}

@GalleryExample("Progress", "Indeterminate")
@Composable
fun ProgressIndeterminateExample() {
    Column(
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        LinearProgress(indeterminate = true)
        LinearProgress(indeterminate = true, enabled = false)
    }
}

@GalleryExample("Progress", "Circular")
@Composable
fun ProgressCircularExample() {
    ControlSize(ControlSize.Large) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ProgressRing(progress = 0.75f)
            ProgressRing(progress = 0.45f, color = DarwinTheme.colorScheme.success)
            ProgressRing()
        }
    }
}

@Composable
internal fun ProgressPage() {
    GalleryPage("Progress", "Displays an indicator showing the completion progress of a task.") {
        PreviewContainer { ProgressPreview() }

        SectionHeader("Usage")
        CodeBlock("""LinearProgress(value = 65f, max = 100f)
LinearProgress(indeterminate = true)
ProgressRing(progress = 0.75f)""")

        SectionHeader("Sizes")
        ExampleCard(
            title = "All Sizes",
            description = "LinearProgress and ProgressRing at each ControlSize level",
            sourceCode = GallerySources.ProgressSizesExample,
        ) { ProgressSizesExample() }

        SectionHeader("Examples")
        ExampleCard(
            title = "Determinate",
            description = "Active and inactive determinate progress bars",
            sourceCode = GallerySources.ProgressDeterminateExample,
        ) { ProgressDeterminateExample() }
        ExampleCard(
            title = "Indeterminate",
            description = "Active and inactive indeterminate progress bars",
            sourceCode = GallerySources.ProgressIndeterminateExample,
        ) { ProgressIndeterminateExample() }
        ExampleCard(
            title = "Circular",
            sourceCode = GallerySources.ProgressCircularExample,
        ) { ProgressCircularExample() }
    }
}
