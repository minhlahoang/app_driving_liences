package com.example.appdrivinglience.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "History")
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val pathExam: String = "",
    val answerExam: Int = 0
)
