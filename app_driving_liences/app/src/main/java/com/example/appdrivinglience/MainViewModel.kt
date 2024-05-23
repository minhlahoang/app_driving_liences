package com.example.appdrivinglience

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    var appThemeState by mutableStateOf(false)
    private val _appThemeLiveData by lazy { MutableLiveData(false) }
    val appThemeLiveData : LiveData<Boolean> = _appThemeLiveData
    fun setAppTheme(data: Boolean) {
        _appThemeLiveData.value = data
    }
}