package com.example.appdrivinglience.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CategoryLicense")
data class CategoryLicense(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nameLicense: String = "",
    val description: String = "",
)