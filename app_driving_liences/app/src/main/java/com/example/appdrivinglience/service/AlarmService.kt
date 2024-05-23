package com.example.appdrivinglience.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.appdrivinglience.MyApplication.Companion.CHANNEL_ID
import com.example.appdrivinglience.R
import com.example.appdrivinglience.database.model.Notification

class AlarmService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.takeIf { it.getIntExtra("idNotification", -1) != -1
                && it.getStringExtra("contentNotification") != null
        }?.let {
            println("---------NOTIFICATION ----------------")
            val id = it.getIntExtra("idNotification",0)
            val content = it.getStringExtra("contentNotification") ?: ""
            showBasicNotification(id, content,this)
        }
        println("---------NOTIFICATION FINISHED ----------------")
        return super.onStartCommand(intent, flags, startId)
    }
    private fun showBasicNotification(id: Int , contentNotification: String , context: Context){
        val notificationManager =  context.getSystemService(NotificationManager::class.java)
        val notificationCompat = NotificationCompat.Builder(context,CHANNEL_ID)
            .setContentTitle("Thông báo")
            .setContentText(contentNotification)
            .setSmallIcon(R.drawable.notification)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            id , notificationCompat
        )
    }
    override fun onDestroy() {
        super.onDestroy()
    }
}