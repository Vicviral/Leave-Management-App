package com.victorloveday.leavemanager.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.victorloveday.leavemanager.R
import com.victorloveday.leavemanager.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
    }
}