package com.ilmiddin1701.codial_app.fragments.mentorlar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.codial_app.databinding.FragmentAddMentorBinding
import com.ilmiddin1701.codial_app.db.MyDbHelper
import com.ilmiddin1701.codial_app.models.MentorData
import com.ilmiddin1701.codial_app.utils.MyData

class AddMentorFragment : Fragment() {
    private val binding by lazy { FragmentAddMentorBinding.inflate(layoutInflater) }
    lateinit var myDbHelper: MyDbHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.apply {
            btnAdd.setOnClickListener {
                myDbHelper = MyDbHelper(requireContext())
                val mentorData = MentorData(edtFirstName.text.toString(), edtLastName.text.toString(), edtPhoneNumber.text.toString(), MyData.courses)
                myDbHelper.addMentor(mentorData)
                Toast.makeText(requireContext(), "Mentor qo'shildi", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
        return binding.root
    }
}