package com.example.appdrivinglience.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QuestionWrong")
data class QuestionWrongModel(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L ,
    val idQuestion: Long = 0L,
)
