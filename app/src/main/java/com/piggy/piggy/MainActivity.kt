package com.piggy.piggy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.RealmChangeListener
import io.realm.RealmResults
import io.realm.kotlin.where

class MainActivity : AppCompatActivity() {

    private lateinit var fab: FloatingActionButton
    private lateinit var fab1: FloatingActionButton
    private lateinit var fab2: FloatingActionButton

    private lateinit var fabText1: TextView
    private lateinit var fabText2: TextView

    private lateinit var fabOpenAnim: Animation
    private lateinit var fabCloseAnim: Animation
    private lateinit var fabRotateOpenAnim: Animation
    private lateinit var fabRotateCloseAnim: Animation

    private var fabIsOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isFirstLaunch = sharedPref.getInt("FIRST_LAUNCH", 0)
        if (isFirstLaunch == 0) {
            with (sharedPref.edit()) {
                putInt("FIRST_LAUNCH", 1)
                apply()
            }

            populateDatabase()
        }

        fab  = findViewById(R.id.fab)
        fab1 = findViewById(R.id.fab_add_1)
        fab2 = findViewById(R.id.fab_add_2)

        fabText1 = findViewById(R.id.fab_text_1)
        fabText2 = findViewById(R.id.fab_text_2)

        fabOpenAnim = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fabCloseAnim = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        fabRotateOpenAnim = AnimationUtils.loadAnimation(this, R.anim.fab_rotate_open)
        fabRotateCloseAnim = AnimationUtils.loadAnimation(this, R.anim.fab_rotate_close)

        fab.setOnClickListener {
            if (fabIsOpen) closeFabs() else openFabs()
        }

        fab1.setOnClickListener {
            closeFabs()
            startActivity(Intent(this, AddIncomeActivity::class.java))
        }

        fab2.setOnClickListener {
            closeFabs()
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        val transactions : RealmResults<Transaction> = realmThread.where<Transaction>().findAllAsync()
        transactions.addChangeListener(RealmChangeListener {

            Log.v(TAG(), transactions.asJSON())
            val transactionsAdapter = TransactionsRecyclerViewAdapter(transactions.toList())
            val recyclerView: RecyclerView = findViewById(R.id.transactions_rv)
            recyclerView.adapter = transactionsAdapter
        })

    }

    private fun openFabs() {
        fabIsOpen = true

        fab1.startAnimation(fabOpenAnim)
        fab2.startAnimation(fabOpenAnim)

        fabText1.startAnimation(fabOpenAnim)
        fabText2.startAnimation(fabOpenAnim)

        fab.startAnimation(fabRotateOpenAnim)

        fab1.isClickable = true
        fab2.isClickable = true

        fab1.visibility     = View.VISIBLE
        fab2.visibility     = View.VISIBLE
        fabText1.visibility = View.VISIBLE
        fabText2.visibility = View.VISIBLE
    }

    private fun closeFabs() {
        fabIsOpen = false

        fab1.startAnimation(fabCloseAnim)
        fab2.startAnimation(fabCloseAnim)

        fabText1.startAnimation(fabCloseAnim)
        fabText2.startAnimation(fabCloseAnim)

        fab.startAnimation(fabRotateCloseAnim)

        fab1.isClickable = false
        fab2.isClickable = false

        fab1.visibility     = View.INVISIBLE
        fab2.visibility     = View.INVISIBLE
        fabText1.visibility = View.INVISIBLE
        fabText2.visibility = View.INVISIBLE
    }

    private fun populateDatabase() {
//        Initial data
    }

}