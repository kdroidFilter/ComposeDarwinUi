package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.CircleUser
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Search
import io.github.kdroidfilter.darwinui.components.NavigationBar
import io.github.kdroidfilter.darwinui.components.NavigationBarItem
import io.github.kdroidfilter.darwinui.components.Sidebar
import io.github.kdroidfilter.darwinui.components.SidebarItem
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader

@Composable
internal fun NavigationBarPage() {
    GalleryPage("Navigation Bar", "Compare Darwin Sidebar with Material 3 bottom NavigationBar.") {
        SectionHeader("Navigation")
        ComparisonSection(
            darwinContent = {
                var active by remember { mutableStateOf("home") }
                Sidebar(
                    items = listOf(
                        SidebarItem(label = "Home", onClick = { active = "home" }, icon = Lucide.House, id = "home"),
                        SidebarItem(label = "Search", onClick = { active = "search" }, icon = Lucide.Search, id = "search"),
                        SidebarItem(label = "Alerts", onClick = { active = "alerts" }, icon = Lucide.Bell, id = "alerts"),
                        SidebarItem(label = "Profile", onClick = { active = "profile" }, icon = Lucide.CircleUser, id = "profile"),
                    ),
                    activeItem = active,
                )
            },
            materialContent = {
                var selected by remember { mutableStateOf(0) }
                Box(modifier = Modifier.fillMaxWidth()) {
                    NavigationBar {
                        NavigationBarItem(
                            selected = selected == 0,
                            onClick = { selected = 0 },
                            icon = { Icon(Lucide.House) },
                            label = { Text("Home") },
                        )
                        NavigationBarItem(
                            selected = selected == 1,
                            onClick = { selected = 1 },
                            icon = { Icon(Lucide.Search) },
                            label = { Text("Search") },
                        )
                        NavigationBarItem(
                            selected = selected == 2,
                            onClick = { selected = 2 },
                            icon = { Icon(Lucide.Bell) },
                            label = { Text("Alerts") },
                        )
                        NavigationBarItem(
                            selected = selected == 3,
                            onClick = { selected = 3 },
                            icon = { Icon(Lucide.CircleUser) },
                            label = { Text("Profile") },
                        )
                    }
                }
            },
        )
    }
}
