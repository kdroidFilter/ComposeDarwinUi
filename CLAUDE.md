# ComposeMacosUI — Project Instructions

## Project Overview
A Kotlin Multiplatform library implementing a macOS/iOS-inspired design system using Compose Multiplatform. Targets Android, iOS, Desktop JVM, and Web (JS + WASM).

## Modules
- **`:macosui`** — Core library (components, theme, icons)
- **`:sample`** — Multiplatform sample app showcasing all components
- **`:gallery-annotations`** — KSP annotation definitions
- **`:gallery-ksp`** — KSP processor for gallery metadata generation

## Tech Stack
- Kotlin 2.3.x, Compose Multiplatform 1.11.x
- KSP for annotation processing
- Detekt + Compose rules for static analysis
- Pure Compose primitives (no Material3 dependency)

## Architecture & Conventions

### Component Design
- Follow `Defaults`/`Colors`/`Elevation` pattern: `ButtonDefaults`, `ButtonColors`, `ButtonElevation`, etc.
- Expose both functional (composable content) and convenience (text-only) overloads
- Use `InteractionSource` for hover/press states with smooth animations
- Follow `CompositionLocal` theming: `LocalColorScheme`, `LocalTypography`, etc.

### Theming
- All design tokens live in `macosui/src/commonMain/.../theme/`
- Use `MacosTheme` as the root provider
- Accent color system: blue, purple, violet, green, orange, red, yellow, cyan, pink, teal, emerald, sky

### Animations
- Use `macosSpring()` and `macosTween()` from the animation system
- Prefer `SpringPreset` enums (e.g., `Snappy`) over raw spring specs

### Platform-Specific Code
- Use expect/actual for platform behaviors (context menus, etc.)
- Platform source sets: `androidMain`, `iosMain`, `jvmMain`, `webMain`

### Gallery / Sample
- Annotate sample composables with `@GalleryEntry` to include them in the gallery
- Each component has a corresponding page in `sample/.../pages/`

## Key Rules
- No Material3 imports in `:macosui`
- Run `./gradlew detekt` before committing to catch Compose rule violations
- New components must have a corresponding sample page
- Keep component APIs minimal and consistent with existing patterns
