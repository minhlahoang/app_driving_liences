package com.example.appdrivinglience.core

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class ReadDataManager @Inject constructor(private val context: Context) {

    @SuppressLint("CommitPrefEdits")
    fun setInstalledApps(data: Boolean){
        val sharePreference: SharedPreferences =  context.getSharedPreferences("APP_INSTALLED", Context.MODE_PRIVATE);

        sharePreference.edit().apply {
            putBoolean("INSTALLED_APPS", true);
        }.apply()
    }

    fun getInstalledApps(): Boolean{
        val sharePreference: SharedPreferences =  context.getSharedPreferences("APP_INSTALLED", Context.MODE_PRIVATE);
        return sharePreference.getBoolean("INSTALLED_APPS", false)
    }
}