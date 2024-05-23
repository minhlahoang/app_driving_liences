package com.example.appdrivinglience.feature.model

data class QuestionModel(
    val idQuestion: String = System.currentTimeMillis().toString(),
    val question: String = "",
    val ansA: String = "",
    val ansB: String = "",
    val ansC: String = "",
    val ansD: String = "",
    val correctQuestion: Int = 0
)
