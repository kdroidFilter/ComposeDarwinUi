package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Menu
import com.composables.icons.lucide.Search
import io.github.kdroidfilter.darwinui.components.NavigationRail as DarwinNavigationRail
import io.github.kdroidfilter.darwinui.components.NavigationRailItem as DarwinNavigationRailItem
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import androidx.compose.material3.Text as M3Text

@Composable
internal fun NavigationRailPage() {
    GalleryPage("Navigation Rail", "Darwin NavigationRail vs Material 3 NavigationRail.") {
        SectionHeader("Basic")
        ComparisonSection(
            darwinContent = {
                var selected by remember { mutableStateOf(0) }
                Box(modifier = Modifier.height(280.dp)) {
                    DarwinNavigationRail {
                        DarwinNavigationRailItem(
                            selected = selected == 0,
                            onClick = { selected = 0 },
                            icon = { Icon(Lucide.Menu) },
                            label = { Text("Home") },
                        )
                        DarwinNavigationRailItem(
                            selected = selected == 1,
                            onClick = { selected = 1 },
                            icon = { Icon(Lucide.Search) },
                            label = { Text("Search") },
                        )
                        DarwinNavigationRailItem(
                            selected = selected == 2,
                            onClick = { selected = 2 },
                            icon = { Icon(Lucide.Bell) },
                            label = { Text("Alerts") },
                        )
                    }
                }
            },
            materialContent = {
                var selected by remember { mutableStateOf(0) }
                Box(modifier = Modifier.height(280.dp)) {
                    NavigationRail {
                        NavigationRailItem(
                            selected = selected == 0,
                            onClick = { selected = 0 },
                            icon = { Icon(Lucide.Menu) },
                            label = { M3Text("Home") },
                        )
                        NavigationRailItem(
                            selected = selected == 1,
                            onClick = { selected = 1 },
                            icon = { Icon(Lucide.Search) },
                            label = { M3Text("Search") },
                        )
                        NavigationRailItem(
                            selected = selected == 2,
                            onClick = { selected = 2 },
                            icon = { Icon(Lucide.Bell) },
                            label = { M3Text("Alerts") },
                        )
                    }
                }
            },
        )
    }
}
