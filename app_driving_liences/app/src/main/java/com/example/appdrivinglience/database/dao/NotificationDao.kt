package com.example.appdrivinglience.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appdrivinglience.database.model.Notification

@Dao
interface NotificationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotification(notification: Notification)

    @Query("UPDATE Notification SET time = :time WHERE id = :id")
    fun updateNotification(time: Long, id : Int )

    @Query("UPDATE Notification SET isSystemNotification = :data WHERE id = :id")
    fun updateStatusNotification(data: Boolean ,id : Int )

    @Query("DELETE FROM Notification WHERE id = :id")
    fun deleteNotificationById(id: Int)

    @Query("SELECT * FROM Notification")
    fun getListNotification(): List<Notification>

    @Query("SELECT * FROM Notification ORDER BY id DESC LIMIT 1")
    fun getLeastNotification(): List<Notification>
}