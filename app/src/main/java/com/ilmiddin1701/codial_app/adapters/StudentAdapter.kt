package com.ilmiddin1701.codial_app.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ilmiddin1701.codial_app.databinding.ItemStudentBinding
import com.ilmiddin1701.codial_app.models.StudentData

class StudentAdapter(var rvAction6: RvAction6, var list: ArrayList<StudentData>): Adapter<StudentAdapter.Vh>() {

    inner class Vh(var itemStudentBinding: ItemStudentBinding): ViewHolder(itemStudentBinding.root){
        @SuppressLint("SetTextI18n")
        fun onBind(studentData: StudentData, position: Int){
            itemStudentBinding.btnCall.setOnClickListener {
                rvAction6.callClick(studentData)
            }
            itemStudentBinding.btnEdit.setOnClickListener {
                rvAction6.editClick(studentData, position)
            }
            itemStudentBinding.btnDelete.setOnClickListener {
                rvAction6.deleteClick(studentData, position)
            }
            itemStudentBinding.tvName.text = "${studentData.firstName} ${studentData.lastName}"
            itemStudentBinding.tvDate.text = studentData.day
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    interface RvAction6 {
        fun callClick(studentData: StudentData)
        fun editClick(studentData: StudentData, position: Int)
        fun deleteClick(studentData: StudentData, position: Int)
    }
}