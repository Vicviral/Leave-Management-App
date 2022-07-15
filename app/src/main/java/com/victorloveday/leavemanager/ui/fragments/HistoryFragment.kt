package com.victorloveday.leavemanager.ui.fragments

import HistoryAdapter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.victorloveday.leavemanager.R
import com.victorloveday.leavemanager.database.model.Leave
import com.victorloveday.leavemanager.databinding.FragmentHistoryBinding
import com.victorloveday.leavemanager.ui.viewmodels.LeaveViewModel
import java.util.ArrayList

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var leaveViewModel: LeaveViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHistoryBinding.bind(view)

        leaveViewModel = ViewModelProvider(requireActivity()).get(LeaveViewModel::class.java)

        setupHistoryRecyclerView()
    }

    private fun setupHistoryRecyclerView() = binding.historyRecyclerView.apply {

        historyAdapter = HistoryAdapter(requireContext())
        adapter = historyAdapter
        layoutManager = LinearLayoutManager(requireContext())

        leaveViewModel.readAllLeaveHistory.observe(viewLifecycleOwner, {

            if (it.isNotEmpty()) {

                if (it.size > 1) {
                    val slideFromRight = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_right)
                    binding.historyRecyclerView.startAnimation(slideFromRight)

                    historyAdapter.setData(it)
                }
            } else {
                Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
            }

        })


    }

}