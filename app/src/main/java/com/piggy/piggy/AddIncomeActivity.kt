package com.piggy.piggy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import io.realm.Realm

class AddIncomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_income)

        val titleET        = findViewById<TextInputEditText>(R.id.title_et)
        val amountET       = findViewById<TextInputEditText>(R.id.amount_et)
        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.type_atv)
        val addButton      = findViewById<MaterialButton>(R.id.add)

        val items = mutableListOf<String>()
        TransactionType.values().forEach {
            val name = when (it) {
                TransactionType.CARD -> "Card"
                TransactionType.CASH -> "Cash"
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

            val type = when (autocompleteTV.text.toString()) {
                "Card" -> TransactionType.CARD
                "Cash" -> TransactionType.CASH
                else   -> null
            }

            if (type == null)
                Log.v(TAG(), "Null found in transaction type")
            else {
                newTransaction.type = type
            }

            realmThread.executeTransactionAsync(
                { transaction ->
                    transaction.insert(newTransaction)
                },
                Realm.Transaction.OnSuccess {
                    finish()
                }
            )

        }
    }


}