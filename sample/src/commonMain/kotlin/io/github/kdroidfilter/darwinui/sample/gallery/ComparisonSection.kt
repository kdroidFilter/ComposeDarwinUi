package io.github.kdroidfilter.darwinui.sample.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@Composable
fun ComparisonSection(
    darwinContent: @Composable ColumnScope.() -> Unit,
    materialContent: @Composable ColumnScope.() -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ComparisonPane(
            title = "Darwin",
            modifier = Modifier.weight(1f),
            content = darwinContent,
        )
        ComparisonPane(
            title = "Material 3",
            modifier = Modifier.weight(1f),
            content = materialContent,
        )
    }
}

@Composable
private fun ComparisonPane(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .clip(DarwinTheme.shapes.large)
            .border(1.dp, DarwinTheme.colors.border, DarwinTheme.shapes.large)
            .background(DarwinTheme.colors.card),
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarwinTheme.colors.backgroundSubtle)
                .padding(horizontal = 16.dp, vertical = 10.dp),
        ) {
            Text(
                text = title,
                style = DarwinTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = DarwinTheme.colors.textSecondary,
            )
        }
        // Separator
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarwinTheme.colors.border)
                .defaultMinSize(minHeight = 1.dp),
        )
        // Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 80.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = content,
        )
    }
}
