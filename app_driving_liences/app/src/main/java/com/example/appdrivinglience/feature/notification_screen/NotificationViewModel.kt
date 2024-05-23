package com.example.appdrivinglience.feature.notification_screen

import android.annotation.SuppressLint
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appdrivinglience.database.dao.NotificationDao
import com.example.appdrivinglience.database.model.Notification
import com.example.appdrivinglience.repository.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationDao: NotificationDao,
    private val alarmRepository: AlarmRepository
): ViewModel(){

    private val _notificationState = mutableStateOf<NotificationState>(NotificationState.Loading)
    val notificationState: State<NotificationState> = _notificationState

    private var notificationRemoved: Notification?= null

    fun setRemoveNotification(data: Notification){
        notificationRemoved = data
    }
    fun getRemoveNotification() : Notification? {
        return notificationRemoved
    }
    fun insertNotification(notification: Notification){
        viewModelScope.launch {
            notificationDao.insertNotification(notification)
        }
    }

    fun getAllListNotification(){
        _notificationState.value = NotificationState.Loading
        viewModelScope.launch {
            runCatching {
                _notificationState.value = NotificationState.Success(notificationDao.getListNotification())

            }.onFailure {
                _notificationState.value = NotificationState.Error(it.message)
            }
        }
    }
    private fun reScheduleNotifications(notification: Notification, timeStart: Long){
        alarmRepository.createAlarm(notification,timeStart)
    }
    fun scheduleLeastNotification(notification: Notification){
        val leastNotification = notificationDao.getLeastNotification()
        if (leastNotification.isNotEmpty()) {
            handleReScheduleNotifications(leastNotification[0],notification.time)
        }
    }
    fun cancelNotifications(notification: Notification){
        alarmRepository.cancelAlarm(notification)
        notificationDao.updateStatusNotification(false,notification.id ?: 0)
    }

    fun deleteNotification(notification: Notification){
        cancelNotifications(notification)
        notificationDao.deleteNotificationById(notification.id)
        _notificationState.value = NotificationState.Success(notificationDao.getListNotification())
    }

    @SuppressLint("SimpleDateFormat")
    fun rescheduleAlarms(){
        viewModelScope.launch {

            val listNotifications = notificationDao.getListNotification()
            println("listNotifications = ${listNotifications.size}")
            println("=======CurrentNotification======")
            listNotifications.forEach {
                val calendar = Calendar.getInstance()
                val currentTime = calendar.timeInMillis
                println("$it")
                println(currentTime)
                if (it.isSystemNotification) {
                    handleReScheduleNotifications(it, calendar, currentTime)
                }
            }
        }
    }

    private fun handleReScheduleNotifications(
        it: Notification,
        calendar: Calendar,
        currentTime: Long
    ) {
        val dayOfWeek = it.daysOfWeek % 7
        val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        calendar.set(Calendar.HOUR_OF_DAY, it.hour)
        calendar.set(Calendar.MINUTE, it.minutes)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND,0)

        if (it.time <= currentTime) {
            if (dayOfWeek > currentDayOfWeek) {
                calendar.add(Calendar.DAY_OF_MONTH, dayOfWeek - currentDayOfWeek)
                notificationDao.updateNotification(calendar.timeInMillis, it.id ?: 0)
            } else if (dayOfWeek < currentDayOfWeek) {
                calendar.add(Calendar.DAY_OF_MONTH, dayOfWeek - currentDayOfWeek + 7)
                notificationDao.updateNotification(calendar.timeInMillis, it.id ?: 0)
                //                    val date = Date(calendar.timeInMillis)
                //                    println(SimpleDateFormat("EEEE, yyyy-MM-dd HH:mm").format(date))
            } else {
                println("SESCHEDULE========> timeStart = ${calendar.timeInMillis}")
                if (calendar.timeInMillis >= currentTime) {
                    reScheduleNotifications(it, calendar.timeInMillis)
                }else {
                    calendar.add(Calendar.DAY_OF_MONTH, 7)
                    notificationDao.updateNotification(calendar.timeInMillis, it.id ?: 0)
                }
            }
        }
    }
    private fun handleReScheduleNotifications(
        it: Notification,
        currentTime: Long
    ) {
        val calendar = Calendar.getInstance()
        val dayOfWeek = it.daysOfWeek % 7
        val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)


        calendar.set(Calendar.HOUR_OF_DAY, it.hour)
        calendar.set(Calendar.MINUTE, it.minutes)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND,0)

        println(calendar.timeInMillis)

        if (it.time <= currentTime) {
            if (dayOfWeek > currentDayOfWeek) {
                calendar.add(Calendar.DAY_OF_MONTH, dayOfWeek - currentDayOfWeek)
                notificationDao.updateNotification(calendar.timeInMillis, it.id ?: 0)
            } else if (dayOfWeek < currentDayOfWeek) {
                calendar.add(Calendar.DAY_OF_MONTH, dayOfWeek - currentDayOfWeek + 7)
                notificationDao.updateNotification(calendar.timeInMillis, it.id ?: 0)
                //                    val date = Date(calendar.timeInMillis)
                //                    println(SimpleDateFormat("EEEE, yyyy-MM-dd HH:mm").format(date))
            } else {
                calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek)
                println("calendar.timeInMillis = ${calendar.timeInMillis}")
                if (calendar.timeInMillis >= currentTime) {
                    reScheduleNotifications(it, calendar.timeInMillis)
                }else {
                    calendar.add(Calendar.DAY_OF_MONTH, 7)
                    notificationDao.updateNotification(calendar.timeInMillis, it.id ?: 0)
                }
            }
        }
    }

}

sealed class NotificationState {
    data object Loading : NotificationState()
    data class Success(val listNotification: List<Notification>) : NotificationState()
    data class Error(val error: String?) : NotificationState()
}