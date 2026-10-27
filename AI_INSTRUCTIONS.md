# AI Instructions for Icefox

This document contains critical project context, architectural guidelines, and specific rules that AI coding assistants must follow when working on the Icefox codebase.

> For the comprehensive architectural reference — see **`STRUCTURE.md`** and **`CLAUDE.md`**. This file is the condensed, rules-first companion.

---

## 1. Project Context & Toolchain Stack

- **Application Name**: Icefox
- **Application ID**: `com.ma.icefox`
- **Application Version**: `1.0.0` (set in `app/build.gradle`)
- **Upstream Engine**: Mozilla GeckoView (`geckoview-omni:158.0.2` anchored in `version.txt`)
- **Upstream Fork**: Iceraven Browser (`https://github.com/fork-maintainers/iceraven-browser`)
- **Developer**: Mina Android (`https://github.com/mina-android`)
- **Repository**: `https://github.com/mina-android/Icefox`
- **Toolchain Stack**:
  - JDK: Java 17 / 21 LTS
  - Android SDK: Platform 35 / 36, Build-Tools 35.0.0+
  - Python: 3.12+ (required for Glean metric code generators)
  - Gradle: 9.7.1 with AGP 9.4.0

---

## 2. Build Commands

```powershell
# Set Android SDK path
$env:ANDROID_HOME = "C:\Users\minaa\AppData\Local\Android\Sdk"

# Set Python 3.12 environment
$env:Path = "C:\Projects\IceRaven\venv\Scripts;C:\Users\minaa\AppData\Local\Programs\Python\Python312;C:\Users\minaa\AppData\Local\Programs\Python\Python312\Scripts;" + $env:Path

# Build release split APKs
.\gradlew app:assembleForkRelease

# Build debug APK
.\gradlew app:assembleForkDebug
```

---

## 3. Strict Invariant Rules

### 3.1 Mandatory Documentation Rule
- **Every Task Execution**: After modifying any code, **every relevant `.md` file (`README.md`, `STRUCTURE.md`, `CHANGELOG.md`, `AGENTS.md`, `AI_INSTRUCTIONS.md`, `CLAUDE.md`) must be kept synchronized and updated**.

### 3.2 GeckoView Version Invariant
- **DO NOT MODIFY `version.txt`**: Changing `version.txt` from `158.0.2` breaks Gradle dependency resolution against Mozilla's Maven repository for `geckoview-omni`. Application version `1.0.0` is configured in `app/build.gradle`.

### 3.3 Zero-Account & Zero-Sync Invariants
- `FxaAccountManager` must never be instantiated or bound.
- Sync engines (`History`, `Bookmarks`, `Passwords`, `Tabs`, `CreditCards`, `Addresses`) must remain unbound.
- `TabsTray` must remain strictly 2-page or 3-page (Normal Tabs, Private Tabs, and optional Tab Groups). `SyncedTabs` is forbidden.

### 3.4 Zero-Autofill & Zero-Password Invariants
- `GeckoRuntime.autocompleteStorageDelegate` must strictly remain `null`.
- Never prompt users to save credentials, credit cards, or addresses in browser engine storage.

### 3.5 Zero-Bookmarks Invariants
- Bookmarks UI, menus, warmup queries, and SQLite databases (`bookmarks.db`) are completely excised.
- `BOOKMARKS_SEARCH_ENGINE_ID` must never be added to application search engine shortcuts.

### 3.6 Zero-AI & Pure Web Invariants
- Generative AI summaries, shake-to-summarize triggers, and AI telemetry are permanently disabled.
- Standard offline translation remains functional.

### 3.7 Nag-Free UI Invariants
- No "Set as default browser" prompts in 3-dots menus, onboarding flows, or settings.
- No news/Pocket stories button on homepage.
- No voice typing microphone button in search toolbars.
