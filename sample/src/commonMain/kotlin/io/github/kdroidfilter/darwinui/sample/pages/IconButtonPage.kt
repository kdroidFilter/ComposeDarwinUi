package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Settings
import com.composables.icons.lucide.Star
import io.github.kdroidfilter.darwinui.components.FilledIconButton
import io.github.kdroidfilter.darwinui.components.IconButton
import io.github.kdroidfilter.darwinui.components.OutlinedIconButton
import io.github.kdroidfilter.darwinui.components.PrimaryButton
import io.github.kdroidfilter.darwinui.components.SecondaryButton
import io.github.kdroidfilter.darwinui.components.OutlineButton
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader

@Composable
internal fun IconButtonPage() {
    GalleryPage("Icon Button", "Compare Darwin icon-in-button with Material 3 dedicated icon button components.") {
        SectionHeader("Variants")
        ComparisonSection(
            darwinContent = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PrimaryButton(onClick = {}) { Icon(Lucide.Settings) }
                    SecondaryButton(onClick = {}) { Icon(Lucide.Heart) }
                    OutlineButton(onClick = {}) { Icon(Lucide.Star) }
                }
            },
            materialContent = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(onClick = {}) { Icon(Lucide.Settings) }
                    FilledIconButton(onClick = {}) { Icon(Lucide.Heart) }
                    OutlinedIconButton(onClick = {}) { Icon(Lucide.Star) }
                }
            },
        )

        SectionHeader("Disabled")
        ComparisonSection(
            darwinContent = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PrimaryButton(onClick = {}, enabled = false) { Icon(Lucide.Settings) }
                    SecondaryButton(onClick = {}, enabled = false) { Icon(Lucide.Heart) }
                    OutlineButton(onClick = {}, enabled = false) { Icon(Lucide.Star) }
                }
            },
            materialContent = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(onClick = {}, enabled = false) { Icon(Lucide.Settings) }
                    FilledIconButton(onClick = {}, enabled = false) { Icon(Lucide.Heart) }
                    OutlinedIconButton(onClick = {}, enabled = false) { Icon(Lucide.Star) }
                }
            },
        )
    }
}
