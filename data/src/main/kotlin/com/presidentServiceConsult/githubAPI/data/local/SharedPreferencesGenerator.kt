package com.presidentServiceConsult.githubAPI.data.local

import android.content.Context
import android.content.SharedPreferences

internal class SharedPreferencesGenerator(private val context: Context) {

    fun generateSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences("com.taxielquaty.driver", Context.MODE_PRIVATE)
    }
}