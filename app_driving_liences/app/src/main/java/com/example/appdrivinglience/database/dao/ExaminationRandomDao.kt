package com.example.appdrivinglience.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appdrivinglience.database.model.ExaminationRandomModel

@Dao
interface ExaminationRandomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExaminationRandom(examinationModel: ExaminationRandomModel)
    @Query("SELECT * FROM ExaminationRandom")
    fun getAllExaminationRandom(): List<ExaminationRandomModel>

    @Query("SELECT * FROM ExaminationRandom WHERE categoryLicense = :categoryLicense")
    fun getAllExaminationRandom(categoryLicense: String): List<ExaminationRandomModel>
}