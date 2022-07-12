package com.example.listbuzz.utiles

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.lifecycle.Observer
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*


    fun Intent.putParcelableExtra(key:String,value:Parcelable){
        putExtra(key,value)
    }
    fun TextInputEditText.transformDatePicker(
        context: Context,
        format:String,
        maxDate: Date?=null
    ){
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false

        val myCalendar = Calendar.getInstance()

        val datePickerSetListener =
            DatePickerDialog.OnDateSetListener{ _,year,month,dayOfMonth ->
            myCalendar.set(Calendar.YEAR,year)
            myCalendar.set(Calendar.MONTH,month)
            myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            val sdf =  SimpleDateFormat(format,Locale.UK)
            setText(sdf.format(myCalendar.time))
        }
        setOnClickListener{
            DatePickerDialog(
                context,datePickerSetListener,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).run {
                maxDate?.time?.also { datePicker.minDate = it }
                show()
            }
        }
    }


    //for time
    fun TextInputEditText.transformTimePicker(
        context: Context,
        format:String,
        ){
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false

        val myCalendar = Calendar.getInstance()
        val timePickSetListener = TimePickerDialog.OnTimeSetListener{_,hourOfDay,minute->
            myCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay)
            myCalendar.set(Calendar.MINUTE,minute)

            val selectTime =  SimpleDateFormat(format,Locale.ENGLISH)
            setText(selectTime.format(myCalendar.time))
        }
        setOnClickListener{
            TimePickerDialog(
                context,timePickSetListener,myCalendar.get(Calendar.HOUR_OF_DAY),myCalendar.get(Calendar.MINUTE),false
            ).show()
            }
        }

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner,  observer: Observer<T>){
    observe(lifecycleOwner, object : Observer<T>{
        override fun onChanged(t: T) {
            observer.onChanged(t)
            removeObserver(this)
        }

    })

}


