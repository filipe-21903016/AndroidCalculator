package com.example.androidcalculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.media.VolumeShaper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidcalculator.databinding.FragmentCalculatorBinding
import com.example.androidcalculator.databinding.FragmentHistoryBinding

private const val ARG_OPERATIONS = "operations"

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private var operations: ArrayList<OperationUi>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            operations = it.getParcelableArrayList(ARG_OPERATIONS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_history, container, false)
        binding = FragmentHistoryBinding.bind(view)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.rvHistoric.layoutManager = LinearLayoutManager(activity as Context)
        binding.rvHistoric.adapter = HistoryAdapter({})
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