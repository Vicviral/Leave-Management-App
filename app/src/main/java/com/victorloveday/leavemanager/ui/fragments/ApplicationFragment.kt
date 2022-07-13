package com.victorloveday.leavemanager.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.victorloveday.leavemanager.R
import com.victorloveday.leavemanager.databinding.FragmentApplicationBinding
import com.victorloveday.leavemanager.databinding.FragmentHomeBinding

class ApplicationFragment: Fragment(R.layout.fragment_application) {

    private lateinit var binding: FragmentApplicationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentApplicationBinding.bind(view)
    }
}