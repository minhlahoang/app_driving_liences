package com.example.appdrivinglience.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appdrivinglience.database.model.CategoryQuestion

@Dao
interface CategoryQuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategoryLicense(categoryQuestion: CategoryQuestion)
    @Query("SELECT * FROM CategoryQuestion")
    fun getAllCategoryQuestion(): List<CategoryQuestion>
}