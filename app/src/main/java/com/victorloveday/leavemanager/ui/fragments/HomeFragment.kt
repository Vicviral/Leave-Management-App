package com.victorloveday.leavemanager.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.victorloveday.leavemanager.R
import com.victorloveday.leavemanager.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        //set backgrounds for colors
        binding.cancelLeave.setBackgroundResource(R.drawable.cancel_btn_bg)
        binding.editLeave.setBackgroundResource(R.drawable.edit_btn_bg)
        disablePendingLeaveButtons()
    }

    private fun disablePendingLeaveButtons() {
        binding.editLeave.isEnabled = false
        binding.cancelLeave.isEnabled = false
    }
    private fun enablePendingLeaveButtons() {
        binding.editLeave.isEnabled = true
        binding.cancelLeave.isEnabled = true
    }
}