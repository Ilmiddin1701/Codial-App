package com.ilmiddin1701.codial_app.fragments.guruhlar

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.ilmiddin1701.codial_app.databinding.FragmentAddEditBinding
import com.ilmiddin1701.codial_app.db.MyDbHelper
import com.ilmiddin1701.codial_app.models.StudentData
import com.ilmiddin1701.codial_app.utils.MyData

class AddEditFragment : Fragment() {
    private val binding by lazy { FragmentAddEditBinding.inflate(layoutInflater) }
    lateinit var myDbHelper: MyDbHelper
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.apply {
            myDbHelper = MyDbHelper(requireContext())
            val addOrEdit = arguments?.getInt("addOrEdit")
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnDate.setOnClickListener {
                val datePicker = DatePickerDialog(requireContext())
                datePicker.setOnCancelListener {
                    tvDate.text = null
                }
                datePicker.setOnDateSetListener(object : DatePickerDialog.OnDateSetListener {
                    @SuppressLint("SetTextI18n")
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                        tvDate.text = "$dayOfMonth/$month/$year"
                    }
                })
                datePicker.show()
            }
            if (addOrEdit == 0) {
                btnSaveOrEdit.setOnClickListener {
                    if (edtFirstName.text.isNotEmpty() && edtLastName.text.isNotEmpty() && edtPhoneNumber.text.isNotEmpty() && tvDate.text.isNotEmpty()) {
                        myDbHelper.addStudent(
                            StudentData(
                                edtFirstName.text.toString(),
                                edtLastName.text.toString(),
                                edtPhoneNumber.text.toString(),
                                tvDate.text.toString(),
                                MyData.groups
                            )
                        )
                        findNavController().popBackStack()
                    } else {
                        Toast.makeText(context, "Ma'lumotlar kiritilmagan", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                edtFirstName.setText(MyData.students?.firstName)
                edtLastName.setText(MyData.students?.lastName)
                edtPhoneNumber.setText(MyData.students?.number)
                tvDate.text = MyData.students?.day
                btnSaveOrEdit.setOnClickListener {
                    if (edtFirstName.text.isNotEmpty() && edtLastName.text.isNotEmpty() && edtPhoneNumber.text.isNotEmpty() && tvDate.text.isNotEmpty()) {
                        val student = MyData.students
                        student?.firstName = edtFirstName.text.toString()
                        student?.lastName = edtLastName.text.toString()
                        student?.number = edtPhoneNumber.text.toString()
                        student?.day = tvDate.text.toString()
                        myDbHelper.editStudent(MyData.students!!)
                        findNavController().popBackStack()
                    } else {
                        Toast.makeText(context, "Ma'lumotlar kiritilmagan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            return binding.root
        }
    }
}