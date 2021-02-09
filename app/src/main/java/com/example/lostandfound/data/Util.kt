package com.example.lostandfound.data

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

abstract class Util {
    @SuppressLint("SimpleDateFormat")
    fun getDate(): String {
        val date = Date()
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        return sdf.format(date)
    }

    companion object {
        @SuppressLint("SimpleDateFormat")
        fun getDateAndTime(): String {
            val date = Date()
            val sdf = SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss")
            return sdf.format(date)
        }
    }
}