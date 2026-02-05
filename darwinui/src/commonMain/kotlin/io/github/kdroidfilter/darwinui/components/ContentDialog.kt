package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.darwinui.components.Button
import io.github.kdroidfilter.darwinui.components.PrimaryButton
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import kotlinx.coroutines.delay

// ===========================================================================
// Dialog Size
// ===========================================================================

enum class DialogSize(val maxWidth: Dp) {
    Standard(448.dp),
    Small(384.dp),
    Large(512.dp),
    ExtraLarge(576.dp),
    Full(720.dp),
}

// ===========================================================================
// ContentDialogButton
// ===========================================================================

enum class ContentDialogButton {
    Primary,
    Secondary,
    Close,
}

// ===========================================================================
// ContentDialog — flat API
// ===========================================================================

/**
 * A dialog with a flat API.
 *
 * @param title The dialog title.
 * @param visible Whether the dialog is shown.
 * @param content The dialog body content.
 * @param primaryButtonText Label for the primary action button.
 * @param secondaryButtonText Optional label for a secondary action button.
 * @param closeButtonText Optional label for a close/cancel button.
 * @param onButtonClick Callback invoked when any button is clicked.
 * @param size The dialog size.
 */
@Composable
fun ContentDialog(
    title: String,
    visible: Boolean,
    content: @Composable () -> Unit,
    primaryButtonText: String,
    secondaryButtonText: String? = null,
    closeButtonText: String? = null,
    onButtonClick: (ContentDialogButton) -> Unit,
    size: DialogSize = DialogSize.Standard,
) {
    var showPopup by remember { mutableStateOf(false) }
    var animateIn by remember { mutableStateOf(false) }

    LaunchedEffect(visible) {
        if (visible) {
            showPopup = true
            delay(16)
            animateIn = true
        } else {
            animateIn = false
            delay(DarwinDuration.Slow.millis.toLong() + 50)
            showPopup = false
        }
    }

    if (showPopup) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = { onButtonClick(ContentDialogButton.Close) },
            properties = PopupProperties(focusable = true),
        ) {
            val colors = DarwinTheme.colors
            val scrimColor = if (colors.isDark) Color.Black.copy(alpha = 0.50f) else Color.Black.copy(alpha = 0.30f)

            // Scrim
            AnimatedVisibility(
                visible = animateIn,
                enter = fadeIn(tween(DarwinDuration.Fast.millis)),
                exit = fadeOut(tween(DarwinDuration.Slow.millis)),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(scrimColor)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = { onButtonClick(ContentDialogButton.Close) },
                        ),
                )
            }

            // Dialog content
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                AnimatedVisibility(
                    visible = animateIn,
                    enter = fadeIn(tween(DarwinDuration.Slow.millis)) +
                            scaleIn(
                                initialScale = 0.95f,
                                transformOrigin = TransformOrigin.Center,
                                animationSpec = tween(DarwinDuration.Slow.millis),
                            ) +
                            slideInVertically(
                                initialOffsetY = { 10 },
                                animationSpec = tween(DarwinDuration.Slow.millis),
                            ),
                    exit = fadeOut(tween(DarwinDuration.Slow.millis)) +
                            scaleOut(
                                targetScale = 0.95f,
                                transformOrigin = TransformOrigin.Center,
                                animationSpec = tween(DarwinDuration.Slow.millis),
                            ) +
                            slideOutVertically(
                                targetOffsetY = { 10 },
                                animationSpec = tween(DarwinDuration.Slow.millis),
                            ),
                ) {
                    val shape = RoundedCornerShape(16.dp)
                    val bgColor = if (colors.isDark) Color(0xFF18181B).copy(alpha = 0.95f)
                    else Color.White.copy(alpha = 0.95f)
                    val borderColor = if (colors.isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)

                    Box(
                        modifier = Modifier
                            .widthIn(max = size.maxWidth)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .shadow(elevation = 24.dp, shape = shape)
                            .clip(shape)
                            .background(bgColor, shape)
                            .border(1.dp, borderColor, shape)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = { /* consume click */ },
                            ),
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            // Title
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 24.dp, end = 24.dp, top = 24.dp),
                            ) {
                                Text(
                                    text = title,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = colors.textPrimary,
                                )
                            }

                            // Body
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp, vertical = 16.dp),
                            ) {
                                content()
                            }

                            // Footer buttons
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                if (closeButtonText != null) {
                                    Button(
                                        onClick = { onButtonClick(ContentDialogButton.Close) },
                                    ) {
                                        Text(closeButtonText)
                                    }
                                }
                                if (secondaryButtonText != null) {
                                    Button(
                                        onClick = { onButtonClick(ContentDialogButton.Secondary) },
                                    ) {
                                        Text(secondaryButtonText)
                                    }
                                }
                                PrimaryButton(
                                    onClick = { onButtonClick(ContentDialogButton.Primary) },
                                ) {
                                    Text(primaryButtonText)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ContentDialogPreview() {
    DarwinTheme {
        ContentDialog(
            title = "Confirm",
            visible = true,
            content = { Text("Are you sure?") },
            primaryButtonText = "Yes",
            closeButtonText = "Cancel",
            onButtonClick = {},
        )
    }
}
