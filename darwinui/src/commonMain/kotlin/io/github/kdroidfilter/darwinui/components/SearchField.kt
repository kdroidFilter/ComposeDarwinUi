package io.github.kdroidfilter.darwinui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideSearch
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    enabled: Boolean = true,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    focusRequester: FocusRequester = remember { FocusRequester() },
    // Keep backward-compatible params (ignored but API-stable)
    label: String? = null,
    supportingText: String? = null,
    isError: Boolean = false,
    size: InputSize = InputSize.Md,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography
    val isDark = colors.isDark

    // macOS-like search field: pill shape, subtle background, no border
    val shape = DarwinTheme.shapes.full
    val bgColor = if (isDark) Color.White.copy(alpha = 0.08f) else Color.Black.copy(alpha = 0.06f)
    val textColor = colors.textPrimary
    val placeholderColor = colors.textTertiary
    val iconColor = colors.textTertiary
    val cursorColor = colors.accent

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.focusRequester(focusRequester),
        enabled = enabled,
        singleLine = singleLine,
        textStyle = typography.bodyMedium.copy(color = textColor),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(cursorColor),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp)
                    .clip(shape)
                    .background(bgColor, shape)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = LucideSearch,
                    tint = iconColor,
                    modifier = Modifier.size(14.dp),
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 6.dp),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (value.isEmpty()) {
                        CompositionLocalProvider(
                            LocalDarwinContentColor provides placeholderColor,
                            LocalDarwinTextStyle provides typography.bodySmall.copy(color = placeholderColor),
                        ) {
                            Text(placeholder)
                        }
                    }
                    CompositionLocalProvider(
                        LocalDarwinTextStyle provides typography.bodySmall.copy(color = textColor),
                    ) {
                        innerTextField()
                    }
                }
                if (trailingIcon != null) {
                    trailingIcon()
                }
            }
        },
    )
}

@Preview
@Composable
private fun SearchFieldPreview() {
    DarwinTheme {
        var query by remember { mutableStateOf("") }
        SearchField(value = query, onValueChange = { query = it })
    }
}
