package com.example.appdrivinglience.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appdrivinglience.database.model.CategoryLicense

@Dao
interface CategoryLicenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategoryLicense(categoryLicense: CategoryLicense)
    @Query("SELECT * FROM CategoryLicense")
    fun getAllCategoryLicense(): List<CategoryLicense>
}