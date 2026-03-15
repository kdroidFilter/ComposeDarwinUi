# Spécification : Alignement API ComposeDarwinUI sur Apple SwiftUI/AppKit

## Contexte

L'API actuelle de `DarwinScaffold`, `TitleBar` et `Sidebar` couvre le cas basique (sidebar + titlebar + content) mais diverge significativement de l'API Apple sur plusieurs axes : layout multi-colonnes, système de toolbar flexible, inspecteur, styles de fenêtre, et sidebar redimensionnable. Ce document décrit en détail le comportement attendu de chaque API à implémenter pour matcher Apple à 100%.

---

## 1. Layout multi-colonnes — `NavigationSplitView` → `DarwinScaffold`

### 1.1 Layout 2 colonnes (existant, à adapter)

**API actuelle :**
```kotlin
DarwinScaffold(
    sidebar = { ... },
    content = { paddingValues -> ... }
)
```

**Pas de changement majeur**, mais la sidebar doit supporter le redimensionnement (voir §6).

### 1.2 Layout 3 colonnes (nouveau)

**API cible :**
```kotlin
DarwinScaffold(
    sidebar = { ... },
    contentList = { ... },   // NOUVEAU — colonne du milieu
    content = { paddingValues -> ... }  // colonne de droite (detail)
)
```

**Comportement attendu :**
- Quand `contentList` est fourni, le layout passe en 3 colonnes : `sidebar | contentList | content`
- `contentList` a une largeur par défaut de **250.dp**, redimensionnable entre **200.dp** et **400.dp**
- Un séparateur vertical draggable (1.dp) sépare `contentList` de `content`
- La colonne `contentList` a le même fond que la sidebar (légèrement teinté, `surface` du thème)
- `contentList` reçoit la même bordure droite que la sidebar
- Le title bar s'étend sur toute la largeur (au-dessus des 3 colonnes) comme dans Finder/Mail
- Quand la sidebar est masquée, `contentList` reste visible (comme Apple Mail)
- Le contenu `content` prend tout l'espace restant (weight = 1f)
- Animation : quand `contentList` apparaît/disparaît, utiliser `darwinSpring(DarwinSpringPreset.Snappy)` comme la sidebar

**Exemple concret (Apple Mail) :**
```
┌─────────────────────────────────────────────────────┐
│  TitleBar (glass blur, pleine largeur)              │
├──────────┬──────────────┬───────────────────────────┤
│ Sidebar  │ ContentList  │ Content (Detail)          │
│ 240dp    │ 250dp        │ reste                     │
│          │              │                           │
│ Favoris  │ Messages     │ Message sélectionné       │
│ Boîtes   │ triés par    │ affiché en entier         │
│          │ date         │                           │
└──────────┴──────────────┴───────────────────────────┘
```

### 1.3 Visibilité des colonnes — `ColumnVisibility`

**API cible :**
```kotlin
enum class ColumnVisibility {
    All,            // Toutes les colonnes visibles
    DoubleColumn,   // Masque la sidebar, garde contentList + content
    DetailOnly,     // Seul content est visible
    Automatic       // Adapté au contexte (= All sur desktop)
}
```

**Intégration dans DarwinScaffold :**
```kotlin
DarwinScaffold(
    columnVisibility: ColumnVisibility = ColumnVisibility.Automatic,
    onColumnVisibilityChange: ((ColumnVisibility) -> Unit)? = null,
    // ... existing params
)
```

**Comportement attendu :**
- `All` : sidebar + contentList + content visibles (ou sidebar + content si pas de contentList)
- `DoubleColumn` : sidebar masquée avec animation slide-out, contentList + content visibles
- `DetailOnly` : sidebar + contentList masquées, seul content visible en plein écran
- `Automatic` : équivalent à `All` sur desktop, pourrait s'adapter sur mobile (futur)
- Le changement de visibilité est toujours animé avec `darwinSpring(DarwinSpringPreset.Snappy)`
- Le bouton sidebar toggle bascule entre `All` ↔ `DoubleColumn` (pas `DetailOnly`)
- **Rétrocompatibilité** : `sidebarVisible: Boolean` et `onSidebarVisibleChange` restent fonctionnels mais sont marqués `@Deprecated` en faveur de `columnVisibility`

---

## 2. Sidebar redimensionnable

### 2.1 Paramètres de largeur

**API cible :**
```kotlin
DarwinScaffold(
    sidebarWidth: DarwinColumnWidth = DarwinColumnWidth.Fixed(240.dp),
    contentListWidth: DarwinColumnWidth = DarwinColumnWidth.Flexible(
        min = 200.dp, ideal = 250.dp, max = 400.dp
    ),
    // ...
)
```

```kotlin
sealed class DarwinColumnWidth {
    data class Fixed(val width: Dp) : DarwinColumnWidth()
    data class Flexible(
        val min: Dp,
        val ideal: Dp,
        val max: Dp
    ) : DarwinColumnWidth()
}
```

### 2.2 Divider draggable (séparateur redimensionnable)

**Comportement attendu du divider :**
- **Visuel** : ligne verticale de 1.dp, couleur `Black.copy(alpha = 0.10f)` (light) / `White.copy(alpha = 0.10f)` (dark)
- **Zone de hit** : 8.dp de large (invisible, centrée sur la ligne), curseur `ColResize` au survol
- **Drag** : `Modifier.draggable(orientation = Horizontal)` modifie la largeur de la colonne gauche
- **Contraintes** : la largeur est clampée entre `min` et `max` pendant le drag
- **Snap** : pas de snap magnétique, le drag est fluide
- **Double-clic** : remet la colonne à sa largeur `ideal`
- **Curseur** : change en `↔` (col-resize) au survol de la zone de hit
- **Hover visuel** : la ligne passe à 2.dp d'épaisseur et change de couleur vers `accent.copy(alpha = 0.5f)` au survol, avec `darwinTween(DarwinDuration.Fast)`

**Dividers présents :**
- Entre sidebar et contentList (ou entre sidebar et content si pas de contentList)
- Entre contentList et content (si contentList est présent)

---

## 3. Inspector (panneau latéral droit)

### 3.1 API cible

**Paramètres dans DarwinScaffold :**
```kotlin
DarwinScaffold(
    inspector: (@Composable () -> Unit)? = null,
    inspectorVisible: Boolean = false,
    onInspectorVisibleChange: ((Boolean) -> Unit)? = null,
    inspectorWidth: DarwinColumnWidth = DarwinColumnWidth.Flexible(
        min = 200.dp, ideal = 260.dp, max = 400.dp
    ),
    // ...
)
```

### 3.2 Comportement attendu

**Layout :**
```
┌──────────────────────────────────────────────────────────────┐
│  TitleBar (pleine largeur, y compris au-dessus de inspector) │
├──────────┬──────────────────────────────┬────────────────────┤
│ Sidebar  │ Content                      │ Inspector          │
│          │                              │ (panneau droit)    │
│          │                              │                    │
└──────────┴──────────────────────────────┴────────────────────┘
```

- L'inspector s'affiche à **droite** du content, séparé par un divider draggable
- Il est **indépendant** du système de colonnes sidebar/contentList : on peut avoir `sidebar | contentList | content | inspector` (4 colonnes)
- **Animation d'apparition** : slide-in depuis la droite avec `darwinSpring(DarwinSpringPreset.Snappy)` + `expandHorizontally(expandFrom = Alignment.End)`
- **Animation de disparition** : slide-out vers la droite avec `shrinkHorizontally(shrinkTowards = Alignment.End)`
- L'inspector a un **fond distinct** : même traitement que la sidebar (`surface` avec bordure gauche)
- Le title bar s'étend sur toute la largeur (y compris au-dessus de l'inspector)
- Le divider entre content et inspector est draggable (mêmes specs que §2.2)
- **Pas de glass blur** sur l'inspector (contenu opaque comme la sidebar)
- Le content se réduit quand l'inspector apparaît (pas d'overlay)

---

## 4. Système de Toolbar étendu

### 4.1 Placement des items — `ToolbarPlacement`

**API cible :**
```kotlin
enum class ToolbarPlacement {
    TopBarLeading,      // Gauche du title bar (= navigationActions actuel)
    TopBarTrailing,     // Droite du title bar (= actions actuel)
    Principal,          // Centre du title bar (= title actuel)
    BottomBar,          // Barre en bas de la fenêtre
    Automatic           // Placement par défaut (= TopBarTrailing)
}
```

> **Note** : Ce système n'est PAS implémenté comme un DSL de placement comme SwiftUI. On garde les slots nommés, mais on ajoute les slots manquants.

### 4.2 Bottom Bar (nouveau slot)

**Paramètre dans DarwinScaffold :**
```kotlin
DarwinScaffold(
    bottomBar: (@Composable () -> Unit)? = null,
    bottomBarHeight: Int = 38,
    // ...
)
```

**Comportement attendu :**
- Le bottom bar s'affiche en **bas** de la zone content (pas en bas de la fenêtre entière — il ne couvre pas la sidebar)
- **Hauteur** : 38.dp par défaut (plus compact que le title bar)
- **Fond** : glass blur identique au title bar (frost 16.dp, saturation 1.05f), positionné en overlay au-dessus du content
- **Bordure** : ligne de 0.5.dp en **haut** (au lieu d'en bas pour le title bar)
- Le `content` reçoit un `PaddingValues` avec `top` (title bar) ET `bottom` (bottom bar)
- Le bottom bar est typiquement utilisé pour : barre de statut, contrôles de lecture, informations contextuelles
- **Pas de bottom bar glass sur la sidebar** — seulement sur la zone content

**Exemple visuel :**
```
┌──────────────────────────────────────────┐
│  TitleBar (glass)                        │
├──────────┬───────────────────────────────┤
│ Sidebar  │ Content (scrollable)          │
│          │                               │
│          │                               │
│          ├───────────────────────────────┤
│          │ BottomBar (glass)             │
└──────────┴───────────────────────────────┘
```

### 4.3 Overflow menu pour les actions

**Comportement attendu :**
- Quand les actions du title bar dépassent l'espace disponible, les items en excès sont regroupés dans un menu `...` (ellipsis)
- Le bouton `...` utilise `TitleBarButtonGroup` avec l'icône `LucideEllipsis` (ou `MoreHorizontal`)
- Au clic : ouvre un `DropdownMenu` contenant les actions masquées
- Chaque action dans le menu affiche son label textuel + icône
- **Implémentation** : utiliser `onGloballyPositioned` + `SubcomposeLayout` pour mesurer les items et décider lesquels débordent
- **Seuil** : un item est caché si sa position dépasse `availableWidth - 48.dp` (espace réservé pour le bouton `...`)

> **Note** : Cette feature est complexe. Elle peut être implémentée dans un second temps. Pour la V1, on peut simplement documenter que le développeur doit gérer le nombre d'actions manuellement.

---

## 5. Styles de TitleBar — `TitleBarStyle`

### 5.1 API cible

```kotlin
enum class TitleBarStyle {
    Unified,         // Style actuel (52.dp, titre centré)
    UnifiedCompact,  // Version compacte (38.dp, texte plus petit)
    Expanded         // Version étendue (60.dp, plus d'espace)
}
```

**Paramètre dans TitleBar :**
```kotlin
@Composable
fun TitleBar(
    style: TitleBarStyle = TitleBarStyle.Unified,
    showsTitle: Boolean = true,   // Permet de masquer le titre (comme Apple)
    // ... existing params
)
```

### 5.2 Comportement détaillé par style

**`Unified` (défaut, comportement actuel) :**
- Hauteur : **52.dp**
- Padding horizontal : **12.dp**
- Zone navigationActions : `widthIn(min = 80.dp)`
- Zone actions : `widthIn(min = 80.dp)`
- Titre : centré, typographie `body` ou custom
- Espacement actions : `spacedBy(8.dp)`

**`UnifiedCompact` :**
- Hauteur : **38.dp** (au lieu de 52)
- Padding horizontal : **10.dp** (au lieu de 12)
- Zone navigationActions : `widthIn(min = 60.dp)` (réduite)
- Zone actions : `widthIn(min = 60.dp)` (réduite)
- Titre : centré, typographie `caption1` (plus petit)
- Espacement actions : `spacedBy(6.dp)`
- Les boutons `TitleBarButtonGroup` ont une hauteur réduite : **28.dp** (au lieu de 32)
- Icônes dans les boutons : **18.dp** (au lieu de 20-24)
- **`showsTitle = false`** : le titre est invisible mais l'espace est maintenu (pas de collapse du center)
- Cas d'usage : Finder en mode compact, préférences système

**`Expanded` :**
- Hauteur : **60.dp**
- Padding horizontal : **16.dp**
- Zone navigationActions : `widthIn(min = 100.dp)`
- Zone actions : `widthIn(min = 100.dp)`
- Titre : centré, typographie `headline` (plus grand)
- Espacement actions : `spacedBy(10.dp)`
- Les boutons `TitleBarButtonGroup` ont une hauteur augmentée : **36.dp**
- Cas d'usage : apps créatives, apps avec toolbar chargée

### 5.3 Intégration avec DarwinScaffold

```kotlin
DarwinScaffold(
    titleBarHeight: Int = 52,  // Existant, auto-calculé si non fourni
    // ...
)
```

- Quand `titleBar` est fourni et utilise un `TitleBarStyle`, le `titleBarHeight` du scaffold doit correspondre
- **Recommandation** : le scaffold devrait pouvoir détecter la hauteur automatiquement via un `CompositionLocal` plutôt que la passer en doublon
- **Nouveau CompositionLocal** : `LocalTitleBarHeight` fourni par `TitleBar`, lu par `DarwinScaffold` pour calculer le padding

---

## 6. Largeur de sidebar configurable et redimensionnable

### 6.1 Changements dans Sidebar

**API cible (Sidebar elle-même) :**
```kotlin
@Composable
fun Sidebar(
    // ... existing params
    width: Dp = 240.dp,              // NOUVEAU — remplace le 240.dp hardcodé
    collapsedWidth: Dp = 56.dp,      // NOUVEAU — remplace le 56.dp hardcodé
    // ...
)
```

**Comportement :**
- La largeur est désormais paramétrable (plus de constante magique)
- L'animation interpole entre `collapsedWidth` et `width`
- Le scaffold passe la largeur courante (après drag) à la sidebar

### 6.2 Changements dans DarwinScaffold

Le scaffold gère l'état de la largeur et passe la valeur à la Sidebar :

```kotlin
DarwinScaffold(
    sidebarWidth: Dp = 240.dp,
    sidebarMinWidth: Dp = 180.dp,
    sidebarMaxWidth: Dp = 360.dp,
    sidebarResizable: Boolean = true,
    // ...
)
```

**Comportement :**
- `sidebarResizable = true` : un divider draggable apparaît entre la sidebar et le content
- Le drag modifie `sidebarWidth` en temps réel (clampé entre min et max)
- Double-clic sur le divider : remet à la valeur par défaut (240.dp)
- La largeur est communiquée à la `Sidebar` composable via un `CompositionLocal` ou un paramètre direct

---

## 7. Titre de navigation — `navigationTitle`

### 7.1 Title display modes

```kotlin
enum class TitleDisplayMode {
    Inline,      // Titre dans le title bar (comportement actuel)
    Large,       // Grand titre qui collapse au scroll (style iOS)
    Automatic    // = Inline sur desktop, Large sur mobile (futur)
}
```

**Paramètre dans DarwinScaffold :**
```kotlin
DarwinScaffold(
    navigationTitle: String? = null,
    titleDisplayMode: TitleDisplayMode = TitleDisplayMode.Inline,
    // ...
)
```

### 7.2 Comportement `Inline` (défaut, desktop)

- Le titre s'affiche dans le slot `principal` du title bar
- Centré horizontalement
- Typographie : `subheadline` (comme macOS Finder)
- Couleur : `textSecondary` (grisé, pas noir)
- Pas d'animation spéciale

### 7.3 Comportement `Large` (iOS-style, optionnel)

- **Au repos** (scroll = 0) : grand titre sous le title bar
  - Zone dédiée de **52.dp** de haut sous le title bar
  - Typographie : `largeTitle` (34sp, bold)
  - Aligné à gauche, padding `start = 16.dp`
  - Fond : même que le content (pas de glass)
- **Au scroll** : le grand titre se réduit progressivement
  - La zone `largeTitle` collapse de 52.dp à 0.dp
  - Simultanément, le titre apparaît en `inline` dans le title bar (fade-in)
  - Seuil de transition : quand le scroll dépasse **44.dp**, le titre inline est fully visible
  - Spring animation : `darwinSpring(DarwinSpringPreset.Smooth)`
- **Snap behavior** : si le scroll est entre 0 et 44.dp au relâchement, snap vers 0 ou 44 (le plus proche)

> **Note** : Le mode `Large` est surtout pertinent pour iOS. Sur desktop, `Inline` est le standard Apple. Cette feature est **basse priorité**.

---

## 8. Toolbar background et color scheme

### 8.1 Background du toolbar

**Paramètre dans TitleBar :**
```kotlin
@Composable
fun TitleBar(
    backgroundStyle: TitleBarBackground = TitleBarBackground.Automatic,
    // ... existing params
)
```

```kotlin
sealed class TitleBarBackground {
    object Automatic : TitleBarBackground()   // Glass dans scaffold, opaque sinon
    object Visible : TitleBarBackground()     // Toujours opaque
    object Hidden : TitleBarBackground()      // Toujours transparent (pas de fond, pas de glass)
    data class Material(
        val frost: Dp = 16.dp,
        val saturation: Float = 1.05f,
        val tint: Color? = null
    ) : TitleBarBackground()                  // Glass personnalisé
}
```

**Comportement :**
- `Automatic` : comportement actuel (glass dans scaffold, opaque sinon)
- `Visible` : fond opaque `backgroundColor`, même dans un scaffold (désactive le glass)
- `Hidden` : aucun fond, aucune bordure, le contenu scrolle sans obstruction. Le title bar reste interactif (les boutons fonctionnent) mais est visuellement invisible
- `Material` : glass avec paramètres personnalisés (frost, saturation, tint)

### 8.2 Color scheme forcé

**Paramètre dans TitleBar :**
```kotlin
@Composable
fun TitleBar(
    forcedColorScheme: DarwinColorScheme? = null,  // null = hérite du thème
    // ...
)
```

**Comportement :**
- Quand non-null, le title bar et TOUS ses enfants utilisent le color scheme forcé
- Implémenté via `CompositionLocalProvider(LocalDarwinColors provides forcedColorScheme)`
- Cas d'usage : title bar sombre sur un contenu clair (comme Safari en navigation privée)
- Affecte : couleurs des boutons, texte, bordures, icônes — tout ce qui lit `DarwinTheme.colorScheme`

---

## 9. Hidden title bar / Window style

### 9.1 Comportement attendu

**Pas besoin d'une nouvelle API** — le comportement est déjà possible :

```kotlin
DarwinScaffold(
    titleBar = null,  // Pas de title bar
    titleBarHeight = 0,
    content = { ... }
)
```

**Ce qui manque** : documenter ce pattern et s'assurer que :
- Le content reçoit `PaddingValues(top = 0.dp)` quand `titleBar = null`
- Le glass blur ne crée pas d'artefacts quand il n'y a pas de title bar
- Le bouton sidebar toggle se positionne correctement (dans la sidebar elle-même, pas dans le title bar)

---

## 10. Sections collapsibles dans la Sidebar — `DisclosureGroup`

### 10.1 API cible

Évolution du modèle `SidebarItem` :

```kotlin
class SidebarItem(
    val label: String,
    val onClick: () -> Unit,
    val icon: ImageVector? = null,
    val group: String? = null,
    val id: String = label,
    val children: List<SidebarItem> = emptyList(),  // NOUVEAU
)
```

### 10.2 Comportement attendu

- Quand `children` est non-vide, l'item affiche un **chevron de disclosure** (▶) à droite
- Au clic sur le chevron (ou sur l'item si pas de `onClick`) : les enfants s'expand/collapse
- **Animation** : les enfants apparaissent avec `expandVertically` + `fadeIn` utilisant `darwinSpring(DarwinSpringPreset.Snappy)`
- Le chevron **tourne** de 0° à 90° avec `animateFloatAsState`
- Les enfants sont **indentés** de `16.dp` supplémentaires par rapport au parent
- Le chevron est visible uniquement quand la sidebar est expanded (disparaît quand collapsed)
- L'état expanded/collapsed de chaque section est **interne** par défaut (géré par `remember`)
- **Option** : permettre de contrôler l'état depuis l'extérieur via un callback

**Différence avec `group`** :
- `group` = header textuel non-interactif qui regroupe visuellement des items
- `children` = section collapsible interactive avec indentation

**Exemple visuel :**
```
📁 Favoris              ▼     (expanded)
    📄 Inbox
    📄 Drafts
    📄 Sent
📁 Smart Mailboxes      ▶     (collapsed)
── Locations ──                (group header, non-collapsible)
    💻 iCloud
    💻 Gmail
```

---

## 11. Résumé des fichiers à modifier

| Fichier | Changements |
|---------|-------------|
| `DarwinScaffold.kt` | Ajouter `contentList`, `inspector`, `bottomBar`, `columnVisibility`, sidebar resizable, dividers draggables |
| `TitleBar.kt` | Ajouter `TitleBarStyle`, `TitleBarBackground`, `forcedColorScheme`, `showsTitle` |
| `Sidebar.kt` | Paramétrer `width`/`collapsedWidth`, supporter `children` (disclosure) |
| `DarwinGlass.kt` | Ajouter `LocalTitleBarHeight` CompositionLocal |
| **Nouveau** `ColumnDivider.kt` | Composant de séparateur vertical draggable réutilisable |
| **Nouveau** `ColumnVisibility.kt` | Enum + logique de visibilité |
| **Nouveau** `DarwinColumnWidth.kt` | Sealed class pour les largeurs flexibles |
| **Nouveau** `TitleBarStyle.kt` | Enum + dimensions par style |
| `ScaffoldPage.kt` (sample) | Exemples pour chaque nouveau feature |

---

## 12. Ordre d'implémentation recommandé

1. **`TitleBarStyle`** (Unified/Compact/Expanded) — impact faible, très visible
2. **Sidebar redimensionnable** + `ColumnDivider` — fondation pour tout le reste
3. **`ColumnVisibility`** — remplace `sidebarVisible` proprement
4. **Layout 3 colonnes** (`contentList`) — utilise le divider + visibility
5. **Inspector** — utilise le divider, mirror de la sidebar côté droit
6. **Bottom bar** — slot simple, glass blur existant
7. **`TitleBarBackground`** + `forcedColorScheme` — customization fine
8. **`DisclosureGroup`** dans Sidebar — enrichissement du modèle
9. **Large title** (optionnel, basse priorité)
10. **Overflow menu** (optionnel, complexe)

## 13. Vérification

- Lancer `./gradlew :sample:run` (desktop) pour tester visuellement chaque feature
- Ajouter une page sample dédiée pour le layout 3 colonnes + inspector
- Vérifier que le glass blur fonctionne correctement avec les nouveaux slots
- Tester le drag des dividers avec des contenus scrollables (pas de conflit de gestes)
- `./gradlew detekt` pour valider les règles Compose
