package com.example.hotornot

import android.content.Context
import android.content.SharedPreferences
import com.google.android.material.textfield.TextInputEditText

class UserSharedPreference(context: Context) {

    private val userSharedPreferences = context.getSharedPreferences("myPreference", Context.MODE_PRIVATE)

    fun putBoolean(key: String, value: Boolean){
        userSharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return userSharedPreferences.getBoolean(key, false)
    }

    fun putString(key: String, value: String){
        userSharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String?{
        return userSharedPreferences.getString(key, null)
    }

    fun putInt(key: String, value: Int){
        userSharedPreferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String): Int {
        return userSharedPreferences.getInt(key, 0)
    }

    fun saveUserDates(firstNameEditText: String, lastNameEditText: String, emailEditText: String) {
        val editor = userSharedPreferences.edit()
        editor.apply {
            putString(Constants.keyFirstName, firstNameEditText)
            putString(Constants.keyLastName, lastNameEditText)
            putString(Constants.keyEmail, emailEditText)
        }.apply()
    }

    fun getUserName(){
        getString(Constants.keyFirstName)
        getString(Constants.keyLastName)
    }

    fun getUserEmail(key: String): String{
        return userSharedPreferences.getString(key, null).toString()
    }
}