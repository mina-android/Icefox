/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.components

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import org.mozilla.fenix.R
import java.io.File

/**
 * Startup routine to purge all legacy account credentials, tokens, Sync state,
 * saved logins, and autofill database files to guarantee zero lingering traces on device.
 */
object AccountAndLoginPurge {

    private const val PREFS_PURGE_EXECUTED_KEY = "icefox_account_bookmark_ai_purged_v2"

    fun purgeLegacyData(context: Context) {
        val defaultPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        if (defaultPrefs.getBoolean(PREFS_PURGE_EXECUTED_KEY, false)) {
            return
        }

        try {
            // 1. Wipe SharedPreferences keys for accounts, sync, logins, autofill, bookmarks, and AI
            defaultPrefs.edit {
                // FxA & Sync
                remove(context.getString(R.string.pref_key_fxa_signed_in))
                remove(context.getString(R.string.pref_key_override_fxa_server))
                remove(context.getString(R.string.pref_key_use_react_fxa))
                remove(context.getString(R.string.pref_key_sign_in))
                remove(context.getString(R.string.pref_key_sync_manage_account))
                remove(context.getString(R.string.pref_key_sync_device_name))
                remove(context.getString(R.string.pref_key_sync_now))
                remove(context.getString(R.string.pref_key_sync_history))
                remove(context.getString(R.string.pref_key_sync_bookmarks))
                remove(context.getString(R.string.pref_key_sync_logins))
                remove(context.getString(R.string.pref_key_sync_tabs))
                remove(context.getString(R.string.pref_key_sync_sign_in))
                remove(context.getString(R.string.pref_key_sync_credit_cards))
                remove(context.getString(R.string.pref_key_sync_address))
                remove(context.getString(R.string.pref_key_sync_debug))
                remove(context.getString(R.string.pref_key_show_sign_in_button))

                // Logins & Passwords
                remove(context.getString(R.string.pref_key_passwords))
                remove(context.getString(R.string.pref_key_autofill_logins))

                // Credit cards & Autofill
                remove(context.getString(R.string.pref_key_credit_cards))
                remove(context.getString(R.string.pref_key_credit_cards_save_and_autofill_cards))
                remove(context.getString(R.string.pref_key_credit_cards_sync_cards_across_devices))
                remove(context.getString(R.string.pref_key_credit_cards_manage_cards))

                // Relay / Email masks
                remove(context.getString(R.string.pref_key_email_masks))

                // Pocket
                remove(context.getString(R.string.pref_key_pocket_homescreen_recommendations))
                remove(context.getString(R.string.pref_key_pocket_sponsored_stories))

                // Bookmarks
                remove(context.getString(R.string.pref_key_search_bookmarks))
                remove(context.getString(R.string.pref_key_search_bookmarks_in_private))
                remove(context.getString(R.string.pref_key_customization_bookmarks))
                remove(context.getString(R.string.pref_key_import_bookmarks))
                remove(context.getString(R.string.pref_key_enable_import_bookmarks))

                // AI Controls & Summaries
                remove(context.getString(R.string.pref_key_page_summaries))
                remove(context.getString(R.string.pref_key_ai_controls))
                remove(context.getString(R.string.pref_key_enable_shake_to_summarize))
                remove(context.getString(R.string.pref_key_enable_ai_controls))

                putBoolean(PREFS_PURGE_EXECUTED_KEY, true)
            }

            // 2. Delete database files
            val databasesToDelete = listOf(
                "logins.db",
                "logins.db-wal",
                "logins.db-shm",
                "logins-schema.db",
                "passwords.db",
                "passwords.db-wal",
                "passwords.db-shm",
                "autofill.db",
                "autofill.db-wal",
                "autofill.db-shm",
                "remote_tabs.db",
                "remote_tabs.db-wal",
                "remote_tabs.db-shm",
                "pocket.db",
                "pocket.db-wal",
                "pocket.db-shm",
                "bookmarks.db",
                "bookmarks.db-wal",
                "bookmarks.db-shm",
                "bookmarks.sqlite",
                "bookmarks.sqlite-wal",
                "bookmarks.sqlite-shm",
            )
            for (dbName in databasesToDelete) {
                try {
                    context.deleteDatabase(dbName)
                } catch (_: Exception) { }
            }

            // 3. Delete files from internal files directory
            val prefixesToDelete = listOf(
                "mozilla_components_fxa",
                "fxa_account",
                "logins",
                "pocket",
                "bookmarks",
                "ai_controls",
                "summariz",
            )
            purgeDirectoryMatching(context.filesDir, prefixesToDelete)
            purgeDirectoryMatching(context.noBackupFilesDir, prefixesToDelete)
        } catch (_: Exception) {
            // Ignore failure during purge; best effort
        }
    }

    private fun purgeDirectoryMatching(dir: File?, prefixes: List<String>) {
        if (dir == null || !dir.exists() || !dir.isDirectory) return
        val files = dir.listFiles() ?: return
        for (file in files) {
            val name = file.name
            if (prefixes.any { name.startsWith(it, ignoreCase = true) }) {
                try {
                    if (file.isDirectory) {
                        file.deleteRecursively()
                    } else {
                        file.delete()
                    }
                } catch (_: Exception) { }
            }
        }
    }
}
