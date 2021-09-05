package com.piggy.piggy

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = arrayOf("$135.00", "$420.00", "$10.97")
        val transactionsAdapter = TransactionsRecyclerViewAdapter(data)

        val recyclerView: RecyclerView = findViewById(R.id.transactions_rv)
        recyclerView.adapter = transactionsAdapter

    }


}