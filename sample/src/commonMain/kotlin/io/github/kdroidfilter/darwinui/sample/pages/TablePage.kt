package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.runtime.Composable
import io.github.kdroidfilter.darwinui.components.Badge
import io.github.kdroidfilter.darwinui.components.BadgeVariant
import io.github.kdroidfilter.darwinui.components.Table
import io.github.kdroidfilter.darwinui.components.TableBody
import io.github.kdroidfilter.darwinui.components.TableCell
import io.github.kdroidfilter.darwinui.components.TableHead
import io.github.kdroidfilter.darwinui.components.TableHeaderCell
import io.github.kdroidfilter.darwinui.components.TableRow
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@GalleryExample("Table", "Default")
@Composable
fun TableDefaultExample() {
    Table {
        TableHead {
            TableRow {
                TableHeaderCell { Text("Name") }
                TableHeaderCell { Text("Email") }
                TableHeaderCell { Text("Role") }
                TableHeaderCell(weight = 0.5f) { Text("Status") }
            }
        }
        TableBody(scrollable = false) {
            TableRow {
                TableCell { Text("Alice Smith") }
                TableCell { Text("alice@example.com") }
                TableCell { Text("Admin") }
                TableCell(weight = 0.5f) {
                    Badge(variant = BadgeVariant.Success) {
                        Text("Active")
                    }
                }
            }
            TableRow {
                TableCell { Text("Bob Jones") }
                TableCell { Text("bob@example.com") }
                TableCell { Text("Editor") }
                TableCell(weight = 0.5f) {
                    Badge(variant = BadgeVariant.Warning) {
                        Text("Pending")
                    }
                }
            }
            TableRow {
                TableCell { Text("Carol White") }
                TableCell { Text("carol@example.com") }
                TableCell { Text("Viewer") }
                TableCell(weight = 0.5f) {
                    Badge(variant = BadgeVariant.Archived) {
                        Text("Inactive")
                    }
                }
            }
        }
    }
}

@Composable
internal fun TablePage() {
    GalleryPage("Table", "A responsive table component for displaying tabular data.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.TableDefaultExample) { TableDefaultExample() }
    }
}
