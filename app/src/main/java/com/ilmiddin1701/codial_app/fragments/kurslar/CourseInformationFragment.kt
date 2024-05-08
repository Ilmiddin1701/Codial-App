package com.ilmiddin1701.codial_app.fragments.kurslar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.codial_app.databinding.FragmentCourseInformationBinding
import com.ilmiddin1701.codial_app.db.MyDbHelper
import com.ilmiddin1701.codial_app.utils.MyData

class CourseInformationFragment : Fragment() {
    private val binding by lazy { FragmentCourseInformationBinding.inflate(layoutInflater) }
    lateinit var myDbHelper: MyDbHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val p = arguments?.getInt("keyPosition")
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            myDbHelper = MyDbHelper(requireContext())
            tvTitle.text = myDbHelper.showCourses()[p!!].name
            tvInformation.text = myDbHelper.showCourses()[p].about
        }
        return binding.root
    }
}