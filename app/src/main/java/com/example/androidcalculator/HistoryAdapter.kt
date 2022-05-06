package com.example.androidcalculator

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcalculator.databinding.ActivityMainBinding
import com.example.androidcalculator.databinding.ItemExpressionBinding

class HistoryAdapter(
    private val onClick: (OperationUi) -> Unit,
    private val onLongClick: (OperationUi) -> Boolean,
    private var items: List<OperationUi> = listOf()
) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(val binding: ItemExpressionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            ItemExpressionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            if (items.size > position) onClick(items[position])
        }
        holder.itemView.setOnLongClickListener { onLongClick(items[position]) }

        holder.binding.textExpression.text = items[position].expression
        holder.binding.textResult.text = items[position].result.toString()
        holder.binding.tvUuid.text = "UUID:${items[position].uuid}"

    }

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<OperationUi>) {
        this.items = items
        notifyDataSetChanged()
    }


}
