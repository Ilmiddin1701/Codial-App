package com.ilmiddin1701.codial_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.ilmiddin1701.codial_app.R
import com.ilmiddin1701.codial_app.models.Spinner

class SpinnerAdapter(private var list: ArrayList<Spinner>) : BaseAdapter() {
    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView: View
        if (convertView == null) {
            itemView = LayoutInflater.from(parent?.context).inflate(R.layout.spinner_item_1, parent, false)
        }else{
            itemView = convertView
        }

        itemView.findViewById<TextView>(R.id.text1).text = list[position].name

        return itemView
    }
}