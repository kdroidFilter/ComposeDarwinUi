package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.IconButton
import io.github.kdroidfilter.darwinui.components.IconButtonRole
import io.github.kdroidfilter.darwinui.components.IconButtonStyle
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.icons.LucideHeart
import io.github.kdroidfilter.darwinui.icons.LucideShare2
import io.github.kdroidfilter.darwinui.icons.LucideStar
import io.github.kdroidfilter.darwinui.icons.LucideTrash2
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
private fun IconButtonPreview() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(icon = LucideHeart, onClick = {}, style = IconButtonStyle.Bordered)
        IconButton(icon = LucideStar, onClick = {}, style = IconButtonStyle.BorderedProminent)
        IconButton(icon = LucideShare2, onClick = {}, style = IconButtonStyle.Borderless)
        IconButton(icon = LucideTrash2, onClick = {}, style = IconButtonStyle.Bordered, role = IconButtonRole.Destructive)
        IconButton(icon = LucideTrash2, onClick = {}, style = IconButtonStyle.BorderedProminent, role = IconButtonRole.Destructive)
        IconButton(icon = LucideTrash2, onClick = {}, style = IconButtonStyle.Borderless, role = IconButtonRole.Destructive)
    }
}

@GalleryExample("IconButton", "Styles")
@Composable
fun IconButtonStylesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(icon = LucideHeart, onClick = {}, style = IconButtonStyle.Bordered)
            IconButton(icon = LucideHeart, onClick = {}, style = IconButtonStyle.Bordered, enabled = false)
            Text("Bordered", style = DarwinTheme.typography.caption1, color = DarwinTheme.colorScheme.textSecondary)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(icon = LucideStar, onClick = {}, style = IconButtonStyle.BorderedProminent)
            IconButton(icon = LucideStar, onClick = {}, style = IconButtonStyle.BorderedProminent, enabled = false)
            Text("Bordered Prominent", style = DarwinTheme.typography.caption1, color = DarwinTheme.colorScheme.textSecondary)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(icon = LucideShare2, onClick = {}, style = IconButtonStyle.Borderless)
            IconButton(icon = LucideShare2, onClick = {}, style = IconButtonStyle.Borderless, enabled = false)
            Text("Borderless", style = DarwinTheme.typography.caption1, color = DarwinTheme.colorScheme.textSecondary)
        }
    }
}

@GalleryExample("IconButton", "Destructive")
@Composable
fun IconButtonDestructiveExample() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(icon = LucideTrash2, onClick = {}, style = IconButtonStyle.Bordered, role = IconButtonRole.Destructive)
            IconButton(icon = LucideTrash2, onClick = {}, style = IconButtonStyle.BorderedProminent, role = IconButtonRole.Destructive)
            IconButton(icon = LucideTrash2, onClick = {}, style = IconButtonStyle.Borderless, role = IconButtonRole.Destructive)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(icon = LucideTrash2, onClick = {}, style = IconButtonStyle.Bordered, role = IconButtonRole.Destructive, enabled = false)
            IconButton(icon = LucideTrash2, onClick = {}, style = IconButtonStyle.BorderedProminent, role = IconButtonRole.Destructive, enabled = false)
            IconButton(icon = LucideTrash2, onClick = {}, style = IconButtonStyle.Borderless, role = IconButtonRole.Destructive, enabled = false)
        }
    }
}

@GalleryExample("IconButton", "Sizes")
@Composable
fun IconButtonSizesExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        for (size in ControlSize.entries) {
            ControlSize(size) {
                IconButton(icon = LucideHeart, onClick = {}, style = IconButtonStyle.Bordered)
            }
        }
    }
}

@Composable
internal fun IconButtonPage() {
    GalleryPage("Icon Button", "Circular icon-only buttons for toolbar and content area actions.") {
        PreviewContainer { IconButtonPreview() }

        SectionHeader("Usage")
        CodeBlock("""IconButton(icon = LucideHeart, onClick = {})
IconButton(icon = LucideStar, onClick = {}, style = IconButtonStyle.BorderedProminent)
IconButton(icon = LucideTrash2, onClick = {}, role = IconButtonRole.Destructive)""")

        SectionHeader("Styles")
        ExampleCard(
            title = "All Styles",
            description = "Bordered, Bordered Prominent, and Borderless with idle and disabled states",
            sourceCode = GallerySources.IconButtonStylesExample,
        ) { IconButtonStylesExample() }

        SectionHeader("Examples")
        ExampleCard(
            title = "Destructive",
            description = "All styles with destructive role",
            sourceCode = GallerySources.IconButtonDestructiveExample,
        ) { IconButtonDestructiveExample() }
        ExampleCard(
            title = "Sizes",
            description = "Icon button at each ControlSize level",
            sourceCode = GallerySources.IconButtonSizesExample,
        ) { IconButtonSizesExample() }
    }
}
