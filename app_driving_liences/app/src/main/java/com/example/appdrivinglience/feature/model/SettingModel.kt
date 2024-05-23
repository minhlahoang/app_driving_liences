package com.example.appdrivinglience.feature.model

import androidx.annotation.DrawableRes
import com.example.appdrivinglience.R

data class SettingModel(
    @DrawableRes val iconRes : Int = R.drawable.ic_launcher_background,
    val nameSetting : String = "",
    val isSwitch : Boolean = false
)
