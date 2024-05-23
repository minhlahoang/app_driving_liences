package com.example.appdrivinglience.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CategoryQuestion")
data class CategoryQuestion(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    val nameCategoryQuestion: String = "",
)
