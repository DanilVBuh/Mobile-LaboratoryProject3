package com.example.laba3

import android.content.Context

class PreferencesHandler(val context: Context) {

    fun saveString(string: String) {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()

        editor.putString(STRING_KEY, string)

        editor.apply()
    }

    fun readString(): String {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(STRING_KEY, "") ?: ""

    }

    companion object {
        val PREFERENCES_FILE_NAME = "MY_PFREFERENCES"
        val STRING_KEY = "string_key"
    }
}