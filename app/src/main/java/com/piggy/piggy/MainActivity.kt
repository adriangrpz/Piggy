package com.piggy.piggy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.RealmChangeListener
import io.realm.RealmResults
import io.realm.kotlin.where

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val fab1 = findViewById<FloatingActionButton>(R.id.fab_add_1)
        val fab2 = findViewById<FloatingActionButton>(R.id.fab_add_2)

        val fabText1 = findViewById<TextView>(R.id.fab_text_1)
        val fabText2 = findViewById<TextView>(R.id.fab_text_2)

        val fabOpenAnim        = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        val fabCloseAnim       = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        val fabRotateOpenAnim  = AnimationUtils.loadAnimation(this, R.anim.fab_rotate_open)
        val fabRotateCloseAnim = AnimationUtils.loadAnimation(this, R.anim.fab_rotate_close)

        var fabIsOpen = false

        fab.setOnClickListener {
            if (fabIsOpen) {
                fabIsOpen = false

                fab1.startAnimation(fabCloseAnim)
                fab2.startAnimation(fabCloseAnim)

                fabText1.startAnimation(fabCloseAnim)
                fabText2.startAnimation(fabCloseAnim)

                fab.startAnimation(fabRotateCloseAnim)

                fab1.isClickable = false
                fab2.isClickable = false

                fabText1.visibility = View.INVISIBLE
                fabText2.visibility = View.INVISIBLE
            }
            else {
                fabIsOpen = true

                fab1.startAnimation(fabOpenAnim)
                fab2.startAnimation(fabOpenAnim)

                fabText1.startAnimation(fabOpenAnim)
                fabText2.startAnimation(fabOpenAnim)

                fab.startAnimation(fabRotateOpenAnim)

                fab1.isClickable = false
                fab2.isClickable = false

                fabText1.visibility = View.VISIBLE
                fabText2.visibility = View.VISIBLE
            }

        }

        val transactions : RealmResults<Transaction> = realmThread.where<Transaction>().findAllAsync()
        transactions.addChangeListener(RealmChangeListener {

            val transactionsAdapter = TransactionsRecyclerViewAdapter(transactions.toList())
            val recyclerView: RecyclerView = findViewById(R.id.transactions_rv)
            recyclerView.adapter = transactionsAdapter
        })
    }

}