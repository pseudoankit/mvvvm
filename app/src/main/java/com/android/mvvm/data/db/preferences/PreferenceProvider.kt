package com.android.mvvm.data.db.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

private const val KEY_SAVED_AT = "key_saved"

class PreferenceProvider(
    context: Context
) {
    private val appContext = context.applicationContext
    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(
            appContext
        )

    fun saveLastSavedAt(savedAt: String) {
        preferences.edit().putString(KEY_SAVED_AT, savedAt).apply()
    }

    fun getLastSavedAt(): String? {
        return preferences.getString(KEY_SAVED_AT, null)
    }
}