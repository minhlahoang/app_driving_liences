package com.example.appdrivinglience.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appdrivinglience.feature.trick_screen.TrickModel

@Dao
interface TrickDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrick(trickModel: TrickModel)

    @Query("SELECT * FROM Trick")
    fun getAllTrick(): List<TrickModel>
}