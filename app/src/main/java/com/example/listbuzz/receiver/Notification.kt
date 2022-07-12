package com.example.listbuzz.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.listbuzz.R
import com.example.listbuzz.data.Priority
import com.example.listbuzz.utiles.utiles.MY_CHANNEL_ID
import com.example.listbuzz.utiles.utiles.MY_CHANNEL_NAME
import kotlin.random.Random

class Notification {
    fun showNotification(
        context: Context,
        title:String?,
        priority: Priority?,
        todotimeanddate:String){

        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.completedTodoFragment)
            .createPendingIntent()

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val notificationBuilder = NotificationCompat.Builder(context,MY_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("ToDo Name :- ${title?.uppercase()}")
        .setContentText(" Importance :- ${priority?.name}  ToDo Time :- $todotimeanddate ")
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setSound(defaultSoundUri)
        .setContentIntent(pendingIntent)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .setCategory(NotificationCompat.CATEGORY_ALARM)

        val notificationManger =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //since  android oreo notification channel is needed

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                MY_CHANNEL_ID,
                MY_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManger.createNotificationChannel(channel)
        }
        val notificationId = Random.nextInt()
        Log.d("ToDolog","$notificationId")
        notificationManger.notify(notificationId, notificationBuilder.build())

    }
}