package com.example.hotornot

import android.content.Context
import android.content.SharedPreferences
import com.example.hotornot.data.Friend
import com.example.hotornot.data.User
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PreferencesUtil internal constructor(private val context: Context) {

    init {
        sPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun getUserData(): User? {
        val json: String = sPref.getString(PREF_SERIALIZABLE_USER, null) ?: return null
        return Json.decodeFromString(json)
    }

    fun saveUserData(user: User) {
        val json = Json.encodeToString(user)
        sPref.edit().putString(PREF_SERIALIZABLE_USER, json).apply()
    }

    fun deleteUser() {
        sPref.edit().clear().apply()
    }

    fun getFriends(): List<Friend> {
        val json: String = sPref.getString(PREF_SERIALIZABLE_FRIENDS, null) ?: return listOf()
        return Json.decodeFromString(json)
    }

    fun setFriends(friends: List<Friend>) {
        val json = Json.encodeToString(friends)
        sPref.edit().putString(PREF_SERIALIZABLE_FRIENDS, json).apply()
    }

    companion object {

        private lateinit var sPref: SharedPreferences
        private const val PREF_FILE_NAME = "com.example.hotornot"
        private const val PREF_SERIALIZABLE_USER = "com.example.hotornot"
        private const val PREF_SERIALIZABLE_FRIENDS = "com.example.hotornot"
        private var instance: PreferencesUtil? = null
        fun getInstance(context: Context): PreferencesUtil {
            if (instance == null) {
                instance = PreferencesUtil(context)
            }
            return instance as PreferencesUtil
        }
    }
}