package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.composables.icons.lucide.ChevronRight
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Mail
import io.github.kdroidfilter.darwinui.components.HorizontalDivider
import io.github.kdroidfilter.darwinui.components.ListItem
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@Composable
internal fun ListItemPage() {
    GalleryPage("List Item", "A flexible component for displaying rows of information in lists.") {
        SectionHeader("One Line")
        ComparisonSection(
            darwinContent = {
                Text(
                    "Darwin uses Sidebar items or custom Row layouts for list entries.",
                    style = DarwinTheme.typography.bodySmall,
                    color = DarwinTheme.colors.textTertiary,
                )
            },
            materialContent = {
                Column {
                    ListItem(headlineContent = { Text("First item") })
                    HorizontalDivider()
                    ListItem(headlineContent = { Text("Second item") })
                    HorizontalDivider()
                    ListItem(headlineContent = { Text("Third item") })
                }
            },
        )

        SectionHeader("Two Line with Icons")
        ComparisonSection(
            darwinContent = {
                Text(
                    "No direct Darwin equivalent — build with Row/Column composables.",
                    style = DarwinTheme.typography.bodySmall,
                    color = DarwinTheme.colors.textTertiary,
                )
            },
            materialContent = {
                Column {
                    ListItem(
                        headlineContent = { Text("Inbox") },
                        supportingContent = { Text("3 unread messages") },
                        leadingContent = { Icon(Lucide.Mail) },
                        trailingContent = { Icon(Lucide.ChevronRight) },
                    )
                    HorizontalDivider()
                    ListItem(
                        headlineContent = { Text("Sent") },
                        supportingContent = { Text("Last sent 2 hours ago") },
                        leadingContent = { Icon(Lucide.Mail) },
                        trailingContent = { Icon(Lucide.ChevronRight) },
                    )
                }
            },
        )
    }
}
