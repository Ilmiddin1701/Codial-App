package com.ilmiddin1701.codial_app.fragments.mentorlar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.codial_app.R
import com.ilmiddin1701.codial_app.adapters.GroupAdapter
import com.ilmiddin1701.codial_app.databinding.FragmentMentorlarBinding
import com.ilmiddin1701.codial_app.db.MyDbHelper
import com.ilmiddin1701.codial_app.models.CourseData
import com.ilmiddin1701.codial_app.utils.MyData

class MentorlarFragment : Fragment() {
    private val binding by lazy { FragmentMentorlarBinding.inflate(layoutInflater) }
    lateinit var myDbHelper: MyDbHelper
    lateinit var groupAdapter: GroupAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            val navOption = NavOptions.Builder()
            navOption.setEnterAnim(R.anim.enter_anim)
            navOption.setExitAnim(R.anim.exit_anim)
            navOption.setPopEnterAnim(R.anim.pop_enter_anim)
            navOption.setPopExitAnim(R.anim.pop_exit_anim)
            myDbHelper = MyDbHelper(requireContext())
            groupAdapter = GroupAdapter(object : GroupAdapter.RvAction2 {
                override fun itemClick(courseData: CourseData, position: Int) {
                    findNavController().navigate(
                        R.id.mentorInformationFragment,
                        bundleOf("p" to position),
                        navOption.build()
                    )
                    MyData.courses = courseData
                }
            }, myDbHelper.showCourses() as ArrayList)
            rv.adapter = groupAdapter
        }
        return binding.root
    }
}