package com.ilmiddin1701.codial_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilmiddin1701.codial_app.databinding.ItemVpRvBinding
import com.ilmiddin1701.codial_app.db.MyDbHelper
import com.ilmiddin1701.codial_app.models.GroupData
import com.ilmiddin1701.codial_app.models.StudentData

class RvAdapter1(var rvAction: RvAction, var list: List<GroupData>, var myDbHelper: MyDbHelper): RecyclerView.Adapter<RvAdapter1.Vh>() {

    inner class Vh(var itemVpRvBinding: ItemVpRvBinding) : RecyclerView.ViewHolder(itemVpRvBinding.root) {
        fun onBind(groupData: GroupData, position: Int) {
            itemVpRvBinding.btnView.setOnClickListener {
                rvAction.viewClick(groupData, position)
            }
            itemVpRvBinding.btnEdit.setOnClickListener {
                rvAction.editClick(groupData, position)
            }
            itemVpRvBinding.btnDelete.setOnClickListener {
                rvAction.deleteClick(groupData, position)
            }
            itemVpRvBinding.tvName.text = groupData.name
            itemVpRvBinding.tvCount.text = counter(groupData, myDbHelper)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemVpRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    interface RvAction{
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