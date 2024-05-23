package com.example.appdrivinglience.database.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notification")
data class Notification(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val time: Long = 0L,
    val hour: Int = 0,
    val minutes: Int =0,
    val daysOfWeek: Int = 0,
    val contentNotification: String? = "",
    val isSystemNotification: Boolean = true
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeLong(time)
        parcel.writeInt(hour)
        parcel.writeInt(minutes)
        parcel.writeInt(daysOfWeek)
        parcel.writeString(contentNotification)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Notification> {
        override fun createFromParcel(parcel: Parcel): Notification {
            return Notification(parcel)
        }

        override fun newArray(size: Int): Array<Notification?> {
            return arrayOfNulls(size)
        }
    }
}
