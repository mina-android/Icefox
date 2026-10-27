# Icefox — Project Reference for Claude

> **Purpose:** This file gives a complete, code-verified reference of the Icefox Android browser so any Claude or AI assistant chat can pick up work immediately without re-reading every source file.  
> **Last synced with code:** v1.0.0 — Initial Zero-Account Release, excised Sync/Bookmarks/Logins/AI, customized About Dev section, nag-free UI, split APKs.

---

## 1. Project Identity

| Field | Value |
|---|---|
| Application Name | **Icefox** |
| Package Name | `com.ma.icefox` |
| Application Version | `1.0.0` (`app/build.gradle`) |
| GeckoView Engine | `geckoview-omni:158.0.2` (`version.txt`) |
| Platform | Android (minSdk 24, targetSdk 35/36) |
| Architecture Support | `arm64-v8a`, `armeabi-v7a`, `x86_64` |
| Developer | [Mina Android](https://github.com/mina-android) |
| GitHub Repository | [https://github.com/mina-android/Icefox](https://github.com/mina-android/Icefox) |
| Fork Origin | [Iceraven Browser](https://github.com/fork-maintainers/iceraven-browser) (Mozilla Fenix & GeckoView) |
| License | Mozilla Public License 2.0 (MPL-2.0) |

---

## 2. Common Commands

### 2.1 Environment Setup
```powershell
$env:ANDROID_HOME = "C:\Users\minaa\AppData\Local\Android\Sdk"
$env:Path = "C:\Projects\IceRaven\venv\Scripts;C:\Users\minaa\AppData\Local\Programs\Python\Python312;C:\Users\minaa\AppData\Local\Programs\Python\Python312\Scripts;" + $env:Path
```

### 2.2 Compilation
```powershell
# Assemble release split APKs
.\gradlew app:assembleForkRelease

# Assemble debug APK
.\gradlew app:assembleForkDebug
```

### 2.3 Installation & Testing
```powershell
# Install ARM64 release APK to connected device
& "$env:ANDROID_HOME\platform-tools\adb.exe" install -r gradle\build\app\outputs\apk\forkRelease\Icefox-v1.0.0-arm64-v8a.apk

# Launch Icefox HomeActivity
& "$env:ANDROID_HOME\platform-tools\adb.exe" shell am start -n com.ma.icefox/org.mozilla.fenix.HomeActivity
```

---

## 3. Architecture & File Mapping

```
IceRaven/
├── app/
│   ├── build.gradle                       # VersionName "1.0.0", applicationId "com.ma.icefox"
│   └── src/main/
│       ├── AndroidManifest.xml            # Deep links: icefox:// and icefox-debug://
│       ├── java/org/mozilla/fenix/
│       │   ├── FenixApplication.kt        # Application boot; triggers AccountAndLoginPurge
│       │   ├── components/
│       │   │   ├── AccountAndLoginPurge.kt# Cold-start SQLite database & token shredder
│       │   │   ├── Core.kt                # Core components provider (no SyncableStore)
│       │   │   └── menu/
│       │   │       └── MenuDialogFragment.kt # Compose 3-dots menu (showBanner = false)
│       │   ├── gecko/
│       │   │   └── GeckoProvider.kt       # GeckoRuntime setup (autocompleteStorage = null)
│       │   ├── home/
│       │   │   ├── HomeFragment.kt        # Homepage controller
│       │   │   ├── store/HomepageState.kt # showStoriesButton = false
│       │   │   └── ui/WordmarkAndLogo.kt  # Jetpack Compose Material 3 typography
│       │   ├── search/
│       │   │   └── BrowserToolbarSearchMiddleware.kt # Suppressed voice search button
│       │   ├── settings/
│       │   │   ├── about/AboutFragment.kt # About Dev (Mina Android, Icefox repo link)
│       │   │   ├── search/SearchEngineFragment.kt # Hidden voice search toggle
│       │   │   └── SupportUtils.kt        # WHATS_NEW_URL = "https://github.com/mina-android/Icefox"
│       │   ├── tabstray/
│       │   │   └── ui/tabstray/TabsTray.kt# 2-Page layout (Normal & Private tabs only)
│       │   └── utils/Settings.kt          # Global feature flags (voice, stories, menu card = false)
│       └── res/
│           ├── values/strings.xml         # Brand & about string resources
│           └── xml/
│               ├── preferences.xml        # Stripped default browser & labs toggles
│               └── search_settings_preferences.xml # Hidden voice search toggle
├── AGENTS.md                              # AI Agent rules & mandatory file sync instructions
├── AI_INSTRUCTIONS.md                     # Condensed rules-first guidelines & toolchain stack
├── CHANGELOG.md                           # Version release notes and migration history
├── CLAUDE.md                              # This file
├── README.md                              # Project overview, badges, features, and quick links
├── STRUCTURE.md                           # System architecture and data flow blueprint
└── version.txt                            # Upstream GeckoView binary anchor (158.0.2)
```

---

## 4. Mandatory Developer Rules

1. **Keep Documentation Synchronized**:
   - Every code modification must immediately update all relevant `.md` files (`README.md`, `STRUCTURE.md`, `CHANGELOG.md`, `AGENTS.md`, `AI_INSTRUCTIONS.md`, `CLAUDE.md`).
2. **Preserve Zero-Account Invariants**:
   - Never reintroduce Mozilla Accounts (FxA), Sync engines, password managers, autofill storage, bookmarks, or AI summarization.
3. **Preserve `version.txt`**:
   - `version.txt` must always remain `158.0.2`.
