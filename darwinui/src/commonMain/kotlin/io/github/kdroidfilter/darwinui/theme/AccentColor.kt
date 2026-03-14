package io.github.kdroidfilter.darwinui.theme

import androidx.compose.ui.graphics.Color

/**
 * Predefined accent color palette matching macOS 26 system accent colors.
 * Each accent defines light and dark variants used to derive theme colors.
 *
 * Values extracted from Apple macOS 26 UI Kit (Sketch).
 */
enum class AccentColor(
    /** Accent used in light theme. */
    val light: Color,
    /** Accent used in dark theme. */
    val dark: Color,
    /** Lighter container shade for dark theme. */
    val containerDark: Color,
    /** Lighter container shade for light theme. */
    val containerLight: Color,
) {
    Blue(
        light = Color(0xFF0088FF),
        dark = Color(0xFF0091FF),
        containerDark = Color(0xFF003D73),
        containerLight = Color(0xFFD6ECFF),
    ),
    Purple(
        light = Color(0xFFCB30E0),
        dark = Color(0xFFDB34F2),
        containerDark = Color(0xFF5C1866),
        containerLight = Color(0xFFF5D6FA),
    ),
    Indigo(
        light = Color(0xFF6155F5),
        dark = Color(0xFF6D7CFF),
        containerDark = Color(0xFF2E276E),
        containerLight = Color(0xFFE0DFFE),
    ),
    Pink(
        light = Color(0xFFFF2D55),
        dark = Color(0xFFFF375F),
        containerDark = Color(0xFF73142A),
        containerLight = Color(0xFFFFD6DE),
    ),
    Red(
        light = Color(0xFFFF383C),
        dark = Color(0xFFFF4245),
        containerDark = Color(0xFF73191B),
        containerLight = Color(0xFFFFD6D7),
    ),
    Orange(
        light = Color(0xFFFF8D28),
        dark = Color(0xFFFF9230),
        containerDark = Color(0xFF734012),
        containerLight = Color(0xFFFFE8D1),
    ),
    Yellow(
        light = Color(0xFFFFCC00),
        dark = Color(0xFFFFD600),
        containerDark = Color(0xFF735C00),
        containerLight = Color(0xFFFFF5CC),
    ),
    Green(
        light = Color(0xFF34C759),
        dark = Color(0xFF30D158),
        containerDark = Color(0xFF175928),
        containerLight = Color(0xFFD4F5DD),
    ),
    Teal(
        light = Color(0xFF00C3D0),
        dark = Color(0xFF00D2E0),
        containerDark = Color(0xFF00585E),
        containerLight = Color(0xFFCCF3F6),
    ),
    Cyan(
        light = Color(0xFF00C0E8),
        dark = Color(0xFF3CD3FE),
        containerDark = Color(0xFF005668),
        containerLight = Color(0xFFCCF2FB),
    ),
    Mint(
        light = Color(0xFF00C8B3),
        dark = Color(0xFF00DAC3),
        containerDark = Color(0xFF005A50),
        containerLight = Color(0xFFCCF4F0),
    ),
    Brown(
        light = Color(0xFFAC7F5E),
        dark = Color(0xFFB78A66),
        containerDark = Color(0xFF4D392A),
        containerLight = Color(0xFFF0E5DB),
    ),
    Graphite(
        light = Color(0xFF8E8E93),
        dark = Color(0xFF8E8E93),
        containerDark = Color(0xFF3A3A3C),
        containerLight = Color(0xFFE5E5EA),
    ),
}
