package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Pencil
import com.composables.icons.lucide.Plus
import io.github.kdroidfilter.darwinui.components.ExtendedFloatingActionButton as DarwinExtendedFAB
import io.github.kdroidfilter.darwinui.components.FloatingActionButton as DarwinFAB
import io.github.kdroidfilter.darwinui.components.LargeFloatingActionButton as DarwinLargeFAB
import io.github.kdroidfilter.darwinui.components.SmallFloatingActionButton as DarwinSmallFAB
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import androidx.compose.material3.FloatingActionButton as M3FAB
import androidx.compose.material3.Text as M3Text

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun FabPage() {
    GalleryPage("Floating Action Button", "Darwin FAB vs Material 3 FloatingActionButton variants.") {
        SectionHeader("Sizes")
        ComparisonSection(
            darwinContent = {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    DarwinSmallFAB(onClick = {}) { Icon(Lucide.Plus) }
                    DarwinFAB(onClick = {}) { Icon(Lucide.Plus) }
                    DarwinLargeFAB(onClick = {}) { Icon(Lucide.Plus) }
                }
            },
            materialContent = {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    SmallFloatingActionButton(onClick = {}) { Icon(Lucide.Plus) }
                    M3FAB(onClick = {}) { Icon(Lucide.Plus) }
                    LargeFloatingActionButton(onClick = {}) { Icon(Lucide.Plus) }
                }
            },
        )

        SectionHeader("Extended")
        ComparisonSection(
            darwinContent = {
                DarwinExtendedFAB(
                    onClick = {},
                    icon = { Icon(Lucide.Pencil) },
                    text = { Text("New note") },
                )
            },
            materialContent = {
                ExtendedFloatingActionButton(
                    onClick = {},
                    icon = { Icon(Lucide.Pencil) },
                    text = { M3Text("New note") },
                )
            },
        )
    }
}
