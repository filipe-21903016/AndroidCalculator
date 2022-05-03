package com.example.androidcalculator

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "operation")
data class OperationRoom(
    @PrimaryKey val uuid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "expression") val expression: String,
    val result: Double,
    val timestamp: Long
) {
    @SuppressLint("SimpleDateFormat")
    fun getOperationDatetime(): String {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy - HH:mm:ss")
        return simpleDateFormat.format(timestamp)
    }
}
