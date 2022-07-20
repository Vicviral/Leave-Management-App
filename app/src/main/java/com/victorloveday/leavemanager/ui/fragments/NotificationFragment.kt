package com.victorloveday.leavemanager.ui.fragments

import NotificationAdapter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.victorloveday.leavemanager.R
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
    }

    private fun setupNotificationRecyclerView() = binding.notificationRecyclerView.apply {
        //for demo purpose
//        val leave = Leave(0, "Going to the moon", "Casual Leave", "I'll need to travel for an emergency trip due to my father's coronation in Ibadan", "28 July", "2 August", "Pending")
//        leaveViewModel.saveLeave(leave)

        adapter = notificationAdapter
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


    }

}