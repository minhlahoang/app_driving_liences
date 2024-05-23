package com.example.appdrivinglience.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ExaminationRandom")
data class ExaminationRandomModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long?= null ,
    val idExam : Long ,
    val categoryLicense : String ,
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
