package com.example.appdrivinglience.feature.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class StudyTheoryModel(
    val id : String = System.currentTimeMillis().toString(),
    val nameTheory : String = "",
    @DrawableRes val iconRes: Int ,
    @ColorRes val colorTheory : Int ,
    val idCategory: Int = 0,
)
