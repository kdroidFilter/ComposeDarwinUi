package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.EllipsisVertical
import com.composables.icons.lucide.Lucide
import io.github.kdroidfilter.darwinui.components.CenterAlignedTopAppBar
import io.github.kdroidfilter.darwinui.components.IconButton
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.components.TopAppBar
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@Composable
internal fun TopAppBarPage() {
    GalleryPage("Top App Bar", "A toolbar placed at the top of the screen for navigation and actions.") {
        SectionHeader("Standard vs Center Aligned")
        ComparisonSection(
            darwinContent = {
                Text(
                    "Darwin uses Sidebar header or custom Row layouts for top navigation.",
                    style = DarwinTheme.typography.bodySmall,
                    color = DarwinTheme.colors.textTertiary,
                )
                Box(modifier = Modifier.fillMaxWidth()) {
                    TopAppBar(
                        title = { Text("Standard") },
                        navigationIcon = {
                            IconButton(onClick = {}) { Icon(Lucide.ArrowLeft) }
                        },
                        actions = {
                            IconButton(onClick = {}) { Icon(Lucide.EllipsisVertical) }
                        },
                    )
                }
            },
            materialContent = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    CenterAlignedTopAppBar(
                        title = { Text("Center Aligned") },
                        navigationIcon = {
                            IconButton(onClick = {}) { Icon(Lucide.ArrowLeft) }
                        },
                        actions = {
                            IconButton(onClick = {}) { Icon(Lucide.EllipsisVertical) }
                        },
                    )
                }
            },
        )
    }
}
