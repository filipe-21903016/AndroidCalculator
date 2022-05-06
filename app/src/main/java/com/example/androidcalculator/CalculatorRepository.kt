package com.example.androidcalculator

import android.content.Context
import java.lang.IllegalStateException

class CalculatorRepository(
    private val context: Context,
    private val local: CalculatorModel,
    private val remote: CalculatorModel
) {
    fun getExpression() = local.expression

    fun insertSymbol(symbol: String): String {
        return if (ConectivityUtil.isOnline(context)) remote.insertSymbol(symbol)
        else local.insertSymbol(symbol)
    }

    fun clear(): String {
        return if (ConectivityUtil.isOnline(context)) remote.clearDisplay() else local.clearDisplay()
    }

    fun performOperation(onFinished: () -> Unit) {
        if (ConectivityUtil.isOnline(context)) {
            remote.performOperation {
                local.expression = remote.expression
                onFinished()
            }
        } else {
            local.performOperation {
                onFinished()
            }
        }

    }

    fun getLastOperation(onFinished: (String) -> Unit) {
        if (ConectivityUtil.isOnline(context)) {
            remote.getLastOperation(onFinished)
        } else {
            local.getLastOperation(onFinished)
        }
    }

    fun deleteOperation(uuid: String, onSuccess: () -> Unit) {
        if (ConectivityUtil.isOnline(context)) remote.deleteOperation(
            uuid,
            onSuccess
        ) else local.deleteOperation(uuid, onSuccess)
    }

    fun getHistory(onFinished: (List<OperationUi>) -> Unit) {
        if (ConectivityUtil.isOnline(context)) {
            remote.getHistory { history ->
                local.deleteAllOperations {
                    local.insertOperations(history) {
                        onFinished(history)
                    }
                }
            }
        } else {
            local.getHistory(onFinished)
        }
    }

    companion object {
        private var instance: CalculatorRepository? = null

        fun init(context: Context, local: CalculatorModel, remote: CalculatorModel) {
            synchronized(this) {
                if (instance == null) {
                    instance = CalculatorRepository(context, local, remote)
                }
            }
        }

        fun getInstance(): CalculatorRepository {
            return instance ?: throw IllegalStateException("Repository not initialized")
        }
    }
}