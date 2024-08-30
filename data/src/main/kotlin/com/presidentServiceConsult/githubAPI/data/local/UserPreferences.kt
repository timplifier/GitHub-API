package com.presidentServiceConsult.githubAPI.data.local

import android.content.SharedPreferences

class UserPreferences(preferences: SharedPreferences) : Preferences(preferences) {
    var githubAPIToken by string()
}