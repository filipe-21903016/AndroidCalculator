package com.example.androidcalculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidcalculator.databinding.FragmentHistoryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val ARG_OPERATIONS = "param1"

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var viewModel: CalculatorViewModel
    private var history: List<OperationUi>? = null
    private val adapter = history?.let { HistoryAdapter(parentFragmentManager, it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            history = it.getParcelableArrayList(ARG_OPERATIONS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.history)
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_history, container, false)
        viewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)
        binding = FragmentHistoryBinding.bind(view)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.rvHistoric.layoutManager = LinearLayoutManager(activity as Context)
        viewModel.getHistory { history }
        binding.rvHistoric.adapter = adapter
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onPause() {
        super.onPause()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }

    companion object {
        @JvmStatic
        fun newInstance(operations : ArrayList<OperationUi>) : HistoryFragment{
            return HistoryFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_OPERATIONS, operations)
                }
            }
        }
    }
}