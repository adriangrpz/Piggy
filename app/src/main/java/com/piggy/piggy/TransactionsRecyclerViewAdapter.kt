package com.piggy.piggy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionsRecyclerViewAdapter(private val data: List<Transaction>):
    RecyclerView.Adapter<TransactionsRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val transactionAmountTV: TextView
        val transactiontitleTV: TextView

        init {
            transactionAmountTV = view.findViewById(R.id.transaction_amount)
            transactiontitleTV = view.findViewById(R.id.transaction_title)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.transactiontitleTV.text =  data[position].title
        holder.transactionAmountTV.text =  data[position].amount.toString()
    }

    override fun getItemCount() = data.size
}