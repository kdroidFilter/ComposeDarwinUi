package io.github.kdroidfilter.darwinui.sample.gallery

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.ClipData
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.text.AnnotatedString
import kotlinx.coroutines.launch
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.icons.LucideCheck
import io.github.kdroidfilter.darwinui.icons.LucideCopy
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import kotlinx.coroutines.delay
import androidx.compose.foundation.Image

@Composable
fun CodeBlock(code: String) {
    val clipboard = LocalClipboard.current
    var copied by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(copied) {
        if (copied) {
            delay(2000)
            copied = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(DarwinTheme.shapes.medium)
            .background(DarwinTheme.colors.backgroundSubtle),
    ) {
        // Header with language label and copy button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DarwinText(
                text = "KOTLIN",
                style = DarwinTheme.typography.labelSmall,
                color = DarwinTheme.colors.textTertiary,
                fontWeight = FontWeight.Medium,
            )

            Row(
                modifier = Modifier
                    .clip(DarwinTheme.shapes.small)
                    .clickable {
                        coroutineScope.launch {
                            clipboard.setClipEntry(
                                ClipEntry(ClipData(AnnotatedString(code)))
                            )
                        }
                        copied = true
                    }
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AnimatedContent(
                    targetState = copied,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "copyIcon",
                ) { isCopied ->
                    Image(
                        imageVector = if (isCopied) LucideCheck else LucideCopy,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        colorFilter = ColorFilter.tint(
                            if (isCopied) DarwinTheme.colors.success
                            else DarwinTheme.colors.textTertiary
                        ),
                    )
                }
                AnimatedContent(
                    targetState = copied,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "copyText",
                ) { isCopied ->
                    DarwinText(
                        text = if (isCopied) "Copied!" else "Copy",
                        style = DarwinTheme.typography.labelSmall,
                        fontWeight = FontWeight.Medium,
                        color = if (isCopied) DarwinTheme.colors.success
                        else DarwinTheme.colors.textTertiary,
                    )
                }
            }
        }

        // Code content
        SelectionContainer {
            BasicText(
                text = code,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = DarwinTheme.typography.bodySmall.fontSize,
                    color = DarwinTheme.colors.textSecondary,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            )
        }
    }
}
