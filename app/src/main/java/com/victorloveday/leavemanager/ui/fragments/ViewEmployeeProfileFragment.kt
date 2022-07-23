package com.victorloveday.leavemanager.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.victorloveday.leavemanager.R
import com.victorloveday.leavemanager.databinding.FragmentProfileBinding

class ViewEmployeeProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    val args: ViewEmployeeProfileFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        displayReceivedData()
    }

    private fun displayReceivedData() {
        binding.fullName.text = args.user.name
        binding.role.text = args.user.role
        binding.age.text = args.user.age
        binding.gender.text = args.user.gender
        binding.nationality.text = args.user.nationality
    }

}