package com.ilmiddin1701.codial_app.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilmiddin1701.codial_app.databinding.ItemMentorBinding
import com.ilmiddin1701.codial_app.models.MentorData

class MentorAdapter(var rvAction3: RvAction3, var list: ArrayList<MentorData>): RecyclerView.Adapter<MentorAdapter.Vh>() {

    inner class Vh(var itemMentorBinding: ItemMentorBinding): RecyclerView.ViewHolder(itemMentorBinding.root){
        @SuppressLint("SetTextI18n")
        fun onBind(mentorData: MentorData, position: Int){
            itemMentorBinding.tvName.text = "${mentorData.firstName} ${mentorData.lastName}"
            itemMentorBinding.btnEdit.setOnClickListener {
                rvAction3.editClick(mentorData, position)
            }
            itemMentorBinding.btnDelete.setOnClickListener {
                rvAction3.deleteClick(mentorData, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemMentorBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    interface RvAction3{
        fun editClick(mentorData: MentorData, position: Int)
        fun deleteClick(mentorData: MentorData, position: Int)
    }
}