package com.piggy.piggy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmChangeListener
import io.realm.RealmResults
import io.realm.kotlin.where

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.v(TAG(), "Fetching transactions")

        val transactions : RealmResults<Transaction> = realmThread.where<Transaction>().findAllAsync()
        transactions.addChangeListener(RealmChangeListener {
            Log.v(TAG(), "Completed the query.")

            Log.v(TAG(), transactions.asJSON())

            val transactionsAdapter = TransactionsRecyclerViewAdapter(transactions.toList())
            val recyclerView: RecyclerView = findViewById(R.id.transactions_rv)
            recyclerView.adapter = transactionsAdapter
            Log.v(TAG(), "Populated recycler view")
        })
    }

}