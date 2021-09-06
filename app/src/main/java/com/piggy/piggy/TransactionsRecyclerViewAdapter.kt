package com.piggy.piggy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class TransactionsRecyclerViewAdapter(private val data: List<Transaction>):
    RecyclerView.Adapter<TransactionsRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val transactionAmountTV: TextView = view.findViewById(R.id.transaction_amount)
        val transactionTitleTV: TextView = view.findViewById(R.id.transaction_title)
        val transactionIconIV: ImageView = view.findViewById(R.id.transaction_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = data[position]
        holder.transactionTitleTV.text =  transaction.title
        holder.transactionAmountTV.text =  transaction.amount.toString()

        val resource = when (transaction.type) {
            TransactionType.CARD -> R.drawable.ic_card
            TransactionType.CASH -> R.drawable.ic_cash
            TransactionType.PTRANSPORT -> R.drawable.ic_ptransport
            TransactionType.CAR -> R.drawable.ic_car
        }

        holder.transactionIconIV.setImageResource(resource)
    }

    override fun getItemCount() = data.size
}