package com.victorloveday.leavemanager.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import com.victorloveday.leavemanager.R
import com.victorloveday.leavemanager.database.UserInfoManager
import com.victorloveday.leavemanager.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var userInfoManager: UserInfoManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        userInfoManager = UserInfoManager(requireContext())
        displayLoggedInUserData()

        userInfoManager.genderFlow.asLiveData().observe(viewLifecycleOwner, {
            when(it) {
                "Male" -> binding.profilePic.setImageResource(R.drawable.ic_male_profile)
                "Female" -> binding.profilePic.setImageResource(R.drawable.ic_female_profile)
            }
        })

    }


    private fun displayLoggedInUserData() {
        userInfoManager.nameFlow.asLiveData().observe(viewLifecycleOwner, { name ->
            userInfoManager.roleFlow.asLiveData().observe(viewLifecycleOwner, { role ->
                userInfoManager.ageFlow.asLiveData().observe(viewLifecycleOwner, { age ->
                    userInfoManager.genderFlow.asLiveData().observe(viewLifecycleOwner, { gender ->
                        userInfoManager.nationFlow.asLiveData().observe(viewLifecycleOwner, { nation ->
                            binding.fullName.text = name
                            binding.role.text = role
                            binding.age.text = age
                            binding.gender.text = gender
                            binding.nationality.text = nation
                        })
                    })
                })
            })
        })

    }
}