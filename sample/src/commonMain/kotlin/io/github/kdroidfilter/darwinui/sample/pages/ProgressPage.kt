package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.LinearProgress
import io.github.kdroidfilter.darwinui.components.ProgressVariant
import io.github.kdroidfilter.darwinui.components.ProgressRing
import io.github.kdroidfilter.darwinui.components.ProgressRingSize
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.CodeBlock
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.PreviewContainer
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.Emerald500

@Composable
private fun ProgressPreview() {
    Column(
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        LinearProgress(value = 65f, max = 100f, showValue = true)
        LinearProgress(value = 40f, max = 100f, variant = ProgressVariant.Success)
        LinearProgress(value = 80f, max = 100f, variant = ProgressVariant.Gradient)
        LinearProgress(indeterminate = true)
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ProgressRing(progress = 0.75f, size = ProgressRingSize.Large)
            ProgressRing(progress = 0.45f, size = ProgressRingSize.Large, color = Emerald500)
            ProgressRing(size = ProgressRingSize.Large)
        }
    }
}

@GalleryExample("Progress", "Linear Default")
@Composable
fun ProgressLinearDefaultExample() {
    LinearProgress(
        value = 65f,
        max = 100f,
        showValue = true,
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
    )
}

@GalleryExample("Progress", "Linear Success")
@Composable
fun ProgressLinearSuccessExample() {
    LinearProgress(
        value = 40f,
        max = 100f,
        variant = ProgressVariant.Success,
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
    )
}

@GalleryExample("Progress", "Linear Gradient")
@Composable
fun ProgressLinearGradientExample() {
    LinearProgress(
        value = 80f,
        max = 100f,
        variant = ProgressVariant.Gradient,
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
    )
}

@GalleryExample("Progress", "Linear Indeterminate")
@Composable
fun ProgressLinearIndeterminateExample() {
    LinearProgress(
        indeterminate = true,
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
    )
}

@GalleryExample("Progress", "Circular")
@Composable
fun ProgressCircularExample() {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        ProgressRing(progress = 0.75f, size = ProgressRingSize.Large)
        ProgressRing(progress = 0.45f, size = ProgressRingSize.Large, color = Emerald500)
        ProgressRing(size = ProgressRingSize.Large)
    }
}

@Composable
internal fun ProgressPage() {
    GalleryPage("Progress", "Displays an indicator showing the completion progress of a task.") {
        PreviewContainer { ProgressPreview() }

        SectionHeader("Usage")
        CodeBlock("""LinearProgress(value = 65f, max = 100f, showValue = true)
ProgressRing(progress = 0.75f)""")

        SectionHeader("Examples")
        ExampleCard(title = "Linear - Default", sourceCode = GallerySources.ProgressLinearDefaultExample) { ProgressLinearDefaultExample() }
        ExampleCard(title = "Linear - Success", sourceCode = GallerySources.ProgressLinearSuccessExample) { ProgressLinearSuccessExample() }
        ExampleCard(
            title = "Linear - Gradient",
            sourceCode = GallerySources.ProgressLinearGradientExample,
        ) { ProgressLinearGradientExample() }
        ExampleCard(
            title = "Linear - Indeterminate",
            sourceCode = GallerySources.ProgressLinearIndeterminateExample,
        ) { ProgressLinearIndeterminateExample() }
        ExampleCard(title = "Circular", sourceCode = GallerySources.ProgressCircularExample) { ProgressCircularExample() }
    }
}
