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
data class OperationUi(
    val expression: String,
    val result: Double,
    val timestamp: Long
) : Parcelable {
    override fun toString(): String {
        return "$expression=$result))"
    }
}