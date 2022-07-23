package com.victorloveday.leavemanager.ui.fragments

import EmployeesAdapter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.victorloveday.leavemanager.R
import com.victorloveday.leavemanager.databinding.FragmentEmployeesBinding
import com.victorloveday.leavemanager.ui.viewmodels.EmployeesViewModel

class EmployeesFragment: Fragment(R.layout.fragment_employees) {

    private lateinit var binding: FragmentEmployeesBinding
    private lateinit var employeesAdapter: EmployeesAdapter
    private lateinit var employeesViewModel: EmployeesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEmployeesBinding.bind(view)

        employeesAdapter = EmployeesAdapter(requireContext())
        employeesViewModel = ViewModelProvider(this).get(EmployeesViewModel::class.java)

        setupHistoryRecyclerView()
    }

    private fun setupHistoryRecyclerView() = binding.employeesRecyclerView .apply {

        adapter = employeesAdapter
        layoutManager = LinearLayoutManager(requireContext())
        setPadding(0, 0, 0, 100)

        employeesViewModel.readAllEmployees.observe(viewLifecycleOwner, { employees ->

            if (employees.isNotEmpty()) {
                employeesAdapter.setData(employees)

            } else {
                Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
            }

        })


        employeesAdapter.setOnEmployeeClickListener {
            val bundle = Bundle().apply {
                putSerializable("user", it)
            }

            findNavController().navigate(
                R.id.action_employeesFragment_to_viewEmployeeProfileFragment,
                bundle
            )
        }

    }

}