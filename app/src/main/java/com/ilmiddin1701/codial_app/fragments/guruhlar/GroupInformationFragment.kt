package com.ilmiddin1701.codial_app.fragments.guruhlar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.ilmiddin1701.codial_app.R
import com.ilmiddin1701.codial_app.adapters.VpAdapter
import com.ilmiddin1701.codial_app.databinding.FragmentGroupInformationBinding
import com.ilmiddin1701.codial_app.db.MyDbHelper

class GroupInformationFragment : Fragment() {
    private val binding by lazy { FragmentGroupInformationBinding.inflate(layoutInflater) }
    lateinit var myDbHelper: MyDbHelper
    private lateinit var vpAdapter: VpAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val p = arguments?.getInt("p")
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            val navOption = NavOptions.Builder()
            navOption.setEnterAnim(R.anim.enter_anim)
            navOption.setExitAnim(R.anim.exit_anim)
            navOption.setPopEnterAnim(R.anim.pop_enter_anim)
            navOption.setPopExitAnim(R.anim.pop_exit_anim)
            btnAdd.setOnClickListener {
                findNavController().navigate(R.id.addGroupFragment, bundleOf(), navOption.build())
            }
            vpAdapter = VpAdapter(childFragmentManager)
            tabLayout.setupWithViewPager(viewPager)
            viewPager.adapter = vpAdapter
            myDbHelper = MyDbHelper(requireContext())
            tvTitle.text = myDbHelper.showCourses()[p!!].name
            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    if (position == 0) btnAdd.visibility = View.INVISIBLE else btnAdd.visibility = View.VISIBLE
                }
                override fun onPageSelected(position: Int) {}
                override fun onPageScrollStateChanged(state: Int) {}
            })
        }
        return binding.root
    }
}