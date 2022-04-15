package com.example.androidcalculator

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import kotlin.math.round

@Parcelize
class OperationUi(
    val expression: String,
    val result: String,
    val timestamp: Long = System.currentTimeMillis()
) : Parcelable {
    override fun toString(): String {
        return "$expression=$result))"
    }

    @SuppressLint("SimpleDateFormat")
    fun getOperationDatetime(): String {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy - HH:mm:ss")
        return simpleDateFormat.format(timestamp)
    }
}