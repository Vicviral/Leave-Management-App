package com.victorloveday.leavemanager.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.victorloveday.leavemanager.R
import com.victorloveday.leavemanager.adapters.HistoryAdapter
import com.victorloveday.leavemanager.api.RetrofitInstance
import com.victorloveday.leavemanager.database.model.LeaveResponse
import com.victorloveday.leavemanager.databinding.FragmentHomeBinding
import com.victorloveday.leavemanager.ui.viewmodels.LeaveViewModel
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var leaveViewModel: LeaveViewModel
    private lateinit var response: Response<LeaveResponse>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        leaveViewModel = ViewModelProvider(this).get(LeaveViewModel::class.java)
        historyAdapter = HistoryAdapter(requireContext())

        fetchUserLeaveHistoryFromAPI()

        //set backgrounds for colors
        binding.cancelLeave.setBackgroundResource(R.drawable.cancel_btn_bg)
        binding.editLeave.setBackgroundResource(R.drawable.edit_btn_bg)
        disablePendingLeaveButtons()

    }

    private fun fetchUserLeaveHistoryFromAPI() {
        //make api request
        lifecycleScope.launchWhenCreated {
            response = try {
                RetrofitInstance.api.getLeaves("userId")

            } catch (e: IOException) {
                Toast.makeText(requireContext(), "Check your internet and try again...", Toast.LENGTH_SHORT).show()

                return@launchWhenCreated
            } catch (e: HttpException) {
                Toast.makeText(requireContext(), "Check your internet and try again...", Toast.LENGTH_SHORT).show()

                return@launchWhenCreated
            } catch (e: SocketTimeoutException) {
                Toast.makeText(requireContext(), "Check your internet and try again...", Toast.LENGTH_SHORT).show()

                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {

                if (!response.body()!!.error) {
                    //upsert response to data base
                    saveHistoryToDatabase()

                } else {
                    Toast.makeText(requireContext(), response.body()!!.message, Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(requireContext(), "Response not successful", Toast.LENGTH_SHORT).show()

                return@launchWhenCreated
            }

        }
    }

    private fun saveHistoryToDatabase() {
        leaveViewModel.readAllLeaveHistory
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