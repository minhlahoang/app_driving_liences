package com.example.appdrivinglience.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ImageQuestion")
data class ImageQuestion(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val urlImage: String,
)
