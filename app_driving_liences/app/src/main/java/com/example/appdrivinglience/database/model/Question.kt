package com.example.appdrivinglience.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Question")
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val idCategory: Long = 0L,
    val idCategoryQuestion: Long = 0L,
    val imageUrl:String = "",
    var question: String= "",
    val ansA : String? = null,
    val ansB : String? = null,
    val ansC : String? = null,
    val ansD : String? = null,
    val correctAns : Int =0,
    val analysis: String?= null,
    val isImportant : Boolean = false
)
