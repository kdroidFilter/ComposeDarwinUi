package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.EllipsisVertical
import com.composables.icons.lucide.Lucide
import io.github.kdroidfilter.darwinui.components.PushButton
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.components.TopAppBar as DarwinTopAppBar
import io.github.kdroidfilter.darwinui.components.CenterAlignedTopAppBar as DarwinCenterAlignedTopAppBar

@Composable
internal fun TopAppBarPage() {
    GalleryPage("Top App Bar", "Darwin TopAppBar components.") {
        SectionHeader("Standard")
        ExampleCard(
            title = "Standard",
            description = "Top app bar with navigation icon, title, and actions",
            sourceCode = """
                TopAppBar(
                    title = { Text("Page Title") },
                    navigationIcon = {
                        PushButton(onClick = {}) { Icon(Lucide.ArrowLeft) }
                    },
                    actions = {
                        PushButton(onClick = {}) { Icon(Lucide.EllipsisVertical) }
                    },
                )
            """.trimIndent(),
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                DarwinTopAppBar(
                    title = { Text("Page Title") },
                    navigationIcon = {
                        PushButton(onClick = {}) { Icon(Lucide.ArrowLeft) }
                    },
                    actions = {
                        PushButton(onClick = {}) { Icon(Lucide.EllipsisVertical) }
                    },
                )
            }
        }

        SectionHeader("Center Aligned")
        ExampleCard(
            title = "Center Aligned",
            description = "Top app bar with a centered title",
            sourceCode = """
                CenterAlignedTopAppBar(
                    title = { Text("Centered Title") },
                    navigationIcon = {
                        PushButton(onClick = {}) { Icon(Lucide.ArrowLeft) }
                    },
                    actions = {
                        PushButton(onClick = {}) { Icon(Lucide.EllipsisVertical) }
                    },
                )
            """.trimIndent(),
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                DarwinCenterAlignedTopAppBar(
                    title = { Text("Centered Title") },
                    navigationIcon = {
                        PushButton(onClick = {}) { Icon(Lucide.ArrowLeft) }
                    },
                    actions = {
                        PushButton(onClick = {}) { Icon(Lucide.EllipsisVertical) }
                    },
                )
            }
        }
    }
}
