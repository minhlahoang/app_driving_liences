package com.example.appdrivinglience.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appdrivinglience.database.model.QuestionWrongModel

@Dao
interface QuestionWrongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestionWrong(questionWrongModel: QuestionWrongModel)

    @Query("DELETE FROM questionwrong where idQuestion = :id")
    fun deleteQuestionWrongById(id: Long)

    @Query("SELECT * FROM questionwrong")
    fun getAllQuestionWrong(): List<QuestionWrongModel>
}