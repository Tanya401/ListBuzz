package com.example.listbuzz.receiver


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.listbuzz.data.Priority
import com.example.listbuzz.utiles.utiles.ACTION_SET_EXACT

class AlarmReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
     val name = intent.getStringExtra("title")
        val date = intent.getStringExtra("date")
        val time = intent.getStringExtra("time")
        val priority = intent.getParcelableExtra<Priority>("priority")
        val todotimeanddate = "$date $time"
        val id= intent.getIntExtra("id",0)
        Log.d("ToDolog","$id id in receiver")
        if(ACTION_SET_EXACT == intent.action){
            if(id>=0){
                val notification = Notification()
                notification.showNotification(context,name,priority,todotimeanddate)
                Log.d("ToDolog","$name $priority $todotimeanddate")
            }
        }
    }
}