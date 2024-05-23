package com.example.appdrivinglience.feature.setting

import androidx.lifecycle.ViewModel
import com.example.appdrivinglience.core.SettingSharePreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class SettingViewModel @Inject constructor(
    private val sharePreference: SettingSharePreference
) : ViewModel(){

    fun getAppTheme(): Boolean {
        return sharePreference.getAppTheme();
    }
}