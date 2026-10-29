<div align="center">

<img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" alt="Icefox Logo" width="120" height="120" style="border-radius: 24px"/>

<h1>Icefox</h1>

<h3>A privacy-first Android web browser with zero accounts, zero sync, zero bookmarks, and zero AI</h3>

[![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white)](https://android.com) [![Version](https://img.shields.io/badge/Version-1.0.1-brightgreen)](https://github.com/mina-android/Icefox/releases) [![License](https://img.shields.io/badge/License-MPL--2.0-blue)](LICENSE) [![Fork of](https://img.shields.io/badge/Fork%20of-Iceraven%20Browser-orange)](https://github.com/fork-maintainers/iceraven-browser)

Icefox is an unencumbered, privacy-centric web browser for Android, forked from [**Iceraven Browser**](https://github.com/fork-maintainers/iceraven-browser) (which builds on Mozilla's Fenix and GeckoView engine).  
All account systems, cloud synchronization, internal password managers, form autofill, bookmarks, AI controls/summaries, and auxiliary cloud services have been permanently excised.

[**Download**](#-download) · [**Key Features**](#-key-features) · [**Architecture**](#-architecture) · [**Building**](#-building) · [**For Developers**](#-for-developers)

</div>

---

## ✨ Key Features

- **🛡️ Zero-Account & Zero-Sync Architecture**: Completely offline, standalone browser. All Mozilla Account (FxA) logins, device pairing, and Firefox Sync engines (`SyncEngine.History`, `SyncEngine.Bookmarks`, `SyncEngine.Passwords`, `SyncEngine.Tabs`, `SyncEngine.CreditCards`, `SyncEngine.Addresses`) are permanently excised.
- **🔒 Zero-Password & Zero-Autofill Storage**: GeckoView's autocomplete storage delegate is strictly `null`. The browser will never prompt to remember passwords, credit cards, or addresses, keeping credential security delegated to external OS-level autofill providers (e.g. Bitwarden, KeePassDX).
- **📑 Zero-Bookmarks & Clean Tabs Tray**: Menus, toolbars, search suggestions, and settings contain no bookmark traces. The Tabs Tray strictly maintains a clean 2-page layout (Private Tabs and Normal Tabs) with the `SyncedTabs` page permanently removed.
- **🧠 Zero-AI & Zero-Telemetry Focus**: Generative AI summaries ("shake to summarize"), AI feature blocks, and telemetry reporting are disabled and purged. Standard offline page translation remains intact.
- **🧩 Full Extension / Add-on Library**: Built on GeckoView with full support for desktop and mobile add-ons via custom Mozilla AMO collections.
- **🎨 Elevated Material 3 Typography & Theming**: Dynamic bold wordmark typography in Jetpack Compose, theme-adaptive headers, and full AMOLED pure-black display support.
- **🧼 Nag-Free Interface**: Excised the default browser prompt cards from the 3-dots menu, hidden default browser onboarding, suppressed homepage news/Pocket stories, removed the bookmarks search shortcut, and removed voice typing microphone buttons.
- **🧹 Cold-Start Zero-Trace Hygiene (`AccountAndLoginPurge`)**: Automatically purges and shreds obsolete SQLite database files (`logins.db`, `autofill.db`, `remote_tabs.db`, `pocket.db`, `bookmarks.db`), authentication tokens, and legacy SharedPreferences on startup.

---

## 📥 Download

Pre-built standalone APKs for **Version 1.0.1** are available directly on the [**GitHub Releases**](https://github.com/mina-android/Icefox/releases) page:

| Architecture | Device Type | APK File |
|---|---|---|
| **ARM64 (v8a)** | Modern 64-bit phones & tablets (Recommended) | [`Icefox-v1.0.1-arm64-v8a.apk`](https://github.com/mina-android/Icefox/releases/download/v1.0.1/Icefox-v1.0.1-arm64-v8a.apk) |
| **ARMv7 (v7a)** | Older 32-bit Android phones | [`Icefox-v1.0.1-armeabi-v7a.apk`](https://github.com/mina-android/Icefox/releases/download/v1.0.1/Icefox-v1.0.1-armeabi-v7a.apk) |
| **x86_64** | Android emulators & Android-x86 PCs | [`Icefox-v1.0.1-x86_64.apk`](https://github.com/mina-android/Icefox/releases/download/v1.0.1/Icefox-v1.0.1-x86_64.apk) |

---

## 🧭 Getting Started

1. **Download & Install**: Grab the APK matching your device's CPU architecture (usually `arm64-v8a`).
2. **Install Add-ons**: Open the main menu (3 dots) -> **Add-ons** to install uBlock Origin, Dark Reader, or configure custom AMO collections.
3. **Customize Homepage**: In Settings -> **Homepage**, configure your top sites, wallpaper, and layout.
4. **Search Shortcuts**: Tap the search bar to search using your favorite engine with quick navigation across open tabs and history.

---

## 🏛 Architecture

Icefox combines modern Android architecture components with Mozilla's GeckoView browser engine:

- **Browser Engine**: Mozilla GeckoView (`geckoview-omni:158.0`).
- **UI Architecture**: Jetpack Compose Material 3 paired with AndroidX Fragments and Navigation component.
- **State Management**: Unidirectional Redux state store (`mozilla.components.lib.state.Store`) with middleware dispatching.
- **Privacy Enforcement**: Hardened delegation in `GeckoProvider`, null storage delegates, and startup shredding in `AccountAndLoginPurge`.

For the detailed architectural blueprint, class diagrams, and data flow pipelines, see **[`STRUCTURE.md`](STRUCTURE.md)**.

---

## 🔨 Building

### Prerequisites
- Android SDK with Platform 35 / 36 and Build-Tools installed.
- Java JDK 17 or 21 (LTS).
- Python 3.12+ (for Glean metric bindings generator).

### Build Commands

```powershell
# Set Android SDK location
$env:ANDROID_HOME = "C:\Users\<user>\AppData\Local\Android\Sdk"

# Assemble forkRelease APKs (split architectures)
.\gradlew app:assembleForkRelease
```

Generated APKs are located at:
`gradle/build/app/outputs/apk/forkRelease/`

---

## 👨‍💻 For Developers

- **Developer**: [Mina Android](https://portfolio.minaashraf285.workers.dev/)
- **Source Code**: [https://github.com/mina-android/Icefox](https://github.com/mina-android/Icefox)
- **Upstream Origin**: Forked from [Iceraven Browser](https://github.com/fork-maintainers/iceraven-browser) (and Mozilla Fenix / GeckoView).
- **Contributing & Rules**: Contributors and AI coding assistants must review **[`AGENTS.md`](AGENTS.md)** and **[`AI_INSTRUCTIONS.md`](AI_INSTRUCTIONS.md)** for project-specific rules and mandatory documentation maintenance after every task.

---

## 📄 License

Icefox is licensed under the [Mozilla Public License 2.0 (MPL-2.0)](LICENSE).
All upstream copyrights belong to their respective authors and contributors.
