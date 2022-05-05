package com.example.androidcalculator

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import java.lang.Exception
import java.util.*

class CalculatorRetrofit(retrofit: Retrofit) : CalculatorModel() {
    private val TAG = CalculatorRetrofit::class.java.simpleName
    private val service = retrofit.create(CalculatorService::class.java)

    override fun performOperation(onFinished: () -> Unit) {
        val currentExpression = expression
        super.performOperation {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val body =
                        PostOperationRequest(currentExpression, expression.toDouble(), Date().time)
                    val result = service.insert(body)
                    Log.i(TAG, result.toString())
                } catch (e: HttpException) {
                    Log.e(TAG, e.message())
                }
            }
            onFinished()
        }
    }

    override fun insertOperations(
        operations: List<OperationUi>,
        onFinished: (List<OperationUi>) -> Unit
    ) {
        throw Exception("Not implemented on web service")
    }

    override fun getLastOperation(onFinished: (String) -> Unit) {
        getHistory { history ->
            onFinished(history.sortedByDescending { it.timestamp }.first().expression)
        }
    }

    override fun deleteOperation(uuid: String, onSuccess: () -> Unit) {
        throw Exception("Not implemented on web service")
    }

    override fun deleteAllOperations(onFinished: () -> Unit) {
        throw Exception("Not implemented on web service")
    }

    override fun getHistory(onFinished: (List<OperationUi>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val operations = service.getAll()
                onFinished(operations.map { OperationUi(it.uuid, it.expression, it.result, it.timestamp) })
            } catch (e: HttpException)
            {
                Log.e(TAG, e.message())
            }
        }
    }
}