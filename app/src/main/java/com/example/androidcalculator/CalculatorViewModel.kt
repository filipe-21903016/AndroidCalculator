package com.example.androidcalculator

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalculatorViewModel(application: Application) : AndroidViewModel(application) {
    private val model = CalculatorModel(
        CalculatorDatabase.getInstance(application).operationDao()
    )
    private val TAG = MainActivity::class.java.simpleName

    fun getDisplayValue() = model.display

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

    fun onGetHistory(onFinished: (List<OperationUi>) -> Unit) {
        model.getAllOperations(onFinished)
    }


    fun deleteOperation(uuid: String, onSuccess: () -> Unit) {
        model.deleteOperation(uuid, onSuccess)
    }
}