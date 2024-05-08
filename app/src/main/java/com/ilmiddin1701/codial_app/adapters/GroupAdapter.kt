package com.ilmiddin1701.codial_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilmiddin1701.codial_app.databinding.ItemKurslarBinding
import com.ilmiddin1701.codial_app.models.CourseData

class GroupAdapter(var rvAction2: RvAction2, var list: ArrayList<CourseData>): RecyclerView.Adapter<GroupAdapter.Vh>() {

    inner class Vh(var itemKurslarBinding: ItemKurslarBinding): RecyclerView.ViewHolder(itemKurslarBinding.root){

        fun onBind(courseData: CourseData, position: Int){
            itemKurslarBinding.name.text = courseData.name
            itemKurslarBinding.root.setOnClickListener {
                rvAction2.itemClick(courseData, position)
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

    interface RvAction2{
        fun itemClick(courseData: CourseData, position: Int)
    }
}