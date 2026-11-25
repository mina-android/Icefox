<div align="center">

<img src="fork-asset-sources/app_icon.png" alt="Icefox Logo" width="112" height="112" />

# Icefox

[![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?style=flat&logo=android&logoColor=white)](https://developer.android.com) [![Framework](https://img.shields.io/badge/Framework-Kotlin%20%2F%20GeckoView-7F52FF?style=flat&logo=kotlin&logoColor=white)](https://kotlinlang.org) [![Release](https://img.shields.io/badge/Release-v1.0.1-blue?style=flat)](https://github.com/mina-android/Icefox/releases) [![License](https://img.shields.io/badge/License-MPL--2.0-black?style=flat)](LICENSE)

<p align="center">
  Icefox is an offline-first Android web browser forked from Iceraven Browser (Mozilla Fenix and GeckoView). All account systems, cloud synchronization, internal password and autofill storage, bookmarks, generative AI controls, and auxiliary cloud services are permanently excised.
</p>

</div>

---

## Navigation

[Features](#features) &bull; [Screenshots](#screenshots) &bull; [Downloads](#downloads) &bull; [Self Building & Local Setup](#self-building--local-setup) &bull; [Contribution](#contribution) &bull; [About the Developer](#about-the-developer) &bull; [Donate](#donate) &bull; [License](#license)

---

## Features

### Core Capabilities

- **Zero-Account and Zero-Sync Runtime**: `FxaAccountManager`, Mozilla Account (FxA) authentication flows, QR device pairing, and all Firefox Sync engines (`SyncEngine.History`, `SyncEngine.Bookmarks`, `SyncEngine.Passwords`, `SyncEngine.Tabs`, `SyncEngine.CreditCards`, `SyncEngine.Addresses`) are unbound from `GlobalSyncableStoreProvider`. The Tabs Tray maintains a 2-page layout (Normal Tabs and Private Tabs) with `SyncedTabs` removed.
- **Zero-Password and Zero-Autofill Storage**: `GeckoRuntime.autocompleteStorageDelegate` is hardcoded to `null`, and `feature-logins`, saved logins preferences, credit card storage, and postal address autofill are disabled. Credential autofill is left to external OS-level providers such as Bitwarden or KeePassDX.
- **Zero-Bookmarks Interface**: Bookmark creation, editing, importing/exporting, search shortcuts (`BOOKMARKS_SEARCH_ENGINE_ID`), and `bookmarksStorage.warmUp()` are removed from toolbars, menus, search suggestions, and startup paths.
- **Zero-AI and Reduced Surface Clutter**: Generative AI page summarization ("shake to summarize"), AI feature controls, Pocket news feeds, Firefox Relay, Mozilla VPN / IP Protection wrappers, voice typing buttons, default-browser nag cards, and alternative app icon aliases are removed. Standard offline page translation and GeckoView add-on support (via custom AMO collections) remain intact.

### Architecture & Data Handling

- **Data Ownership and Privacy**: All browsing state remains localized to the Android application sandbox (`/data/data/com.ma.icefox/`). On cold startup, `AccountAndLoginPurge.purgeLegacyData(...)` deletes any lingering SQLite databases (`logins.db`, `autofill.db`, `remote_tabs.db`, `pocket.db`, `bookmarks.db`), token files, and obsolete SharedPreferences.
- **Offline Reliability**: The browser operates without requiring any Mozilla account or cloud endpoint, using local GeckoView profile storage and Mozilla's unidirectional Redux state store (`mozilla.components.lib.state.Store`) for tab and session state.
- **Engine and UI Stack**: Built on Mozilla GeckoView (`158.0.2`, anchored in `version.txt`) with Jetpack Compose Material 3, AndroidX Fragments, and Navigation components, including AMOLED dark theme support and Material You monochrome launcher icons.

---

## Screenshots

Screenshots are not yet included in this repository. UI previews for the homepage, tabs tray, add-ons manager, and settings screens will be added once captured from the `v1.0.1` release build.

---

## Downloads

Pre-built standalone APKs for **v1.0.1** are published under [GitHub Releases](https://github.com/mina-android/Icefox/releases).

| Platform | Architecture | Package Type | Download Link | Checksum (SHA-256) |
| :--- | :--- | :--- | :--- | :--- |
| **Android** | arm64-v8a | Package `.apk` | [Download (v1.0.1)](https://github.com/mina-android/Icefox/releases/download/v1.0.1/Icefox-v1.0.1-arm64-v8a.apk) | Verify on Releases page |
| **Android** | armeabi-v7a | Package `.apk` | [Download (v1.0.1)](https://github.com/mina-android/Icefox/releases/download/v1.0.1/Icefox-v1.0.1-armeabi-v7a.apk) | Verify on Releases page |
| **Android** | x86_64 | Package `.apk` | [Download (v1.0.1)](https://github.com/mina-android/Icefox/releases/download/v1.0.1/Icefox-v1.0.1-x86_64.apk) | Verify on Releases page |

---

## Self Building & Local Setup

Follow this procedure to compile the Icefox APK from source.

### 1. Prerequisites

Verify that the following toolchains are installed on your host machine:

- **Java JDK**: Version `17` or `21` (LTS)
- **Android SDK**: Platforms `35` / `36` and corresponding Build-Tools
- **Python**: Version `3.12` or higher (used by the Glean metric bindings generator)
- **Git**: `2.30+`

### 2. Repository Setup

```bash
# Clone the repository with submodules
git clone --recursive https://github.com/mina-android/Icefox.git

# Navigate into the project root
cd Icefox

# Optionally check out a release tag
git checkout tags/v1.0.1 -b release-build
```

### 3. Dependency Installation

Configure `ANDROID_HOME` and verify Gradle wrapper resolution (Gradle downloads Maven and GeckoView `158.0.2` dependencies automatically on first run):

```powershell
# PowerShell example on Windows
$env:ANDROID_HOME = "$env:LOCALAPPDATA\Android\Sdk"
.\gradlew --version
```

### 4. Build Compilation

```powershell
# Compile release APKs split by ABI
.\gradlew app:assembleForkRelease
```

The output APKs are written to `gradle/build/app/outputs/apk/forkRelease/`.

### 5. Running the Application Locally

```powershell
# Build and install the debug APK onto a connected device or emulator via ADB
.\gradlew app:installForkDebug
```

---

## Contribution

Contributions that maintain Icefox's zero-account, zero-sync, zero-bookmarks, zero-password-storage, and zero-AI invariants are welcome. Do not modify `version.txt` away from `158.0.2`, as it pins the GeckoView binary dependency.

### Contribution Process

1. **Check Issues**: Review existing GitHub Issues to ensure the topic or bug is not already tracked.
2. **Open a Discussion or Bug Report**: For architectural changes, open an issue first to confirm alignment with project invariants.
3. **Branching**: Create a branch from `main` using descriptive names (`fix/issue-description` or `feat/feature-name`).
4. **Code Quality Standards**:
   - Run static checks and linting: `.\gradlew detekt ktlintCheck`
   - Execute unit tests: `.\gradlew app:testForkDebugUnitTest`
5. **Submitting**: Open a Pull Request targeting `main`, summarizing the change and referencing any related issue numbers. Note that local documentation files (`CHANGELOG.md`, `STRUCTURE.md`) should be updated alongside code changes.

---

## About the Developer

This project is authored and maintained by **mina-android**, focused on developing transparent, local-first utilities and privacy-conscious software.

[![Portfolio](https://img.shields.io/badge/Portfolio-mina--android-181717?style=flat&logo=cloudflare&logoColor=white)](https://portfolio.minaashraf285.workers.dev/) [![GitHub](https://img.shields.io/badge/GitHub-mina--android-181717?style=flat&logo=github&logoColor=white)](https://github.com/mina-android) [![Issues](https://img.shields.io/badge/Issues-Report%20a%20Bug-181717?style=flat&logo=github&logoColor=white)](https://github.com/mina-android/Icefox/issues)

---

## Donate

Development and maintenance of this software are independent and self-supported. If this project helps you in your workflow, consider supporting development:

[![PayPal](https://img.shields.io/badge/PayPal-Donate-00457C?style=flat&logo=paypal&logoColor=white)](https://paypal.me/minaashraf285) [![GitHub Sponsors](https://img.shields.io/badge/GitHub%20Sponsors-Sponsor-EA4AAA?style=flat&logo=githubsponsors&logoColor=white)](https://github.com/sponsors/mina-android) [![Ko-fi](https://img.shields.io/badge/Ko--fi-Support-FF5E5B?style=flat&logo=kofi&logoColor=white)](https://ko-fi.com/minaashraf285)

---

## License

Distributed under the **Mozilla Public License 2.0 (MPL-2.0)**. See the [`LICENSE`](LICENSE) file in the root directory for the complete legal text.

```text
Copyright (c) 2026 mina-android and upstream Mozilla / Iceraven contributors
Licensed under the Mozilla Public License 2.0 (MPL-2.0).
```
