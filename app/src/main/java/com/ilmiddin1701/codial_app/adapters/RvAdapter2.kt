package com.ilmiddin1701.codial_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ilmiddin1701.codial_app.databinding.ItemVpRvBinding
import com.ilmiddin1701.codial_app.db.MyDbHelper
import com.ilmiddin1701.codial_app.models.GroupData
import com.ilmiddin1701.codial_app.models.StudentData

class RvAdapter2(var rvAction5: RvAction5, var list: List<GroupData>, var myDbHelper: MyDbHelper): Adapter<RvAdapter2.Vh>() {

    inner class Vh(var itemVpRvBinding: ItemVpRvBinding): ViewHolder(itemVpRvBinding.root){
        fun onBind(groupData: GroupData, position: Int){
            itemVpRvBinding.tvName.text = groupData.name
            itemVpRvBinding.tvCount.text = counter(groupData, myDbHelper)

            itemVpRvBinding.btnView.setOnClickListener {
                rvAction5.viewClick(groupData, position)
            }
            itemVpRvBinding.btnEdit.setOnClickListener {
                rvAction5.editClick(groupData, position)
            }
            itemVpRvBinding.btnDelete.setOnClickListener {
                rvAction5.deleteClick(groupData, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemVpRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    interface RvAction5 {
        fun viewClick(groupData: GroupData, position: Int)
        fun editClick(groupData: GroupData, position: Int)
        fun deleteClick(groupData: GroupData, position: Int)
    }

    fun counter(groupData: GroupData, myDbHelper1: MyDbHelper) : String{
        val list = ArrayList<StudentData>()
        myDbHelper1.showStudents().forEach {
            if (it.groupId?.id == groupData.id){
                list.add(it)
            }
        }
        return "O'quvchilar soni: ${list.size} ta"
    }
}