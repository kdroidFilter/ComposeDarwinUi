package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SegmentedControl
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SegmentedControlDefaults
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalWindowActive
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.SegmentedControlVariant
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader

@Composable
internal fun SegmentedControlPage() {
    GalleryPage("Segmented Control", "macOS-style segmented control with accent-colored indicator.") {
        val trio = listOf("1", "2", "3")
        val duo = listOf("1", "2")

        SectionHeader("Content Area")
        ExampleCard(
            title = "Trio — Active window",
            description = "3 segments across all sizes, last segment selected",
            sourceCode = """
                ControlSize(size) {
                    SegmentedControl(
                        options = listOf("1", "2", "3"),
                        selectedIndex = 2,
                        onSelectedIndexChange = { },
                    )
                }
            """.trimIndent(),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                for (size in ControlSize.entries) {
                    ControlSize(size) {
                        var sel by remember { mutableStateOf(2) }
                        SegmentedControl(
                            options = trio,
                            selectedIndex = sel,
                            onSelectedIndexChange = { sel = it },
                        )
                    }
                }
            }
        }

        ExampleCard(
            title = "Trio — Inactive window",
            description = "Same controls with inactive window state",
            sourceCode = """
                CompositionLocalProvider(LocalWindowActive provides false) {
                    SegmentedControl(...)
                }
            """.trimIndent(),
        ) {
            CompositionLocalProvider(LocalWindowActive provides false) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    for (size in ControlSize.entries) {
                        ControlSize(size) {
                            var sel by remember { mutableStateOf(2) }
                            SegmentedControl(
                                options = trio,
                                selectedIndex = sel,
                                onSelectedIndexChange = { sel = it },
                            )
                        }
                    }
                }
            }
        }

        ExampleCard(
            title = "Duo — Active window",
            description = "2 segments across all sizes",
            sourceCode = """
                ControlSize(size) {
                    SegmentedControl(
                        options = listOf("1", "2"),
                        selectedIndex = 0,
                        onSelectedIndexChange = { },
                    )
                }
            """.trimIndent(),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                for (size in ControlSize.entries) {
                    ControlSize(size) {
                        var sel by remember { mutableStateOf(0) }
                        SegmentedControl(
                            options = duo,
                            selectedIndex = sel,
                            onSelectedIndexChange = { sel = it },
                        )
                    }
                }
            }
        }

        ExampleCard(
            title = "Duo — Inactive window",
            description = "Same controls with inactive window state",
            sourceCode = """
                CompositionLocalProvider(LocalWindowActive provides false) {
                    SegmentedControl(...)
                }
            """.trimIndent(),
        ) {
            CompositionLocalProvider(LocalWindowActive provides false) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    for (size in ControlSize.entries) {
                        ControlSize(size) {
                            var sel by remember { mutableStateOf(0) }
                            SegmentedControl(
                                options = duo,
                                selectedIndex = sel,
                                onSelectedIndexChange = { sel = it },
                            )
                        }
                    }
                }
            }
        }

        SectionHeader("Over-glass")
        ExampleCard(
            title = "Trio — Active window",
            description = "Over-glass variant for use on translucent surfaces",
            sourceCode = """
                SegmentedControl(
                    options = listOf("1", "2", "3"),
                    selectedIndex = 2,
                    onSelectedIndexChange = { },
                    colors = SegmentedControlDefaults.colors(
                        variant = SegmentedControlVariant.OverGlass,
                    ),
                )
            """.trimIndent(),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                for (size in ControlSize.entries) {
                    ControlSize(size) {
                        var sel by remember { mutableStateOf(2) }
                        SegmentedControl(
                            options = trio,
                            selectedIndex = sel,
                            onSelectedIndexChange = { sel = it },
                            colors = SegmentedControlDefaults.colors(
                                variant = SegmentedControlVariant.OverGlass,
                            ),
                        )
                    }
                }
            }
        }

        ExampleCard(
            title = "Trio — Inactive window",
            description = "Over-glass with inactive window state",
            sourceCode = """
                CompositionLocalProvider(LocalWindowActive provides false) {
                    SegmentedControl(
                        ...,
                        colors = SegmentedControlDefaults.colors(
                            variant = SegmentedControlVariant.OverGlass,
                        ),
                    )
                }
            """.trimIndent(),
        ) {
            CompositionLocalProvider(LocalWindowActive provides false) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    for (size in ControlSize.entries) {
                        ControlSize(size) {
                            var sel by remember { mutableStateOf(2) }
                            SegmentedControl(
                                options = trio,
                                selectedIndex = sel,
                                onSelectedIndexChange = { sel = it },
                                colors = SegmentedControlDefaults.colors(
                                    variant = SegmentedControlVariant.OverGlass,
                                ),
                            )
                        }
                    }
                }
            }
        }

        ExampleCard(
            title = "Duo — Active window",
            description = "Over-glass duo variant",
            sourceCode = """
                SegmentedControl(
                    options = listOf("1", "2"),
                    selectedIndex = 0,
                    onSelectedIndexChange = { },
                    colors = SegmentedControlDefaults.colors(
                        variant = SegmentedControlVariant.OverGlass,
                    ),
                )
            """.trimIndent(),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                for (size in ControlSize.entries) {
                    ControlSize(size) {
                        var sel by remember { mutableStateOf(0) }
                        SegmentedControl(
                            options = duo,
                            selectedIndex = sel,
                            onSelectedIndexChange = { sel = it },
                            colors = SegmentedControlDefaults.colors(
                                variant = SegmentedControlVariant.OverGlass,
                            ),
                        )
                    }
                }
            }
        }

        ExampleCard(
            title = "Duo — Inactive window",
            description = "Over-glass duo with inactive window state",
            sourceCode = """
                CompositionLocalProvider(LocalWindowActive provides false) {
                    SegmentedControl(
                        ...,
                        colors = SegmentedControlDefaults.colors(
                            variant = SegmentedControlVariant.OverGlass,
                        ),
                    )
                }
            """.trimIndent(),
        ) {
            CompositionLocalProvider(LocalWindowActive provides false) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    for (size in ControlSize.entries) {
                        ControlSize(size) {
                            var sel by remember { mutableStateOf(0) }
                            SegmentedControl(
                                options = duo,
                                selectedIndex = sel,
                                onSelectedIndexChange = { sel = it },
                                colors = SegmentedControlDefaults.colors(
                                    variant = SegmentedControlVariant.OverGlass,
                                ),
                            )
                        }
                    }
                }
            }
        }

        SectionHeader("Disabled")
        ExampleCard(
            title = "Disabled",
            description = "Non-interactive disabled state",
            sourceCode = """
                SegmentedControl(
                    options = listOf("On", "Off"),
                    selectedIndex = 0,
                    onSelectedIndexChange = {},
                    enabled = false,
                )
            """.trimIndent(),
        ) {
            SegmentedControl(
                options = listOf("On", "Off"),
                selectedIndex = 0,
                onSelectedIndexChange = {},
                enabled = false,
            )
        }

        SectionHeader("Custom colors")
        ExampleCard(
            title = "Custom pill color",
            description = "Override selectedSegment to use a custom color",
            sourceCode = """
                SegmentedControl(
                    options = listOf("Red", "Green", "Blue"),
                    selectedIndex = selected,
                    onSelectedIndexChange = { selected = it },
                    colors = SegmentedControlDefaults.colors(
                        selectedSegment = Color(0xFFE74C3C),
                    ),
                )
            """.trimIndent(),
        ) {
            var sel by remember { mutableStateOf(0) }
            val customPillColors = listOf(
                Color(0xFFE74C3C),
                Color(0xFF2ECC71),
                Color(0xFF3498DB),
            )
            SegmentedControl(
                options = listOf("Red", "Green", "Blue"),
                selectedIndex = sel,
                onSelectedIndexChange = { sel = it },
                colors = SegmentedControlDefaults.colors(
                    selectedSegment = customPillColors[sel],
                ),
            )
        }

        ExampleCard(
            title = "Custom track & content colors",
            description = "Override track, selected content, and unselected content",
            sourceCode = """
                SegmentedControl(
                    options = listOf("Dark", "Light"),
                    selectedIndex = selected,
                    onSelectedIndexChange = { selected = it },
                    colors = SegmentedControlDefaults.colors(
                        track = Color(0xFF1A1A2E),
                        selectedSegment = Color(0xFFE94560),
                        selectedContent = Color.White,
                        unselectedContent = Color(0xFF999999),
                    ),
                )
            """.trimIndent(),
        ) {
            var sel by remember { mutableStateOf(0) }
            SegmentedControl(
                options = listOf("Dark", "Light"),
                selectedIndex = sel,
                onSelectedIndexChange = { sel = it },
                colors = SegmentedControlDefaults.colors(
                    track = Color(0xFF1A1A2E),
                    selectedSegment = Color(0xFFE94560),
                    selectedContent = Color.White,
                    unselectedContent = Color(0xFF999999),
                ),
            )
        }

        SectionHeader("Custom shape")
        ExampleCard(
            title = "Rounded pill shape",
            description = "Override shape with a fully rounded corner radius",
            sourceCode = """
                SegmentedControl(
                    options = listOf("A", "B", "C"),
                    selectedIndex = selected,
                    onSelectedIndexChange = { selected = it },
                    shape = RoundedCornerShape(50),
                )
            """.trimIndent(),
        ) {
            var sel by remember { mutableStateOf(1) }
            SegmentedControl(
                options = listOf("A", "B", "C"),
                selectedIndex = sel,
                onSelectedIndexChange = { sel = it },
                shape = RoundedCornerShape(50),
            )
        }

        ExampleCard(
            title = "Square shape",
            description = "Override shape with no corner radius",
            sourceCode = """
                SegmentedControl(
                    options = listOf("X", "Y"),
                    selectedIndex = selected,
                    onSelectedIndexChange = { selected = it },
                    shape = RoundedCornerShape(0.dp),
                )
            """.trimIndent(),
        ) {
            var sel by remember { mutableStateOf(0) }
            SegmentedControl(
                options = listOf("X", "Y"),
                selectedIndex = sel,
                onSelectedIndexChange = { sel = it },
                shape = RoundedCornerShape(0.dp),
            )
        }

        ExampleCard(
            title = "Combined custom colors + shape",
            description = "Full customisation: colors, shape, and over-glass base",
            sourceCode = """
                SegmentedControl(
                    options = listOf("Sunrise", "Sunset"),
                    selectedIndex = selected,
                    onSelectedIndexChange = { selected = it },
                    colors = SegmentedControlDefaults.colors(
                        variant = SegmentedControlVariant.OverGlass,
                        track = Color(0xFF2D1B69),
                        selectedSegment = Color(0xFFFF6B35),
                        selectedContent = Color.White,
                        unselectedContent = Color(0xFFBB86FC),
                    ),
                    shape = RoundedCornerShape(50),
                )
            """.trimIndent(),
        ) {
            var sel by remember { mutableStateOf(0) }
            SegmentedControl(
                options = listOf("Sunrise", "Sunset"),
                selectedIndex = sel,
                onSelectedIndexChange = { sel = it },
                colors = SegmentedControlDefaults.colors(
                    variant = SegmentedControlVariant.OverGlass,
                    track = Color(0xFF2D1B69),
                    selectedSegment = Color(0xFFFF6B35),
                    selectedContent = Color.White,
                    unselectedContent = Color(0xFFBB86FC),
                ),
                shape = RoundedCornerShape(50),
            )
        }
    }
}
