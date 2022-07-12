package com.example.listbuzz.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.listbuzz.data.ToDoModal
import com.example.listbuzz.utiles.putParcelableExtra
import com.example.listbuzz.utiles.utiles.ACTION_SET_EXACT
import java.text.SimpleDateFormat
import java.util.*

object SetAlarm {
    fun setAlarmReminderForTodo(context: Context, todoModal: ToDoModal) {
        val alarmManager: AlarmManager? =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val intent = Intent(context, AlarmManager::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            todoModal.id,
            intent.apply {
                action = ACTION_SET_EXACT
                putExtra("title", todoModal.title)
                putExtra("date", todoModal.date)
                putExtra("time", todoModal.time)
                //putParcelableArrayListExtra()
                putParcelableExtra("priority", todoModal.priority)
                putExtra("id", todoModal.id)
                Log.d("TodoLog", "$todoModal.id")
            },
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
        )
        val reminderDateandTime = "${todoModal.date} ${todoModal.time}"
        Log.d("ToDoLog", "$reminderDateandTime in setAlarm")
        val formate = SimpleDateFormat("dd/MM/yyyy h:mm a")
        val todoTime = formate.parse(reminderDateandTime)
        val nowTime = Calendar.getInstance().time

        Log.d("ToDoLog", "$todoTime in before setAlarm")
        Log.d("ToDoLog", "${System.currentTimeMillis()}")

        if (todoTime > nowTime) {
            alarmManager?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        todoTime.time,
                        pendingIntent
                    )
                    Log.d("ToDoLog", "$todoTime in setAlarm in alarm Manager")
                } else {
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        todoTime.time,
                        pendingIntent
                    )
                }
            }
        } else {
            alarmManager?.cancel(pendingIntent)
        }
    }
}
