package com.example.androidcalculator

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidcalculator.databinding.ActivityMainBinding
import com.example.androidcalculator.databinding.FragmentCalculatorBinding
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorFragment : Fragment() {
    private lateinit var binding: FragmentCalculatorBinding
    private lateinit var viewModel: CalculatorViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.calculator)
        val view = inflater.inflate(
            R.layout.fragment_calculator, container, false
        )
        binding = FragmentCalculatorBinding.bind(view)
        viewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)
        binding.textVisor.text = viewModel.getDisplayValue()
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.button1.setOnClickListener { binding.textVisor.text = viewModel.onClickSymbol("1") }

        binding.button2.setOnClickListener { binding.textVisor.text = viewModel.onClickSymbol("2") }

        binding.button3.setOnClickListener { binding.textVisor.text = viewModel.onClickSymbol("3") }

        binding.button4.setOnClickListener { binding.textVisor.text = viewModel.onClickSymbol("4") }

        binding.button5.setOnClickListener { binding.textVisor.text = viewModel.onClickSymbol("5") }

        binding.button6.setOnClickListener { binding.textVisor.text = viewModel.onClickSymbol("6") }

        binding.button7.setOnClickListener { binding.textVisor.text = viewModel.onClickSymbol("7") }

        binding.button8.setOnClickListener { binding.textVisor.text = viewModel.onClickSymbol("8") }

        binding.button9.setOnClickListener { binding.textVisor.text = viewModel.onClickSymbol("9") }

        binding.buttonAdition.setOnClickListener {
            binding.textVisor.text = viewModel.onClickSymbol("+")
        }

        binding.buttonSubtraction.setOnClickListener {
            binding.textVisor.text = viewModel.onClickSymbol("-")
        }

        binding.buttonMultiplication?.setOnClickListener {
            binding.textVisor.text = viewModel.onClickSymbol("*")
        }

        binding.buttonFloat.setOnClickListener {
            binding.textVisor.text = viewModel.onClickSymbol(".")
        }

        binding.buttonDouble0?.setOnClickListener {
            binding.textVisor.text = viewModel.onClickSymbol("00")
        }

        binding.buttonNumberSign.setOnClickListener {
            binding.textVisor.text = viewModel.onClickSymbol("#")
        }

        binding.buttonDivision?.setOnClickListener {
            binding.textVisor.text = viewModel.onClickSymbol("/")
        }

        binding.buttonEquals.setOnClickListener {
            binding.textVisor.text = viewModel.onClickEquals()
        }

        binding.buttonC.setOnClickListener {
            binding.textVisor.text = viewModel.onClickClear()
        }
    }
}