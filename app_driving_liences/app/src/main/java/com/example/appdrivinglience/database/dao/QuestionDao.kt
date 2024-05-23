package com.example.appdrivinglience.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appdrivinglience.database.model.Question
@Dao
interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestion(question: Question)

    @Query("SELECT * FROM Question")
    fun getAllQuestion(): List<Question>

    @Query("SELECT * FROM Question where idCategoryQuestion = :id")
    fun getListQuestionByIdCategory(id: Long): List<Question>

    @Query("SELECT * FROM Question where id = :id")
    fun getListQuestionById(id: Long): List<Question>
}