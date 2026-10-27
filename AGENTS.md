# AI Agents & Project Architectural Rules: Icefox Zero-Account Edition

## Core Architectural Principles

This codebase is **Icefox**, a privacy-centric fork of **Iceraven Browser** (Mozilla Fenix & GeckoView) where **all account systems, cloud synchronization, website login/password storage, form autofill, bookmarks, AI controls/summaries, and auxiliary account-linked services are permanently excised**.

Any AI assistant or contributor working on this repository must strictly adhere to the following guidelines:

---

### 1. Mandatory Documentation Maintenance Rule
- **Every Task Execution Rule**: After **any** code update, feature modification, refactoring, or bug fix, **all relevant `.md` documentation files (`README.md`, `STRUCTURE.md`, `CHANGELOG.md`, `AGENTS.md`, `AI_INSTRUCTIONS.md`, `CLAUDE.md`) MUST be updated immediately** to reflect the changes before concluding the task.
- **Changelog Maintenance**: Every user-facing or architectural change must be recorded under the current version in `CHANGELOG.md` following Keep a Changelog standards.
- **AI Rules Synchronization**: Any new architectural rule or convention must be recorded in `AGENTS.md` and `AI_INSTRUCTIONS.md`.

---

### 2. No Account or Sync Reintroduction
- **Mozilla Account (FxA)**: Never add back `FxaAccountManager`, Mozilla Account login, signup, authentication flows, or device constellation / pairing.
- **Firefox Sync**: All Sync engines (`SyncEngine.History`, `SyncEngine.Bookmarks`, `SyncEngine.Passwords`, `SyncEngine.Tabs`, `SyncEngine.CreditCards`, `SyncEngine.Addresses`) must remain disabled and unbound from `GlobalSyncableStoreProvider`.
- **Tabs Tray**: The Tabs Tray must strictly remain a 2-page or 3-page layout (Private Tabs, Normal Tabs, and optionally Tab Groups). The `SyncedTabs` page and header indicator are permanently removed.
- **Menus & Share**: Menus and share sheets must not contain "Send tab to device", "Sync and save data", "Sync now", or Account preferences.

---

### 3. No Internal Password / Autofill Storage
- **GeckoView Autocomplete**: `GeckoRuntime.autocompleteStorageDelegate` must strictly remain `null`. GeckoView must never prompt users to save credentials, never remember passwords, and never autofill form data.
- **Saved Logins**: The browser password manager (`feature-logins`, saved logins UI, password exceptions) is disabled. Users who desire password management are directed to external OS-level autofill providers (e.g., Bitwarden, KeePassDX).
- **Credit Cards & Addresses**: Credit card and address autofill storage is stripped from the browser engine and Settings.

---

### 4. No Bookmarks
- **Bookmarks UI & Features**: Menus, toolbars, search suggestions, home shortcuts, and settings must never expose bookmark creation, editing, importing/exporting, or viewing.
- **Bookmarks Storage Warmup**: `bookmarksStorage.warmUp()` must not execute during startup.
- **Zero Bookmarks Trace**: `bookmarks.db` and associated SQLite database files are permanently wiped and purged.

---

### 5. No AI Controls or Summarization
- **AI Controls & Page Summaries**: All generative AI summaries ("shake to summarize"), AI feature blocks, and telemetry are permanently disabled and stripped from UI/Settings. Standard page translation remains functional.

---

### 6. Auxiliary Cloud Services
- **Firefox Relay**: No email masking or Relay RP integration.
- **Pocket**: No Pocket stories recommendation feed or sponsored content.
- **IP Protection / VPN**: No Mozilla VPN / IP Protection services or account authentication wrappers.

---

### 7. Zero-Trace Startup Hygiene
- `AccountAndLoginPurge.purgeLegacyData(...)` runs on cold application startup to actively remove any lingering SQLite databases (`logins.db`, `autofill.db`, `remote_tabs.db`, `pocket.db`, `bookmarks.db`), token files, or obsolete SharedPreferences.

---

### 8. Application Branding & Identity
- Application name is **Icefox** (application ID `com.ma.icefox`, version `1.0.0`).
- Deep links use `icefox://` (and `icefox-debug://`).
- Homepage and About headers use dynamic "Icefox" typography and adaptive app launcher icon.
- "Show sign in button", "Firefox Labs", and "Set as default browser" (Settings toggle, 3-dots menu card, and onboarding prompts) are permanently removed.
- About section includes "About Dev" referencing developer Mina Android and source repository (Icefox at `https://github.com/mina-android/Icefox`).

---

### 9. Homepage & Search Hygiene
- **News / Pocket Stories**: Homepage news icon, stories entry point, and "Discover More" screen navigation are permanently removed.
- **Search Menu**: Bookmarks search engine shortcut (`BOOKMARKS_SEARCH_ENGINE_ID`) is permanently removed from the homepage search sheet.
- **Voice Typing**: Voice search / voice typing microphone button and display toolbar entry points are permanently disabled and excised from search settings.

---

### 10. Dependency & Version Invariants
- `version.txt` in repository root must **never** be changed away from `158.0.2` — it anchors the GeckoView binary maven dependency. Application versioning `1.0.0` is managed in `app/build.gradle`.
