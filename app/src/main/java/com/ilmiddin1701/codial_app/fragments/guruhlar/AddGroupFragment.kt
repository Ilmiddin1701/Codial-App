package com.ilmiddin1701.codial_app.fragments.guruhlar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.codial_app.adapters.SpinnerAdapter

import com.ilmiddin1701.codial_app.databinding.FragmentAddGroupBinding
import com.ilmiddin1701.codial_app.db.MyDbHelper
import com.ilmiddin1701.codial_app.models.GroupData
import com.ilmiddin1701.codial_app.models.MentorData
import com.ilmiddin1701.codial_app.models.Spinner
import com.ilmiddin1701.codial_app.utils.MyData

class AddGroupFragment : Fragment() {
    private val binding by lazy { FragmentAddGroupBinding.inflate(layoutInflater) }
    lateinit var myDbHelper: MyDbHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            mentorList.add(Spinner("Yo'q"))
            myDbHelper.showMentors().forEach {
                if (it.courseId?.id == MyData.courses?.id) {
                    mentorList.add(Spinner("${it.firstName} ${it.lastName}"))
                }
            }
            spinnerMentor.adapter = SpinnerAdapter(mentorList)
            spinnerMentor.setOnItemSelectedListener(object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        courseMentor.text = null
                    } else {
                        courseMentor.text = mentorList[position].name
                        myDbHelper.showMentors().forEach{
                            if (it.courseId?.id == MyData.courses?.id){
                                MyData.mentors = it
                            }
                        }
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            })
            spinnerTime.adapter = SpinnerAdapter(timeList)
            spinnerTime.setOnItemSelectedListener(object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        courseTime.text = null
                    } else {
                        courseTime.text = timeList[position].name
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            })
            spinnerDay.adapter = SpinnerAdapter(dayList)
            spinnerDay.setOnItemSelectedListener(object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        courseDay.text = null
                    } else {
                        courseDay.text = dayList[position].name
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            })
            btnSave.setOnClickListener {
                if (groupName.text.isNotEmpty() && courseMentor.text.isNotEmpty() && courseTime.text.isNotEmpty() && courseDay.text.isNotEmpty()) {
                    myDbHelper.addGroup(
                        GroupData(
                            name = groupName.text.toString(),
                            mentorId = MyData.mentors,
                            day = courseDay.text.toString(),
                            time = courseTime.text.toString(),
                            isOpened = 0
                        )
                    )
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(requireContext(), "Ma'lumotlar kiritilmagan", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }
}