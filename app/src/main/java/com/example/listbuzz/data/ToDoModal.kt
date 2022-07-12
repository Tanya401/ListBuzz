package com.example.listbuzz.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "to_do_table")
@Parcelize
data class ToDoModal (
    @ColumnInfo(name = "title")
    val title:String,
    @ColumnInfo(name="date")
    val date:String,
    @ColumnInfo(name="time")
    val time:String,
    @ColumnInfo(name="priority")
    val priority:Priority,
    @ColumnInfo(name="addReminder")
    val addReminderForToDo:Boolean=false,
    @ColumnInfo(name="isCompleted")
    var todoCompleted:Boolean=false,
@PrimaryKey(autoGenerate = true)
@ColumnInfo(name="id")
val id:Int=0
): Parcelable