package com.example.appdrivinglience.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appdrivinglience.database.model.History

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: History)
    @Query("SELECT * FROM History WHERE pathExam =:path")
    fun getAllHistoryList(path : String): List<History>
}