package com.example.appdrivinglience.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.example.appdrivinglience.database.model.Notification
import com.example.appdrivinglience.service.AlarmService
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor( @ApplicationContext val context: Context) : AlarmRepository {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    override fun createAlarm(notification: Notification , timeStart: Long) {
        val intentService = Intent(context, AlarmService::class.java)
        intentService.putExtra("idNotification", notification.id)
        intentService.putExtra("contentNotification", notification.contentNotification)

        val pendingIntent = PendingIntent.getService(context, notification.hashCode(),intentService,PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        println("id = "+notification.id+ "timeStart = "+ timeStart + " current = " + System.currentTimeMillis())
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC, timeStart, pendingIntent)
    }
    override fun cancelAlarm(notification: Notification) {
        val intentService = Intent(context, AlarmService::class.java)
        val pendingIntent = PendingIntent.getService(context, notification.hashCode(),intentService,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.cancel(pendingIntent)
    }
}