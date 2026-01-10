package com.example.reflexgame.user

import android.content.Context
import kotlin.random.Random

object UserManager {

    private const val PREF_NAME = "reflex_user_prefs"
    private const val KEY_USERNAME = "username"

    fun getOrCreateUsername(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        var username = prefs.getString(KEY_USERNAME, null)

        if (username == null) {
            username = "Player_${Random.nextInt(1000, 9999)}"
            prefs.edit().putString(KEY_USERNAME, username).apply()
        }

        return username
    }
}