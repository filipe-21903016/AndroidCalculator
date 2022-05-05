package com.example.androidcalculator

import android.util.Log
import net.objecthunter.exp4j.ExpressionBuilder
import java.util.*

abstract class CalculatorModel {
    var expression: String = "0"
        private set
    private val TAG = MainActivity::class.java.simpleName

    fun clearDisplay(): String {
        expression = "0"
        return expression
    }

    fun insertSymbol(symbol: String): String {
        Log.i(TAG, "CalculatorModel inserts symbol $symbol")
        expression = if (expression == "0") symbol else "$expression$symbol"
        return expression
    }

    open fun performOperation(onFinished: () -> Unit ){
        val expressionBuilder = ExpressionBuilder(expression).build()
        val result = expressionBuilder.evaluate()
        Log.i(TAG, "CalculatorModel performs operation -> $expression=$result")
        /*
        val operation = OperationRoom(
            expression =  display, result = result, timestamp = Date().time
        )
        display = result.toString()
        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(operation)
            onFinished()
        }
        */
        expression = result.toString()
        onFinished()
    }

    abstract fun insertOperations(operations: List<OperationUi>, onFinished: (List<OperationUi>) -> Unit)
    abstract fun getLastOperation(onFinished: (String) -> Unit)
    abstract fun deleteOperation(uuid: String, onSuccess: () -> Unit)
    abstract fun deleteAllOperations(onFinished: () -> Unit)
    abstract fun getHistory(onFinished: (List<OperationUi>) -> Unit)

    /*
    fun getAllOperations(onFinished: (List<OperationUi>) -> Unit) {
        Log.i(TAG, "CalculatorModel getting all operations")
        CoroutineScope(Dispatchers.IO).launch {
            val operations = dao.getAll()
            onFinished(operations.map { OperationUi(it.uuid, it.expression, it.result, it.timestamp) })
        }
    }
     */
}