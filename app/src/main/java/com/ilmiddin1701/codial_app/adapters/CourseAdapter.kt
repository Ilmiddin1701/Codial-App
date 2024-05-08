package com.ilmiddin1701.codial_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ilmiddin1701.codial_app.databinding.ItemKurslarBinding
import com.ilmiddin1701.codial_app.models.CourseData

class CourseAdapter(var rvAction1: RvAction1, var list: ArrayList<CourseData>): Adapter<CourseAdapter.Vh>() {

    inner class Vh(var itemKurslarBinding: ItemKurslarBinding): ViewHolder(itemKurslarBinding.root){

        fun onBind(courseData: CourseData, position: Int){
            itemKurslarBinding.name.text = courseData.name
            itemKurslarBinding.root.setOnClickListener {
                rvAction1.itemClick(courseData, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemKurslarBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    interface RvAction1{
        fun itemClick(courseData: CourseData, position: Int)
    }
}