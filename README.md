<div align="center">

<img src="fork-asset-sources/app_icon.png" alt="Icefox Logo" width="112" height="112" />

# Icefox

[![Platform](https://img.shields.io/badge/Platform-Android-lightgrey.svg?style=flat-square)](https://developer.android.com) [![Latest Release](https://img.shields.io/badge/Release-v1.0.1-blue.svg?style=flat-square)](https://github.com/mina-android/Icefox/releases) [![License](https://img.shields.io/badge/License-MPL--2.0-black.svg?style=flat-square)](https://www.mozilla.org/en-US/MPL/2.0/) [![Fork of](https://img.shields.io/badge/Fork%20of-Iceraven%20Browser-lightgrey.svg?style=flat-square)](https://github.com/fork-maintainers/iceraven-browser)

<p align="center">
  Icefox is an offline-first Android web browser forked from Iceraven Browser (Mozilla Fenix and GeckoView). All account systems, cloud synchronization, internal password and autofill storage, bookmarks, generative AI controls, and auxiliary cloud services are permanently excised.
</p>

</div>

---

## Navigation

[Features](#features) &bull; [Screenshots](#screenshots) &bull; [Downloads](#downloads) &bull; [Self Building & Local Setup](#self-building--local-setup) &bull; [Frequently Asked Questions](#frequently-asked-questions) &bull; [Contribution](#contribution) &bull; [About the Developer](#about-the-developer) &bull; [Donate](#donate) &bull; [License](#license)

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

## Frequently Asked Questions

#### Where does the application store its settings and database?
On Android, all profile data, GeckoView state, local caches, and preferences are stored inside the application's private sandbox directory (`/data/data/com.ma.icefox/`). No data is synchronized to external servers.

#### Does this application collect telemetry or connect to external servers?
Mozilla Account, Firefox Sync, Pocket, Relay, and AI telemetry/summarization pipelines are disabled and purged. Outbound requests occur only when navigating to user-requested websites, fetching add-ons/updates from configured AMO collections, or querying the user-selected search engine.

#### Can I run multiple instances concurrently?
Android enforces a single process per application ID (`com.ma.icefox`). Debug builds use a separate `.debug` suffix (`icefox-debug://` deep links) and can be installed alongside the release build.

#### How do I perform a complete data reset?
In Android System Settings, open **Apps > Icefox > Storage & cache** and select **Clear storage**, or uninstall the application. On every cold start, `AccountAndLoginPurge` also checks for and removes legacy database files if any remain.

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

This project is maintained by **mina-android**, an independent software developer focused on privacy-hardened Android applications and stripped-down open-source forks.

- **Public Code Repository**: [github.com/mina-android](https://github.com/mina-android)
- **Project Source**: [github.com/mina-android/Icefox](https://github.com/mina-android/Icefox)
- **Issue Tracker**: [github.com/mina-android/Icefox/issues](https://github.com/mina-android/Icefox/issues)

---

## Donate

No external donation or sponsorship platforms are currently configured for this repository.

---

## License

Distributed under the **Mozilla Public License 2.0 (MPL-2.0)** License. See [mozilla.org/en-US/MPL/2.0](https://www.mozilla.org/en-US/MPL/2.0/) for full legal text and conditions.

```text
Copyright (c) 2026 mina-android and upstream Mozilla / Iceraven contributors
Licensed under the MPL-2.0 License.
```
