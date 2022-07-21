package com.victorloveday.leavemanager.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.victorloveday.leavemanager.R
import com.victorloveday.leavemanager.databinding.FragmentEmployeesBinding

class EmployeesFragment: Fragment(R.layout.fragment_employees) {

    private lateinit var binding: FragmentEmployeesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEmployeesBinding.bind(view)

        binding.employeesRecyclerView.setOnClickListener {
            findNavController().navigate(
                R.id.action_employeesFragment_to_viewEmployeeProfileFragment
            )

        }
    }
}