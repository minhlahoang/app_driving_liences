package com.example.appdrivinglience.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Examination")
data class Examination(
    @PrimaryKey(autoGenerate = true)
    val id : Long ,
    val idQuestion : Long ,
    val numberQuestion: Int ,
    val answerCorrect : String ,
)
