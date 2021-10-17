package com.piggy.piggy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import io.realm.Realm

class AddExpenseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        val titleET        = findViewById<TextInputEditText>(R.id.title_et)
        val amountET       = findViewById<TextInputEditText>(R.id.amount_et)
        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.category_atv)
        val addButton      = findViewById<MaterialButton>(R.id.add)

        val items = mutableListOf<String>()
        TransactionCategory.values().forEach {
            val name = when (it) {
                TransactionCategory.CARD -> "Card"
                TransactionCategory.CASH -> "Cash"
                else                 -> ""
            }
            if (name != "")
                items.add(name)
        }

        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        autocompleteTV.setAdapter(adapter)

        addButton.setOnClickListener {

            val newTransaction = Transaction()
            newTransaction.title = titleET.text.toString()
            newTransaction.amount = amountET.text.toString().toInt()
            Log.v(tag(), newTransaction.toString())

            val category = when (autocompleteTV.text.toString()) {
                "Card" -> TransactionCategory.CARD
                "Cash" -> TransactionCategory.CASH
                else   -> null
            }

            if (category == null)
                Log.v(tag(), "Null found in transaction category")
            else {
                newTransaction.category = category
            }

            newTransaction.type = TransactionType.EXPENSE

            realmThread.executeTransactionAsync(
                { transaction ->
                    transaction.insert(newTransaction)
                    Log.v(tag(), "Expense created")
                },
                Realm.Transaction.OnSuccess {
                    finish()
                    Log.v(tag(), "Finishing activity")
                }
            )

        }
    }


}