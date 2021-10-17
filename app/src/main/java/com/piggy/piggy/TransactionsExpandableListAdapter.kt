package com.piggy.piggy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView

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
        var convertView = convertView
        val monthTitle = getGroup(groupPosition) as String

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.month_group, null)
        }

        val monthTitleTV = convertView!!.findViewById<TextView>(R.id.month_title)
        monthTitleTV.text = monthTitle

        return convertView
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        val transactionTitle = (getChild(groupPosition, childPosition) as Transaction).title

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.transaction_item, null)
        }

        val transactiontitleTV = convertView!!.findViewById<TextView>(R.id.transaction_title)
        transactiontitleTV.text = transactionTitle

        return convertView
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }

}