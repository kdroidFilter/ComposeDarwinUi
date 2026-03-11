package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import com.composables.icons.lucide.Ellipsis
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Menu
import com.composables.icons.lucide.Plus
import com.composables.icons.lucide.Search
import io.github.kdroidfilter.darwinui.components.BottomAppBar as DarwinBottomAppBar
import io.github.kdroidfilter.darwinui.components.FloatingActionButton as DarwinFAB
import io.github.kdroidfilter.darwinui.components.IconButton
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import androidx.compose.material3.IconButton as M3IconButton

@Composable
internal fun BottomAppBarPage() {
    GalleryPage("Bottom App Bar", "Darwin BottomAppBar vs Material 3 BottomAppBar.") {
        SectionHeader("With Actions and FAB")
        ComparisonSection(
            darwinContent = {
                DarwinBottomAppBar(
                    actions = {
                        IconButton(onClick = {}) { Icon(Lucide.Menu) }
                        IconButton(onClick = {}) { Icon(Lucide.Search) }
                        IconButton(onClick = {}) { Icon(Lucide.Ellipsis) }
                    },
                    floatingActionButton = {
                        DarwinFAB(onClick = {}) { Icon(Lucide.Plus) }
                    },
                )
            },
            materialContent = {
                BottomAppBar(
                    actions = {
                        M3IconButton(onClick = {}) { Icon(Lucide.Menu) }
                        M3IconButton(onClick = {}) { Icon(Lucide.Search) }
                        M3IconButton(onClick = {}) { Icon(Lucide.Ellipsis) }
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {}) { Icon(Lucide.Plus) }
                    },
                )
            },
        )

        SectionHeader("Actions Only")
        ComparisonSection(
            darwinContent = {
                DarwinBottomAppBar(
                    actions = {
                        IconButton(onClick = {}) { Icon(Lucide.Menu) }
                        IconButton(onClick = {}) { Icon(Lucide.Search) }
                        IconButton(onClick = {}) { Icon(Lucide.Ellipsis) }
                    },
                )
            },
            materialContent = {
                BottomAppBar(
                    actions = {
                        M3IconButton(onClick = {}) { Icon(Lucide.Menu) }
                        M3IconButton(onClick = {}) { Icon(Lucide.Search) }
                        M3IconButton(onClick = {}) { Icon(Lucide.Ellipsis) }
                    },
                )
            },
        )
    }
}
