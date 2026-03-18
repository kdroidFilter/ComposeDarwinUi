package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Card
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Sidebar
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SidebarDefaults
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SidebarItem
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Switcher
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icons
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

@GalleryExample("Sidebar", "Preview")
@Composable
fun SidebarPreviewExample() {
    var activeItem by remember { mutableStateOf("Favorites") }
    var isCollapsed by remember { mutableStateOf(false) }
    val sidebarItems = remember {
        listOf(
            SidebarItem("Favorites", onClick = { activeItem = "Favorites" }, icon = Icons.Star, group = "Favorites"),
            SidebarItem("Recents", onClick = { activeItem = "Recents" }, icon = Icons.Calendar, group = "Favorites"),
            SidebarItem("iCloud Shared", onClick = { activeItem = "iCloud Shared" }, icon = Icons.Folder, group = "Favorites"),
            SidebarItem("Documents", onClick = { activeItem = "Documents" }, icon = Icons.Folder, group = "Favorites"),
            SidebarItem("Downloads", onClick = { activeItem = "Downloads" }, icon = Icons.Download, group = "Favorites"),
            SidebarItem("iCloud Drive", onClick = { activeItem = "iCloud Drive" }, icon = Icons.Upload, group = "Locations"),
            SidebarItem("Home", onClick = { activeItem = "Home" }, icon = Icons.Home, group = "Locations"),
            SidebarItem("Mac HD", onClick = { activeItem = "Mac HD" }, icon = Icons.BarChart3, group = "Locations"),
            SidebarItem("Trash", onClick = { activeItem = "Trash" }, icon = Icons.Trash2, group = "Locations"),
        )
    }
    val pinnedItems = remember {
        listOf(SidebarItem("Settings", onClick = { activeItem = "Settings" }, icon = Icons.Settings))
    }
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Switcher(checked = isCollapsed, onCheckedChange = { isCollapsed = it })
            Text(
                text = if (isCollapsed) "Collapsed" else "Expanded",
                style = MacosTheme.typography.caption1,
                color = MacosTheme.colorScheme.textSecondary,
            )
        }
        Card(modifier = Modifier.fillMaxWidth().height(320.dp)) {
            Row(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.fillMaxHeight().background(MacosTheme.colorScheme.muted)) {
                    Sidebar(
                        items = sidebarItems,
                        pinnedItems = pinnedItems,
                        activeItem = activeItem,
                        onLogout = { activeItem = "Logged out" },
                        collapsed = isCollapsed,
                        onCollapsedChange = { isCollapsed = it },
                        collapsible = true,
                    )
                }
                Box(modifier = Modifier.width(1.dp).fillMaxHeight().background(MacosTheme.colorScheme.border))
                Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(16.dp), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Current page:",
                            color = MacosTheme.colorScheme.textSecondary,
                            style = MacosTheme.typography.caption1,
                        )
                        Text(text = activeItem, fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 4.dp))
                    }
                }
            }
        }
        Text(
            text = "Use the toggle or click \"Collapse\" in the sidebar.",
            style = MacosTheme.typography.caption2,
            color = MacosTheme.colorScheme.textTertiary.copy(alpha = 0.70f),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
    }
}

@GalleryExample("Sidebar", "Collapsible")
@Composable
fun SidebarCollapsibleExample() {
    var active by remember { mutableStateOf("Favorites") }
    var collapsed by remember { mutableStateOf(false) }
    val items = remember {
        listOf(
            SidebarItem("Favorites", onClick = { active = "Favorites" }, icon = Icons.Star, group = "Favorites"),
            SidebarItem("Recents", onClick = { active = "Recents" }, icon = Icons.Calendar, group = "Favorites"),
            SidebarItem("Home", onClick = { active = "Home" }, icon = Icons.Home, group = "Locations"),
            SidebarItem("Trash", onClick = { active = "Trash" }, icon = Icons.Trash2, group = "Locations"),
        )
    }
    val pinnedItems = remember {
        listOf(SidebarItem("Settings", onClick = { active = "Settings" }, icon = Icons.Settings))
    }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Switcher(checked = collapsed, onCheckedChange = { collapsed = it }, label = "Collapsed")
        Card(modifier = Modifier.fillMaxWidth().height(192.dp)) {
            Row(modifier = Modifier.fillMaxSize()) {
                Sidebar(
                    items = items,
                    pinnedItems = pinnedItems,
                    activeItem = active,
                    onLogout = {},
                    collapsed = collapsed,
                    onCollapsedChange = { collapsed = it },
                    collapsible = true,
                )
                Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(16.dp), contentAlignment = Alignment.Center) {
                    Text(text = "Current: $active", color = MacosTheme.colorScheme.textSecondary)
                }
            }
        }
    }
}

@GalleryExample("Sidebar", "Disclosure Groups")
@Composable
fun SidebarDisclosureExample() {
    var active by remember { mutableStateOf("Inbox") }
    val items = remember {
        listOf(
            SidebarItem(
                "Mailboxes", onClick = {}, icon = Icons.Folder, group = "Mail",
                children = listOf(
                    SidebarItem("Inbox", onClick = { active = "Inbox" }, icon = Icons.Star),
                    SidebarItem(
                        "Work", onClick = { active = "Work" }, icon = Icons.Folder,
                        children = listOf(
                            SidebarItem(
                                "Projects", onClick = { active = "Projects" }, icon = Icons.Folder,
                                children = listOf(
                                    SidebarItem(
                                        "2026", onClick = { active = "2026" }, icon = Icons.Folder,
                                        children = listOf(
                                            SidebarItem("Q1 Report", onClick = { active = "Q1 Report" }, icon = Icons.Star),
                                            SidebarItem("Q2 Report", onClick = { active = "Q2 Report" }, icon = Icons.Calendar),
                                        ),
                                    ),
                                ),
                            ),
                            SidebarItem("Archive", onClick = { active = "Archive" }, icon = Icons.Trash2),
                        ),
                    ),
                    SidebarItem("Trash", onClick = { active = "Trash" }, icon = Icons.Trash2),
                ),
            ),
            SidebarItem(
                "Smart Mailboxes", onClick = {}, icon = Icons.Settings, group = "Mail",
                children = listOf(
                    SidebarItem("Unread", onClick = { active = "Unread" }, icon = Icons.Home),
                    SidebarItem("Flagged", onClick = { active = "Flagged" }, icon = Icons.Star),
                ),
            ),
            SidebarItem("Downloads", onClick = { active = "Downloads" }, icon = Icons.Download, group = "Locations"),
            SidebarItem("Documents", onClick = { active = "Documents" }, icon = Icons.Folder, group = "Locations"),
        )
    }
    Card(modifier = Modifier.fillMaxWidth().height(480.dp)) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxHeight().background(MacosTheme.colorScheme.muted)) {
                Sidebar(
                    items = items,
                    activeItem = active,
                )
            }
            Box(modifier = Modifier.width(1.dp).fillMaxHeight().background(MacosTheme.colorScheme.border))
            Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(16.dp), contentAlignment = Alignment.Center) {
                Text(text = "Selected: $active", color = MacosTheme.colorScheme.textSecondary)
            }
        }
    }
}

@GalleryExample("Sidebar", "Custom Item Colors")
@Composable
fun SidebarCustomColorsExample() {
    var active by remember { mutableStateOf("Home") }
    val items = remember {
        listOf(
            SidebarItem("Home", onClick = { active = "Home" }, icon = Icons.Home),
            SidebarItem("Favorites", onClick = { active = "Favorites" }, icon = Icons.Star),
            SidebarItem("Downloads", onClick = { active = "Downloads" }, icon = Icons.Download),
            SidebarItem("Trash", onClick = { active = "Trash" }, icon = Icons.Trash2),
        )
    }
    val accentColor = MacosTheme.colorScheme.accent
    val customColors = SidebarDefaults.itemColors(
        activeBackgroundColor = accentColor,
        activeContentColor = Color.White,
    )
    Card(modifier = Modifier.fillMaxWidth().height(260.dp)) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxHeight().background(MacosTheme.colorScheme.muted)) {
                Sidebar(
                    items = items,
                    activeItem = active,
                    itemColors = customColors,
                )
            }
            Box(modifier = Modifier.width(1.dp).fillMaxHeight().background(MacosTheme.colorScheme.border))
            Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(16.dp), contentAlignment = Alignment.Center) {
                Text(text = "Selected: $active", color = MacosTheme.colorScheme.textSecondary)
            }
        }
    }
}

@Composable
internal fun SidebarPage() {
    GalleryPage("Sidebar", "A macOS-style navigation sidebar with collapsible state.") {
        SectionHeader("Examples")
        ExampleCard(title = "Preview", sourceCode = GallerySources.SidebarPreviewExample) { SidebarPreviewExample() }
        ExampleCard(
            title = "Collapsible",
            description = "Icon-only collapse mode — useful for web layouts and secondary panels. " +
                "Do not use in a primary macOS sidebar inside a Scaffold: the collapsed rendering " +
                "breaks the glass material highlight alignment. Use Scaffold's columnVisibility to hide/show the sidebar instead.",
            sourceCode = GallerySources.SidebarCollapsibleExample,
        ) { SidebarCollapsibleExample() }
        ExampleCard(
            title = "Disclosure Groups",
            description = "Sidebar with collapsible children using disclosure chevrons",
            sourceCode = GallerySources.SidebarDisclosureExample,
        ) { SidebarDisclosureExample() }
        ExampleCard(
            title = "Custom Item Colors",
            description = "Sidebar with accent background and white text for active items",
            sourceCode = GallerySources.SidebarCustomColorsExample,
        ) { SidebarCustomColorsExample() }
    }
}
