package com.example.androidcalculator

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception

class CalculatorViewModel : ViewModel() {
    private val model = CalculatorRepository.getInstance()
    private val TAG = MainActivity::class.java.simpleName

    fun getDisplayValue() = model.getExpression()

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
        return model.clear()
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
}