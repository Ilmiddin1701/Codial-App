package com.ilmiddin1701.codial_app.fragments.mentorlar

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.codial_app.R
import com.ilmiddin1701.codial_app.adapters.MentorAdapter
import com.ilmiddin1701.codial_app.databinding.CustomDialog1Binding
import com.ilmiddin1701.codial_app.databinding.CustomDialog2Binding
import com.ilmiddin1701.codial_app.databinding.FragmentMentorInformationBinding
import com.ilmiddin1701.codial_app.db.MyDbHelper
import com.ilmiddin1701.codial_app.models.CourseData
import com.ilmiddin1701.codial_app.models.MentorData
import com.ilmiddin1701.codial_app.utils.MyData

class MentorInformationFragment : Fragment(), MentorAdapter.RvAction3 {
    private val binding by lazy { FragmentMentorInformationBinding.inflate(layoutInflater) }
    lateinit var myDbHelper: MyDbHelper
    private lateinit var mentorAdapter: MentorAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            val navOption = NavOptions.Builder()
            navOption.setEnterAnim(R.anim.enter_anim)
            navOption.setExitAnim(R.anim.exit_anim)
            navOption.setPopEnterAnim(R.anim.pop_enter_anim)
            navOption.setPopExitAnim(R.anim.pop_exit_anim)
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnAdd.setOnClickListener {
                findNavController().navigate(R.id.addMentorFragment, bundleOf(), navOption.build())
            }
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            val p = arguments?.getInt("p")
            myDbHelper = MyDbHelper(requireContext())
            tvTitle.text = myDbHelper.showCourses()[p!!].name
            notifyAdapter()
            return binding.root
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun editClick(mentorData: MentorData, position: Int) {
        val dialog = AlertDialog.Builder(requireContext(), R.style.NewDialog).create()
        val customDialog = CustomDialog2Binding.inflate(layoutInflater)
        customDialog.mentorFirstName.setText(mentorData.firstName)
        customDialog.mentorLastName.setText(mentorData.lastName)
        customDialog.mentorPhoneNumber.setText(mentorData.phoneNumber)
        customDialog.btnAdd.setOnClickListener {
            if (customDialog.mentorFirstName.text.isNotEmpty() && customDialog.mentorLastName.text.isNotEmpty() && customDialog.mentorPhoneNumber.text.isNotEmpty()){
                mentorData.firstName = customDialog.mentorFirstName.text.toString()
                mentorData.lastName = customDialog.mentorLastName.text.toString()
                mentorData.phoneNumber = customDialog.mentorPhoneNumber.text.toString()
                myDbHelper.editMentor(mentorData)
                mentorAdapter.notifyDataSetChanged()
                dialog.cancel()
            }
        }
        customDialog.btnClose.setOnClickListener {
            dialog.cancel()
        }
        dialog.setView(customDialog.root)
        dialog.show()
    }

    override fun deleteClick(mentorData: MentorData, position: Int) {
        myDbHelper.deleteMentor(mentorData)
        notifyAdapter()
    }

    fun notifyAdapter() {
        val courseList = ArrayList<MentorData>()
        myDbHelper.showMentors().forEach {
            if (it.courseId?.id == MyData.courses?.id){
                courseList.add(it)
            }
        }
        mentorAdapter = MentorAdapter(this@MentorInformationFragment,courseList)
        binding.rv.adapter = mentorAdapter
    }
}