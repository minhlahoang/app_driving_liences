package com.example.appdrivinglience.feature.trick_screen

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Trick")
data class TrickModel(
    @PrimaryKey(autoGenerate = true)
    val idTrick: Long= 0L,
    val title: String = "",
    var content: String = "",
)
