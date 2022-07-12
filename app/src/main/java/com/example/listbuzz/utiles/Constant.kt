package com.example.listbuzz.utiles

import android.graphics.Color.red
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import com.example.listbuzz.R
import com.example.listbuzz.data.Priority


object utiles{
    val priority = listOf("High", "Medium", "Low")

        const val ACTION_SET_EXACT = "package com.example.listbuzz.receiver.AlarmReceiver"
        const val MY_CHANNEL_ID = "To Do App Notification Channel Id"
        const val MY_CHANNEL_NAME = "ToDo App notification"


        fun selectPriority(priority: String): Priority {
            return when (priority) {
                "High" -> {
                    Priority.High
                }
                "Medium" -> {
                    Priority.Medium
                }
                "Low" -> {
                    Priority.Low
                }
                else -> Priority.Low
            }
        }

        @RequiresApi(Build.VERSION_CODES.M)
            fun backgroundColor(cardView: CardView, priority: Priority) {
                when (priority) {
                    Priority.High -> {
                        cardView.setCardBackgroundColor(cardView.context.getColor(R.color.red))
                    }
                    Priority.Medium -> {
                        cardView.setCardBackgroundColor(cardView.context.getColor(R.color.yellow))
                    }
                    Priority.Low -> {
                        cardView.setCardBackgroundColor(cardView.context.getColor(R.color.green))
                    }
                }
            }
        }

