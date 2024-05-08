package com.ilmiddin1701.codial_app.fragments.guruhlar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.codial_app.R
import com.ilmiddin1701.codial_app.adapters.RvAdapter1
import com.ilmiddin1701.codial_app.adapters.RvAdapter2
import com.ilmiddin1701.codial_app.adapters.SpinnerAdapter
import com.ilmiddin1701.codial_app.databinding.FragmentItemViewPager2Binding
import com.ilmiddin1701.codial_app.databinding.ItemEditGroupBinding
import com.ilmiddin1701.codial_app.db.MyDbHelper
import com.ilmiddin1701.codial_app.models.GroupData
import com.ilmiddin1701.codial_app.models.Spinner
import com.ilmiddin1701.codial_app.models.StudentData
import com.ilmiddin1701.codial_app.utils.MyData

class ItemViewPager2Fragment : Fragment(), RvAdapter2.RvAction5 {
    private val binding by lazy { FragmentItemViewPager2Binding.inflate(layoutInflater) }
    lateinit var myDbHelper: MyDbHelper
    lateinit var rvAdapter2: RvAdapter2
    lateinit var listGroups: ArrayList<GroupData>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onResume()
        return binding.root
    }

    override fun viewClick(groupData: GroupData, position: Int) {
        val navOption = NavOptions.Builder()
        navOption.setEnterAnim(R.anim.enter_anim)
        navOption.setExitAnim(R.anim.exit_anim)
        navOption.setPopEnterAnim(R.anim.pop_enter_anim)
        navOption.setPopExitAnim(R.anim.pop_exit_anim)
        MyData.groups = groupData
        findNavController().navigate(R.id.groupViewFragment, bundleOf(), navOption.build())
    }

    override fun editClick(groupData: GroupData, position: Int) {
        myDbHelper = MyDbHelper(requireContext())
        val mentorList = ArrayList<Spinner>()
        val timeList = arrayListOf(
            Spinner("Yo'q"),
            Spinner("08:00 - 10:00"),
            Spinner("10:00 - 12:00"),
            Spinner("14:00 - 16:00"),
            Spinner("16:00 - 18:00"),
            Spinner("18:00 - 20:00")
        )
        val dayList = arrayListOf(
            Spinner("Yo'q"),
            Spinner("Dushanba/Chorshanba/Juma"),
            Spinner("Seshanba/Payshanba/Shanba")
        )
        val dialog = AlertDialog.Builder(requireContext()).create()
        val itemEditGroup = ItemEditGroupBinding.inflate(layoutInflater)
        itemEditGroup.groupName.setText(groupData.name)
        mentorList.add(Spinner("Yo'q"))
        myDbHelper.showMentors().forEach {
            if (it.courseId?.id == MyData.courses?.id) {
                mentorList.add(Spinner("${it.firstName} ${it.lastName}"))
            }
        }
        itemEditGroup.spinnerMentor.adapter = SpinnerAdapter(mentorList)
        itemEditGroup.spinnerMentor.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    itemEditGroup.courseMentor.text = null
                } else {
                    itemEditGroup.courseMentor.text = mentorList[position].name
                    myDbHelper.showMentors().forEach{
                        if (it.courseId?.id == MyData.courses?.id){
                            MyData.mentors = it
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        itemEditGroup.spinnerTime.adapter = SpinnerAdapter(timeList)
        itemEditGroup.spinnerTime.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    itemEditGroup.courseTime.text = null
                } else {
                    itemEditGroup.courseTime.text = timeList[position].name
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        itemEditGroup.spinnerDay.adapter = SpinnerAdapter(dayList)
        itemEditGroup.spinnerDay.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    itemEditGroup.courseDay.text = null
                } else {
                    itemEditGroup.courseDay.text = dayList[position].name
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        itemEditGroup.btnAdd.setOnClickListener {
            if (itemEditGroup.groupName.text.isNotEmpty() && itemEditGroup.courseMentor.text.isNotEmpty() && itemEditGroup.courseTime.text.isNotEmpty() && itemEditGroup.courseDay.text.isNotEmpty()) {
                val mentor = itemEditGroup.courseMentor.text.toString()
                var firstName = mentor.substringBefore(' ')
                var lastName = mentor.substringAfter(' ')
                groupData.name = itemEditGroup.groupName.text.toString()
                groupData.mentorId?.firstName = firstName
                groupData.mentorId?.lastName = lastName
                groupData.day = itemEditGroup.courseDay.text.toString()
                groupData.time = itemEditGroup.courseTime.text.toString()
                myDbHelper.editGroup(groupData)
                onResume()
                dialog.cancel()
            }
        }
        itemEditGroup.btnClose.setOnClickListener {
            dialog.cancel()
        }
        dialog.setView(itemEditGroup.root)
        dialog.show()
    }

    override fun deleteClick(groupData: GroupData, position: Int) {
        myDbHelper = MyDbHelper(requireContext())
        var students: StudentData? = null
        val listStudents = ArrayList<StudentData>()
        myDbHelper.showStudents().forEach {
            if (it.groupId?.id == MyData.groups?.id) {
                listStudents.add(it)
                students = it
            }
        }
        if (listStudents.size != 0) {
            myDbHelper.deleteStudent(students!!)
            myDbHelper.deleteGroup(groupData)
        } else {
            Toast.makeText(context, "Guruh talabalar ro'yxati bo'sh!", Toast.LENGTH_SHORT).show()
        }
        onResume()
    }

    override fun onResume() {
        super.onResume()
        myDbHelper = MyDbHelper(requireContext())
        listGroups = ArrayList()
        myDbHelper.showGroups().forEach {
            if (it.mentorId?.courseId?.id == MyData.courses?.id && it.isOpened == 0) {
                listGroups.add(it)
            }
        }
        rvAdapter2 = RvAdapter2(this@ItemViewPager2Fragment, listGroups, myDbHelper)
        binding.rv.adapter = rvAdapter2
    }
}