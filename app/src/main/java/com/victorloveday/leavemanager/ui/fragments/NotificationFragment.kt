package com.victorloveday.leavemanager.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.victorloveday.leavemanager.R
import com.victorloveday.leavemanager.databinding.FragmentApplicationBinding
import com.victorloveday.leavemanager.databinding.FragmentHomeBinding
import com.victorloveday.leavemanager.databinding.FragmentNotificationBinding

class NotificationFragment: Fragment(R.layout.fragment_notification) {

    private lateinit var binding: FragmentNotificationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotificationBinding.bind(view)
    }
}