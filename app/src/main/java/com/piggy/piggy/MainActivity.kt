package com.piggy.piggy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ExpandableListView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.RealmChangeListener
import io.realm.RealmResults
import io.realm.kotlin.where
import java.text.NumberFormat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var balanceText : TextView
    private lateinit var earningsText: TextView
    private lateinit var expensesText: TextView

    private lateinit var fab : FloatingActionButton
    private lateinit var fab1: FloatingActionButton
    private lateinit var fab2: FloatingActionButton

    private lateinit var fabText1: TextView
    private lateinit var fabText2: TextView

    private lateinit var fabOpenAnim       : Animation
    private lateinit var fabCloseAnim      : Animation
    private lateinit var fabRotateOpenAnim : Animation
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

        balanceText  = findViewById(R.id.balance_amount_tv)
        earningsText = findViewById(R.id.earnings_amount_tv)
        expensesText = findViewById(R.id.expenses_amount_tv)

        fab  = findViewById(R.id.fab)
        fab1 = findViewById(R.id.fab_add_1)
        fab2 = findViewById(R.id.fab_add_2)

        fabText1 = findViewById(R.id.fab_text_1)
        fabText2 = findViewById(R.id.fab_text_2)

        fabOpenAnim        = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fabCloseAnim       = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        fabRotateOpenAnim  = AnimationUtils.loadAnimation(this, R.anim.fab_rotate_open)
        fabRotateCloseAnim = AnimationUtils.loadAnimation(this, R.anim.fab_rotate_close)

        fab.setOnClickListener { if (fabIsOpen) closeFabs() else openFabs() }

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

            var transactionCollection = mutableMapOf<String, List<Transaction>>()

            var months = listOf("January", "February", "March")
            var transactions = transactions.toList()

            transactionCollection["January"] = transactions
            transactionCollection["February"] = transactions
            transactionCollection["March"] = transactions

            val transactionsAdapter = TransactionsExpandableListAdapter(this, months, transactionCollection)
            val expandableLV : ExpandableListView = findViewById(R.id.expandable_lv)
            expandableLV.setAdapter(transactionsAdapter)

            var totalEarnings = 0
            var totalExpenses = 0
            for (transaction in transactions.toList()) {
                if (transaction.type == TransactionType.INCOME)
                    totalEarnings += transaction.amount
                else
                    totalExpenses += transaction.amount
            }

            val nf: NumberFormat = NumberFormat.getInstance()

            val balanceString  = nf.format(totalEarnings - totalExpenses)
            val earningsString = nf.format(totalEarnings)
            val expensesString = nf.format(totalExpenses)

            balanceText.text  = getString(R.string.amount_template, balanceString)
            earningsText.text = getString(R.string.amount_template, earningsString)
            expensesText.text = getString(R.string.amount_template, expensesString)
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
        for (i in 1..10) {
            val newTransaction = Transaction()
            newTransaction.title = "Transaction $i"
            newTransaction.amount = Random.nextInt(3000) + 1
            if (i % 2 == 0)
                newTransaction.typeDescription = TransactionType.INCOME.name
            else
                newTransaction.typeDescription = TransactionType.EXPENSE.name

            realmThread.executeTransactionAsync { transaction ->
                transaction.insert(newTransaction)
            }
        }
    }

}