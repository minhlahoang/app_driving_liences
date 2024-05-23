package com.example.appdrivinglience.repository

import com.example.appdrivinglience.database.model.Notification
import java.util.Calendar

interface AlarmRepository {

    fun createAlarm(notification: Notification, timeStart: Long)
    fun cancelAlarm(notification: Notification )
}