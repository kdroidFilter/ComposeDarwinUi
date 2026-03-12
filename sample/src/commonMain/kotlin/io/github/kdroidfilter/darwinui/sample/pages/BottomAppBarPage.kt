package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.runtime.Composable
import com.composables.icons.lucide.Ellipsis
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Menu
import com.composables.icons.lucide.Plus
import com.composables.icons.lucide.Search
import io.github.kdroidfilter.darwinui.components.BottomAppBar as DarwinBottomAppBar
import io.github.kdroidfilter.darwinui.components.PushButton
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader

@Composable
internal fun BottomAppBarPage() {
    GalleryPage("Bottom App Bar", "Darwin BottomAppBar with actions and optional FAB slot.") {
        SectionHeader("With Actions and FAB")
        ExampleCard(
            title = "With Actions and FAB",
            description = "Bottom bar with icon actions and a floating action button slot",
            sourceCode = """
                BottomAppBar(
                    actions = {
                        PushButton(onClick = {}) { Icon(Lucide.Menu) }
                        PushButton(onClick = {}) { Icon(Lucide.Search) }
                        PushButton(onClick = {}) { Icon(Lucide.Ellipsis) }
                    },
                    floatingActionButton = {
                        PushButton(onClick = {}) { Icon(Lucide.Plus) }
                    },
                )
            """.trimIndent(),
        ) {
            DarwinBottomAppBar(
                actions = {
                    PushButton(onClick = {}) { Icon(Lucide.Menu) }
                    PushButton(onClick = {}) { Icon(Lucide.Search) }
                    PushButton(onClick = {}) { Icon(Lucide.Ellipsis) }
                },
                floatingActionButton = {
                    PushButton(onClick = {}) { Icon(Lucide.Plus) }
                },
            )
        }

        SectionHeader("Actions Only")
        ExampleCard(
            title = "Actions Only",
            description = "Bottom bar with icon actions and no FAB",
            sourceCode = """
                BottomAppBar(
                    actions = {
                        PushButton(onClick = {}) { Icon(Lucide.Menu) }
                        PushButton(onClick = {}) { Icon(Lucide.Search) }
                        PushButton(onClick = {}) { Icon(Lucide.Ellipsis) }
                    },
                )
            """.trimIndent(),
        ) {
            DarwinBottomAppBar(
                actions = {
                    PushButton(onClick = {}) { Icon(Lucide.Menu) }
                    PushButton(onClick = {}) { Icon(Lucide.Search) }
                    PushButton(onClick = {}) { Icon(Lucide.Ellipsis) }
                },
            )
        }
    }
}
