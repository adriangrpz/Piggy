package com.piggy.piggy

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDateTime
import java.util.TimeZone
import java.time.format.DateTimeFormatter

class TransactionsExpandableListAdapter(private var context: Context,
                                        private var months: List<String>,
                                        private var collection: Map<String, List<Transaction>>):
    BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return months.size
    }

    override fun getChildrenCount(p0: Int): Int {
        return this.collection[this.months[p0]]!!.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return months[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return this.collection[this.months[groupPosition]]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var newView: View? = convertView
        val monthTitle = getGroup(groupPosition) as String

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            newView = inflater.inflate(R.layout.month_group, null)
        }

        val monthTitleTV = newView!!.findViewById<TextView>(R.id.month_title)
        monthTitleTV.text = monthTitle

        return newView
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        var newView = convertView
        val transaction = getChild(groupPosition, childPosition) as Transaction
        val transactionTitle  = transaction.title
        val transactionAmount = transaction.amount
        val transactionDate = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(transaction.createdAt()),
            TimeZone.getDefault().toZoneId()
        )

        if (newView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            newView = inflater.inflate(R.layout.transaction_item, null)
        }

        val transactionTitleTV  = newView!!.findViewById<TextView>(R.id.transaction_title)
        val transactionAmountTV = newView.findViewById<TextView>(R.id.transaction_amount)
        val transactionDateTV   = newView.findViewById<TextView>(R.id.transaction_date)
        val transactionIconIV   = newView.findViewById<ImageView>(R.id.transaction_icon)

        val resource = when (transaction.category) {
            TransactionCategory.CARD -> R.drawable.ic_card
            TransactionCategory.CASH -> R.drawable.ic_cash
            TransactionCategory.PTRANSPORT -> R.drawable.ic_ptransport
            TransactionCategory.CAR -> R.drawable.ic_car
        }

        val formatter  = DateTimeFormatter.ofPattern("dd MMM, hh:mm a")
        val date       = transactionDate.format(formatter)

        transactionTitleTV.text  = transactionTitle
        transactionDateTV.text   = date
        transactionIconIV.setImageResource(resource)

        val nf: NumberFormat = NumberFormat.getInstance()
        val transactionAmountString = nf.format(transactionAmount)

        if (transaction.type == TransactionType.INCOME) {
            transactionAmountTV.text = context.getString(R.string.pos_amount_template, transactionAmountString)
            transactionAmountTV.setTextColor(Color.parseColor("#FEC260"))
        }
        else {
            transactionAmountTV.text = context.getString(R.string.neg_amount_template, transactionAmountString)
            transactionAmountTV.setTextColor(Color.parseColor("#C4C4C4"))
        }

        return newView
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }

}