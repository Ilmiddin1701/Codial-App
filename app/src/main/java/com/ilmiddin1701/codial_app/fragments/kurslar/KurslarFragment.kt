package com.ilmiddin1701.codial_app.fragments.kurslar

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
import com.ilmiddin1701.codial_app.adapters.CourseAdapter
import com.ilmiddin1701.codial_app.databinding.CustomDialog1Binding
import com.ilmiddin1701.codial_app.databinding.FragmentKurslarBinding
import com.ilmiddin1701.codial_app.db.MyDbHelper
import com.ilmiddin1701.codial_app.models.CourseData

class KurslarFragment : Fragment() {
    private val binding by lazy { FragmentKurslarBinding.inflate(layoutInflater) }
    lateinit var courseAdapter: CourseAdapter
    lateinit var myDbHelper: MyDbHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        showItem()
        binding.apply {
            btnBack.setOnClickListener { findNavController().popBackStack() }
            btnAdd.setOnClickListener {
                val dialog = AlertDialog.Builder(requireContext(), R.style.NewDialog).create()
                val customDialog = CustomDialog1Binding.inflate(layoutInflater)
                customDialog.btnAdd.setOnClickListener {
                    if (customDialog.edtName.text.isNotEmpty() && customDialog.edtInformation.text.isNotEmpty()){
                        val courseData = CourseData(customDialog.edtName.text.toString(), customDialog.edtInformation.text.toString())
                        myDbHelper.addCourse(courseData)
                        showItem()
                        dialog.cancel()
                    }
                }
                customDialog.btnClose.setOnClickListener {
                    dialog.cancel()
                }
                dialog.setView(customDialog.root)
                dialog.show()
            }
        }
        return binding.root
    }

    fun showItem(){
        myDbHelper = MyDbHelper(requireContext())
        courseAdapter = CourseAdapter(object : CourseAdapter.RvAction1{
            override fun itemClick(courseData: CourseData, position: Int) {
                val navOption = NavOptions.Builder()
                navOption.setEnterAnim(R.anim.enter_anim)
                navOption.setExitAnim(R.anim.exit_anim)
                navOption.setPopEnterAnim(R.anim.pop_enter_anim)
                navOption.setPopExitAnim(R.anim.pop_exit_anim)
                findNavController().navigate(
                    R.id.courseInformationFragment,
                    bundleOf("keyPosition" to position),
                    navOption.build()
                )
            }
        }, myDbHelper.showCourses() as ArrayList)
        binding.rv.adapter = courseAdapter
    }
}