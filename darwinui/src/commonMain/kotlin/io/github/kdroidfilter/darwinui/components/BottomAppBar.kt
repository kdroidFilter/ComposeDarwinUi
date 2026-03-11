package io.github.kdroidfilter.darwinui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor

// ===========================================================================
// BottomAppBarDefaults — mirrors M3's BottomAppBarDefaults
// ===========================================================================

object BottomAppBarDefaults {
    val containerColor: Color @Composable get() = DarwinTheme.colorScheme.surfaceContainer
    val contentColor: Color @Composable get() = DarwinTheme.colorScheme.onSurfaceVariant
    val ContainerElevation: Dp = 3.dp
    val ContentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
}

// ===========================================================================
// BottomAppBar — mirrors M3's BottomAppBar
// ===========================================================================

@Composable
fun BottomAppBar(
    actions: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    floatingActionButton: (@Composable () -> Unit)? = null,
    containerColor: Color = BottomAppBarDefaults.containerColor,
    contentColor: Color = BottomAppBarDefaults.contentColor,
    tonalElevation: Dp = BottomAppBarDefaults.ContainerElevation,
    contentPadding: PaddingValues = BottomAppBarDefaults.ContentPadding,
) {
    CompositionLocalProvider(LocalDarwinContentColor provides contentColor) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(containerColor)
                .border(
                    width = 1.dp,
                    color = DarwinTheme.colorScheme.outlineVariant,
                )
                .padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            // Action icons on the left
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                content = actions,
            )

            // Optional FAB anchored to the right with end padding
            if (floatingActionButton != null) {
                Box(modifier = Modifier.padding(end = 16.dp)) {
                    floatingActionButton()
                }
            }
        }
    }
}
