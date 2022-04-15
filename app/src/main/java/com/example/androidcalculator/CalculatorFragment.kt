package com.example.androidcalculator

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidcalculator.databinding.ActivityMainBinding
import com.example.androidcalculator.databinding.FragmentCalculatorBinding
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorFragment : Fragment() {
    private lateinit var binding: FragmentCalculatorBinding
    private val TAG = MainActivity::class.java.simpleName
    private val operations = mutableListOf<OperationUi>()
    private val adapter = HistoryAdapter(::onOperationClick)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.fragment_calculator, container, false
        )
        binding = FragmentCalculatorBinding.bind(view)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.button1.setOnClickListener { onClickSymbol("1") }

        binding.button2.setOnClickListener { onClickSymbol("2") }

        binding.button3.setOnClickListener { onClickSymbol("3") }

        binding.button4.setOnClickListener { onClickSymbol("4") }

        binding.button5.setOnClickListener { onClickSymbol("5") }

        binding.button6.setOnClickListener { onClickSymbol("6") }

        binding.button7.setOnClickListener { onClickSymbol("7") }

        binding.button8.setOnClickListener { onClickSymbol("8") }

        binding.button9.setOnClickListener { onClickSymbol("9") }

        binding.buttonAdition.setOnClickListener { onClickOp("+") }

        binding.buttonSubtraction.setOnClickListener { onClickOp("-") }

        binding.buttonMultiplication?.setOnClickListener { onClickOp("*") }

        binding.buttonFloat.setOnClickListener { onClickOp(".") }

        binding.buttonDouble0?.setOnClickListener { onClickOp("00") }

        binding.buttonNumberSign.setOnClickListener { onClickOp("#") }

        binding.buttonDivision?.setOnClickListener { onClickOp("/") }

        binding.buttonEquals.setOnClickListener { onClickEquals() }

        binding.buttonC.setOnClickListener {
            Log.i(TAG, "Click no botão +")
            binding.textVisor.text = ""
        }

        binding.rvHistoric?.layoutManager = LinearLayoutManager(activity as Context)
        binding.rvHistoric?.adapter = adapter
    }


    fun onClickSymbol(symbol: String) {
        Log.i(TAG, "Click no botão $symbol")
        if (binding.textVisor.text == "0") {
            binding.textVisor.text = symbol
        } else {
            binding.textVisor.append(symbol)
        }
    }

    fun onClickOp(symbol: String) {
        Log.i(TAG, "Click no botão $symbol")
        binding.textVisor.append(symbol)
    }

    fun onClickEquals() {
        Log.i(TAG, "Click no botão =")
        val expression = binding.textVisor.text.toString()
        val expressionBuilder = ExpressionBuilder(
            expression
        ).build()
        val result = expressionBuilder.evaluate().toString()
        binding.textVisor.text = result
        operations.add(
            OperationUi(
                expression = expression,
                result = result
            )
        )
        adapter.updateItems(operations)
        Log.i(TAG, "O resultado é $result")
    }

    private fun onOperationClick(message: String) {
        Toast.makeText(activity as Context, message, Toast.LENGTH_LONG).show()
    }
}