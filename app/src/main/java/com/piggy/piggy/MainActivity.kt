package com.piggy.piggy

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = dummyTransactions()
        val transactionsAdapter = TransactionsRecyclerViewAdapter(data)

        val recyclerView: RecyclerView = findViewById(R.id.transactions_rv)
        recyclerView.adapter = transactionsAdapter

    }

    private fun dummyTransactions(): List<Transaction> {
        val transactions = mutableListOf<Transaction>()
        for (i in 1..20) {
            transactions.add(Transaction(
                "Transaction $i",
                Random.nextInt(-1000, 1000),
                TransactionType.values().toList().shuffled().first())
            )
        }
        return transactions
    }

}