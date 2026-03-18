package io.github.kdroidfilter.nucleus.ui.apple.macos.util

import kotlinx.browser.window

// Retrieve the browser's locale once; navigator.language is stable for the session.
private val browserLocale: String get() = window.navigator.language

// js() blocks have access to the enclosing function's parameters as JS variables.
// This compiles for both js and wasmJs targets (opt-in set in build.gradle.kts).
@Suppress("UNUSED_PARAMETER")
private fun jsFormatMonth(year: Int, month: Int, locale: String, style: String): String =
    js("new Date(year, month, 1).toLocaleDateString(locale, {month: style})")

@Suppress("UNUSED_PARAMETER")
private fun jsFormatWeekday(timestamp: Double, locale: String, style: String): String =
    js("new Date(timestamp).toLocaleDateString(locale, {weekday: style})")

internal actual fun localizedMonthNames(): List<String> {
    val locale = browserLocale
    return (0 until 12).map { jsFormatMonth(2000, it, locale, "long") }
}

internal actual fun localizedMonthShortNames(): List<String> {
    val locale = browserLocale
    return (0 until 12).map { jsFormatMonth(2000, it, locale, "short") }
}

internal actual fun localizedWeekdayShortNames(): List<String> {
    val locale = browserLocale
    // Jan 4, 1970 was a Sunday (3 * 86_400_000 ms from epoch)
    val sundayMs = 259_200_000.0
    return (0 until 7).map { jsFormatWeekday(sundayMs + it * 86_400_000.0, locale, "short") }
}
