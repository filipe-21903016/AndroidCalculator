package com.example.androidcalculator

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorModel {
    var display: String = "0"
    val history = mutableListOf<Operation>()
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

    fun performOperation(): Double {
        val expressionBuilder = ExpressionBuilder(display).build()
        val expression = display
        val result = expressionBuilder.evaluate()
        Log.i(TAG, "CalculatorModel performs operation -> $expression=$result")
        CoroutineScope(Dispatchers.IO).launch {
            addToHistory(expression, result)
        }
        display = result.toString()
        return result
    }

    suspend fun addToHistory(expression: String, result: Double){
        Thread.sleep(30 * 1000)
        history.add(Operation(expression, result))
        Log.i(TAG, "CalculatorModel added $expression=$result to history")
    }

    fun getAllOperations(callback: (List<Operation>) -> Unit) {
        Log.i(TAG, "CalculatorModel getting all operations")
        CoroutineScope(Dispatchers.IO).launch {
            Thread.sleep(30 * 1000)
            callback(history.toList())
        }
    }

    fun clearDisplay(): String {
        display = "0"
        Log.i(TAG, "CalculatorModel cleared display")
        return display
    }
}