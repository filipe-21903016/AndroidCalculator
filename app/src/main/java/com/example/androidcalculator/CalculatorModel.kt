package com.example.androidcalculator

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.ExpressionBuilder
import java.util.*

class CalculatorModel(private val dao: OperationDao) {
    var display: String = "0"
        private set
    private val TAG = MainActivity::class.java.simpleName

    fun insertSymbol(symbol: String): String {
        display = if (display == "0") {
            symbol
        } else {
            "$display$symbol"
        }
        Log.i(TAG, "CalculatorModel inserts symbol $symbol")
        return display
    }

    fun performOperation(onFinished: () -> Unit ){
        val expressionBuilder = ExpressionBuilder(display).build()
        val result = expressionBuilder.evaluate()
        Log.i(TAG, "CalculatorModel performs operation -> $display=$result")
        val operation = OperationRoom(
            expression =  display, result = result, timestamp = Date().time
        )
        display = result.toString()
        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(operation)
            onFinished()
        }
    }

    fun getAllOperations(onFinished: (List<OperationUi>) -> Unit) {
        Log.i(TAG, "CalculatorModel getting all operations")
        CoroutineScope(Dispatchers.IO).launch {
            val operations = dao.getAll()
            onFinished(operations.map { OperationUi(it.uuid, it.expression, it.result, it.timestamp) })
        }
    }

    fun clearDisplay(): String {
        display = "0"
        Log.i(TAG, "CalculatorModel cleared display")
        return display
    }


    fun deleteOperation(uuid: String, onSuccess: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteById(uuid)
            onSuccess()
        }
    }
}