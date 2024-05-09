package com.ilmiddin1701.codial_app.fragments.guruhlar

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.codial_app.R
import com.ilmiddin1701.codial_app.adapters.StudentAdapter
import com.ilmiddin1701.codial_app.databinding.FragmentGroupViewBinding
import com.ilmiddin1701.codial_app.db.MyDbHelper
import com.ilmiddin1701.codial_app.models.StudentData
import com.ilmiddin1701.codial_app.utils.MyData

class GroupViewFragment : Fragment(), StudentAdapter.RvAction6 {
    private val binding by lazy { FragmentGroupViewBinding.inflate(layoutInflater) }
    lateinit var myDbHelper: MyDbHelper
    lateinit var studentAdapter: StudentAdapter
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onResume()
        binding.apply {
            if (MyData.groups?.isOpened == 1){
                btnStart.visibility = View.GONE
            }
            myDbHelper = MyDbHelper(requireContext())
            tvTitle.text = MyData.groups?.name
            tvName.text = "Nomi: ${MyData.groups?.name}"
            tvTime.text = "Vaqti: ${MyData.groups?.time}"
            tvDay.text = "Kuni: ${MyData.groups?.day}"
            val navOption = NavOptions.Builder()
            navOption.setEnterAnim(R.anim.enter_anim)
            navOption.setExitAnim(R.anim.exit_anim)
            navOption.setPopEnterAnim(R.anim.pop_enter_anim)
            navOption.setPopExitAnim(R.anim.pop_exit_anim)
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnAdd.setOnClickListener {
                findNavController().navigate(R.id.addEditFragment, bundleOf("addOrEdit" to 0), navOption.build())
            }
            btnStart.setOnClickListener {
                MyData.groups?.isOpened = 1
                myDbHelper.editGroup(MyData.groups!!)
                findNavController().popBackStack()
            }
        }
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        myDbHelper = MyDbHelper(requireContext())
        val list = ArrayList<StudentData>()
        myDbHelper.showStudents().forEach {
            if (it.groupId?.id == MyData.groups?.id){
                list.add(it)
            }
        }
        binding.tvCount.text = "O'quvchilar soni: ${list.size} ta"
        val listStudents = ArrayList<StudentData>()
        myDbHelper.showStudents().forEach {
            if (it.groupId?.id == MyData.groups?.id){
                listStudents.add(it)
            }
        }
        studentAdapter = StudentAdapter(this@GroupViewFragment, listStudents)
        binding.rv.adapter = studentAdapter
    }

    override fun editClick(studentData: StudentData, position: Int) {
        val navOption = NavOptions.Builder()
        navOption.setEnterAnim(R.anim.enter_anim)
        navOption.setExitAnim(R.anim.exit_anim)
        navOption.setPopEnterAnim(R.anim.pop_enter_anim)
        navOption.setPopExitAnim(R.anim.pop_exit_anim)
        MyData.students = studentData
        findNavController().navigate(R.id.addEditFragment, bundleOf("addOrEdit" to 1), navOption.build())
    }

    override fun deleteClick(studentData: StudentData, position: Int) {
        myDbHelper.deleteStudent(studentData)
        onResume()
        val listStudents = ArrayList<StudentData>()
        myDbHelper.showStudents().forEach {
            if (it.groupId?.id == MyData.groups?.id){
                listStudents.add(it)
            }
        }
        if (listStudents.size == 0){
            findNavController().popBackStack()
        }
    }
}