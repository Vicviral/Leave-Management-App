package com.victorloveday.leavemanager.ui.fragments

import HistoryAdapter
import NotificationAdapter
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
import com.victorloveday.leavemanager.database.model.Notification
import com.victorloveday.leavemanager.databinding.FragmentNotificationBinding
import com.victorloveday.leavemanager.ui.viewmodels.LeaveViewModel

class NotificationFragment : Fragment(R.layout.fragment_notification) {

    private lateinit var binding: FragmentNotificationBinding

    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var leaveViewModel: LeaveViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotificationBinding.bind(view)

        notificationAdapter = NotificationAdapter(requireContext())
        leaveViewModel = ViewModelProvider(requireActivity()).get(LeaveViewModel::class.java)

        setupNotificationRecyclerView()
    }

    private fun setupNotificationRecyclerView() = binding.notificationRecyclerView.apply {

        adapter = notificationAdapter
        layoutManager = LinearLayoutManager(requireContext())
        setPadding(0, 0, 0, 100)

        leaveViewModel.readAllNotification.observe(viewLifecycleOwner, {

            if (it.isNotEmpty()) {
                notificationAdapter.setData(it)

            } else {
                Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
            }

        })

    }


}