package com.example.appdrivinglience.core

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class SettingSharePreference @Inject constructor(@ApplicationContext context: Context) {

    companion object{
        private const val APP_THEME = "APP_THEME"
    }
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    fun saveAppTheme(theme: Boolean) {
      sharedPreferences.edit().apply {
          putBoolean(APP_THEME, theme);
      }.apply()
    }

    fun getAppTheme(): Boolean{
        return sharedPreferences.getBoolean(APP_THEME,false)
    }
}