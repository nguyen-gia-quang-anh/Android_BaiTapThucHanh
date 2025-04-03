package com.example.th4_b1sharedpreferences

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.core.content.edit

// preference helper class to save and retrieve from edt_name and edt_pwd
class PreferenceHelper(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("my_pref", Context.MODE_PRIVATE)

    fun saveUser(name: String, pwd: String) {
        sharedPreferences.edit {
            putString("name", name)
            putString("pwd", pwd)
        }
    }

    fun getName(): String? {
        return sharedPreferences.getString("name", "")
    }

    fun getPwd(): String? {
        return sharedPreferences.getString("pwd", "")
    }

    fun clearData() {
        sharedPreferences.edit().clear().apply()
    }
}