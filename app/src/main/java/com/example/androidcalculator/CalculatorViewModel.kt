package com.example.androidcalculator

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

class CalculatorViewModel(application: Application) : AndroidViewModel(application) {
    //private val model = CalculatorRoom(CalculatorDatabase.getInstance(application).operationDao())
    private val model = CalculatorRetrofit(RetrofitBuilder.getInstance("https://cm-calculadora.herokuapp.com/api/"))
    private val TAG = MainActivity::class.java.simpleName

    fun getDisplayValue() = model.expression

    fun onClickSymbol(symbol: String): String {
        Log.i(TAG, "Click \'$symbol\'")
        return model.insertSymbol(symbol)
    }

    fun onClickEquals(onSaved: ()-> Unit ):String {
        Log.i(TAG, "Click \'=\'")
        model.performOperation(onSaved)
        var result = getDisplayValue().toDouble()
        return if(result % 1 == 0.0) result.toLong().toString() else result.toString()
    }

    fun onClickClear(): String {
        Log.i(TAG, "Click \'C\'")
        return model.clearDisplay()
    }

    fun onDeleteOperation(uuid: String, onSuccess: () -> Unit) {
        model.deleteOperation(uuid, onSuccess)
    }

    fun onClickGetLastOperation(onFinished: (String) -> Unit){
        model.getLastOperation(onFinished)
    }

    fun onGetHistory(onFinished: (List<OperationUi>) -> Unit){
        model.getHistory(onFinished)
    }

    private fun getAllOperationsWs(callback: (List<OperationUi>) -> Unit) {
        data class GetAllOperationsResponse(
            val uuid: String,
            val expression: String,
            val result: Double,
            val timestamp: Long
        )
        CoroutineScope(Dispatchers.IO).launch {
            val request: Request = Request.Builder()
                .url("https://cm-calculadora.herokuapp.com/api/operations")
                .addHeader(
                    "apiKey",
                    "8270435acfead39ccb03e8aafbf37c49359dfbbcac4ef4769ae82c9531da0e17"
                )
                .build()
            val response = OkHttpClient().newCall(request).execute().body
            if (response != null) {
                val responseObj =
                    Gson().fromJson(response.string(), Array<GetAllOperationsResponse>::class.java)
                        .toList()
                callback(responseObj.map {
                    OperationUi(
                        uuid = it.uuid,
                        expression = it.expression,
                        result = it.result,
                        timestamp = it.timestamp
                    )
                })
            }
        }
    }
}