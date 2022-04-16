package com.example.androidcalculator

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.androidcalculator.databinding.FragmentHistoryBinding
import com.example.androidcalculator.databinding.FragmentOperationDetailBinding


private const val ARG_OPERATION = "param1"

class OperationDetailFragment : Fragment() {
    private var operationUi: OperationUi? = null
    private lateinit var binding: FragmentOperationDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            operationUi = it.getParcelable(ARG_OPERATION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.operationDetails)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_operation_detail, container, false)
        binding = FragmentOperationDetailBinding.bind(view)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        binding.textExpression.text = operationUi?.expression
        binding.textResult.text = "=${operationUi?.result}"
        //binding.textDatetime.text = operationUi?.getOperationDatetime()
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
        fun newInstance(operationUi: OperationUi) =
            OperationDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_OPERATION, operationUi)
                }
            }
    }
}