package com.ilmiddin1701.codial_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.codial_app.R
import com.ilmiddin1701.codial_app.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val navOption = NavOptions.Builder()
        navOption.setEnterAnim(R.anim.enter_anim)
        navOption.setExitAnim(R.anim.exit_anim)
        navOption.setPopEnterAnim(R.anim.pop_enter_anim)
        navOption.setPopExitAnim(R.anim.pop_exit_anim)
        binding.apply {
            card1.setOnClickListener {
                findNavController().navigate(R.id.kurslarFragment, bundleOf(), navOption.build())
            }
            card2.setOnClickListener {
                findNavController().navigate(R.id.guruhlarFragment, bundleOf(), navOption.build())
            }
            card3.setOnClickListener {
                findNavController().navigate(R.id.mentorlarFragment, bundleOf(), navOption.build())
            }
        }
        return binding.root
    }
}