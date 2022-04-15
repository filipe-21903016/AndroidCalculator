package com.example.androidcalculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


private const val ARG_OPERATION = "param1"

class OperationDetailFragment : Fragment() {
    private var operationUi: OperationUi? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            operationUi = it.getParcelable(ARG_OPERATION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_operation_detail, container, false)
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