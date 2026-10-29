# Changelog

All notable changes to the **Icefox** web browser will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [1.0.1] - 2026-10-29

### 🎨 Icon Polish & Universal Asset Refresh
- **Generous Safe-Zone Homescreen Adaptive Icon**:
  - Scaled adaptive icon foreground artwork (`ic_launcher_foreground.png`) and Android 12+ `<monochrome>` layer (`ic_launcher_monochrome.png`) to **50%** canvas scale, providing ~4.2dp of breathing clearance around the fox ear tips and tail, eliminating edge clipping on all circular, squircle, and OEM launcher masks.
- **Normal & Standalone Launcher Icon Margins**:
  - Scaled normal standalone launcher icons inside their circular badges to ~55% diameter (11px margin on 48px base) across all densities (`mdpi`, `hdpi`, `xhdpi`, `xxhdpi`, `xxxhdpi`) ensuring generous breathing room and zero edge cutting.
- **Icefox Splash Screen Icon**:
  - Updated `ic_splash_logo` in `forkRelease` and `forkDebug` to display the clean floating Icefox brand mark centered at a compact `72dp × 72dp` on the splash screen background, ensuring the fox ears and contours remain completely unclipped by Android's circular splash mask.
- **Universal Brand Mark Refresh**:
  - Updated `ic_wordmark_logo` in `res/drawable-nodpi/` for both `forkRelease` and `forkDebug` with high-resolution Icefox artwork for the homepage header, search widget, and authentication screens.
  - Updated store and web icons (`ic_launcher-web.png`, `ic_launcher-playstore.png`) with safe margins.
  - Updated README.md header logo to use the high-resolution master brand asset (`fork-asset-sources/app_icon.png`).
- **Launcher Bloat Reduction & Manifest Cleanliness**:
  - Excised all obsolete alternative app icon `<activity-alias>` entries (20+ aliases) from `AndroidManifest.xml` to prevent launcher bloat and unnecessary manifest size.
  - Excised the "App icon" / "Select App Icon" preference category from `customization_preferences.xml`.
- **Dynamic Themed Icon & Theme Matching Background**:
  - Added full Android 12+ Material You Themed Icon (`ic_launcher_monochrome.png`) support for homescreen icon tinting.
  - Configured adaptive icon background to match theme luminance (`#FFFFFF` in light mode, `#1C1B1F` in dark mode).
- **Developer Attribution**:
  - Updated developer profile link in the About screen (`AboutFragment.kt`) to point directly to developer Mina Android's portfolio (`https://portfolio.minaashraf285.workers.dev/`).
- **Version Bump**:
  - Bumped application `versionName` to `1.0.1` and `versionCode` to `2` in `app/build.gradle`.

---

## [1.0.0] - 2026-10-27

### Initial Release: Icefox Zero-Account Edition (Forked from Iceraven Browser)

This initial release establishes **Icefox**, an offline-first, unencumbered fork of Iceraven Browser built on Mozilla GeckoView `158.0.2` and Android Components.

#### 🛡️ Zero-Account & Zero-Sync Architecture
- **Mozilla Account (FxA) Excision**:
  - Completely excised `FxaAccountManager`, Mozilla Account login, signup, authentication flows, and QR constellation / device pairing.
  - Stripped "Sync and save data", "Send tab to device", and account preferences from main menus, navigation graphs, and share sheets.
- **Sync Engines Disconnected**:
  - Permanently unbound all Sync engines (`SyncEngine.History`, `SyncEngine.Bookmarks`, `SyncEngine.Passwords`, `SyncEngine.Tabs`, `SyncEngine.CreditCards`, `SyncEngine.Addresses`) from `GlobalSyncableStoreProvider`.
- **Tabs Tray Streamlining**:
  - Fixed the Tabs Tray to a clean 2-page or 3-page layout (Normal Tabs, Private Tabs, and optional Tab Groups).
  - Permanently removed the `SyncedTabs` page, cloud tab sync indicators, and empty-state sync cards.

#### 🔒 Zero-Password & Zero-Autofill Storage
- **GeckoView Autocomplete Storage Nullified**:
  - Hardened `GeckoRuntime.autocompleteStorageDelegate` to strictly remain `null`.
  - Suppressed all engine-level prompts to save credentials, website logins, credit cards, and postal addresses.
- **Password Manager Excised**:
  - Disabled `feature-logins`, saved logins preferences, and exception lists. Credential management is delegated to external OS-level autofill providers (e.g. Bitwarden, KeePassDX).

#### 📑 Zero-Bookmarks & Zero-AI Hygiene
- **Bookmarks Purged**:
  - Permanently removed bookmark creation buttons, bookmark star toolbar icons, bookmark folders, import/export tools, and settings.
  - Stripped `bookmarksStorage.warmUp()` from application boot.
  - Excised bookmarks shortcut (`BOOKMARKS_SEARCH_ENGINE_ID`) from the homepage search shortcuts sheet.
- **AI Controls & Summarization Removed**:
  - Permanently disabled and stripped AI page summaries ("shake to summarize"), AI preference toggles, and generative telemetry.
  - Preserved standard offline page translation.

#### 🎨 Interface Polish & Branding
- **Icefox Identity & Versioning**:
  - Rebranded application name to **Icefox** across all system manifests, shortcuts, strings, and settings.
  - Updated application ID to `com.ma.icefox` and URL schemes to `icefox://` and `icefox-debug://`.
  - Set application version to `1.0.0` across default configurations and split architecture APK outputs.
- **Dynamic Wordmark Typography**:
  - Replaced legacy bitmap wordmarks in `WordmarkAndLogo.kt` with dynamic Jetpack Compose Material 3 typography (`headlineMedium`), adapting crisply to wallpaper palette and theme luminance.
- **About Dev Section**:
  - Added dedicated developer attribution items to the top of the About screen:
    - **Mina Android** (`https://portfolio.minaashraf285.workers.dev/`)
    - **Source code** (`https://github.com/mina-android/Icefox`)
    - **Other Projects by Mina Android** (`https://github.com/mina-android`)
- **App Icon & Dynamic Theming**:
  - Replaced legacy launcher icons with a custom Icefox adaptive icon (`app_icon.png`), optimized with calculated safe-zone margins (60% canvas ratio) to ensure zero clipping on circular launcher masks while eliminating excessive blank space.
  - Implemented Android 12+ homescreen Material You Themed Icon (`themed_icon.png`) via `<monochrome>` layer for dynamic wallpaper and system palette tinting.
  - Configured adaptive launcher background to match the system theme luminance (`#FFFFFF` in light mode, `#1C1B1F` in dark mode).
  - Removed "App icon" / "Select App Icon" preference category from Customize Settings.
  - Excised 20+ obsolete alternative icon `<activity-alias>` entries from `AndroidManifest.xml` to prevent launcher bloat.
- **Nag Banner & Shortcut Removal**:
  - Removed "Set as default browser" prompt cards from the 3-dots menu (`MenuDialogFragment.kt`).
  - Excised default browser onboarding prompts from first-run onboarding (`OnboardingFragment.kt`) and hidden settings toggles.
  - Removed homepage news / Pocket stories entry button (`showStoriesButton = false`) and no-op'd "Discover More" screen navigation.
  - Excised the voice typing / search microphone button from both the search toolbar and display toolbar (`shouldShowVoiceSearch = false`).

#### 🧹 Cold-Start Zero-Trace Hygiene
- **`AccountAndLoginPurge`**:
  - Introduced startup hygiene task running during cold application boot to actively detect and shred obsolete SQLite database files: `logins.db`, `autofill.db`, `remote_tabs.db`, `pocket.db`, and `bookmarks.db`.
  - Purged legacy FxA sync tokens and obsolete SharedPreferences keys.

#### 🔧 Repository & Build Configuration
- Added all AI instruction documents (`AGENTS.md`, `AI_INSTRUCTIONS.md`, `CLAUDE.md`, `GEMINI.md`, implementation plans) and `.gitignore` itself to `.gitignore`, keeping internal assistant files local and untracked.
