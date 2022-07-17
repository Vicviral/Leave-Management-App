package com.victorloveday.leavemanager.ui.fragments

import HistoryAdapter
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import com.victorloveday.leavemanager.R
import com.victorloveday.leavemanager.api.RetrofitInstance
import com.victorloveday.leavemanager.database.model.HistoryResponse
import com.victorloveday.leavemanager.database.model.Leave
import com.victorloveday.leavemanager.databinding.FragmentHomeBinding
import com.victorloveday.leavemanager.ui.viewmodels.LeaveViewModel
import com.victorloveday.leavemanager.utils.Constants.Companion.GIVEN_ANNUAL_LEAVE
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var leaveViewModel: LeaveViewModel
    private lateinit var response: Response<HistoryResponse>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        historyAdapter = HistoryAdapter(requireContext())
        leaveViewModel = ViewModelProvider(requireActivity()).get(LeaveViewModel::class.java)

        displayUserMetrics()
        fetchUserLeaveHistoryFromAPI()
        setupRecentHistoryRecyclerView()
        displayRecentPendingLeave()
    }

    private fun displayUserMetrics() {

        leaveViewModel.getLeavesByStatusAndType("Casual", "Approved").observe(viewLifecycleOwner, {
            var prefix = "days"
            if (it.size <= 1) prefix = "day"
            binding.days1.text = "${it.size} $prefix"

            val progressBarValue = 100f / GIVEN_ANNUAL_LEAVE.toFloat() * it.size

            val casualLeaveProgressBar = binding.casualLeaveProgressBar
            casualLeaveProgressBar.apply {
                progress = progressBarValue
            }

        })
        leaveViewModel.getLeavesByStatusAndType("Sick", "Approved").observe(viewLifecycleOwner, {
            var prefix = "days"
            if (it.size <= 1) prefix = "day"
            binding.days2.text = "${it.size} $prefix"

            val progressBarValue = 100f / GIVEN_ANNUAL_LEAVE.toFloat()

            val sickLeaveProgressBar = binding.sickLeaveProgressBar
            sickLeaveProgressBar.apply {
                progress = progressBarValue
            }

        })
        leaveViewModel.getLeavesByStatusAndType("Maternity", "Approved").observe(viewLifecycleOwner, {
            var prefix = "days"
            if (it.size <= 1) prefix = "day"
            binding.days3.text = "${it.size} $prefix"

            val progressBarValue = 100f / GIVEN_ANNUAL_LEAVE.toFloat()

            val maternityLeaveProgressBar = binding.maternityLeaveProgressBar
            maternityLeaveProgressBar.apply {
                progress = progressBarValue
            }

        })

    }

    private fun displayRecentPendingLeave() = binding.recentLeavesRecyclerView.apply {
        leaveViewModel.getRecentPendingLeave("Pending").observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                val recentPendingLeave = it[0]
                binding.leaveTittle.text = recentPendingLeave.leave_title
                binding.leaveType.text = recentPendingLeave.leave_type
                binding.leaveDescription.text = recentPendingLeave.leave_message
                binding.leaveStatus.text = recentPendingLeave.status
                binding.duration.text = recentPendingLeave.leave_duration

                enablePendingLeaveButtons()
            }else {
                disablePendingLeaveButtons()
            }

        })
    }

    private fun setupRecentHistoryRecyclerView() = binding.recentLeavesRecyclerView.apply {
        //for demo purpose
//        val leave = Leave(0, "Going to the moon", "Casual Leave", "I'll need to travel for an emergency trip due to my father's coronation in Ibadan", "28 July", "2 August", "Pending")
//        leaveViewModel.saveLeave(leave)

        adapter = historyAdapter
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        leaveViewModel.readRecentFiveLeaves.observe(viewLifecycleOwner, {
            historyAdapter.setData(it)

            //animate recycler view position
            if (it.size > 1) {
                val slideFromRight = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_right)
                binding.recentLeavesRecyclerView.startAnimation(slideFromRight)
                Handler(Looper.getMainLooper()).postDelayed({
                    (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(0, -200)
                }, 200)
            }
        })

    }

    fun fetchUserLeaveHistoryFromAPI() {
        //make api request
        lifecycleScope.launchWhenCreated {
            response = try {
                RetrofitInstance.api.getLeaves("employeeLeaves", "5")

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

                if (response.body()!!.status == 1) {
                    //upsert response to local data base
                    val result = response.body()!!.info
                    saveHistoryToDatabase(result)

                } else {
                    Toast.makeText(requireContext(), "Failed ${response.body()}", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(requireContext(), "Failed: ${response.body()?.status}", Toast.LENGTH_SHORT).show()

                return@launchWhenCreated
            }

        }
    }

    private fun saveHistoryToDatabase(leave: List<Leave>) {
        leaveViewModel.saveLeave(leave)
        Toast.makeText(requireContext(), "Successfully saved.", Toast.LENGTH_SHORT).show()

    }

    private fun disablePendingLeaveButtons() {
        binding.editLeave.isEnabled = false
        binding.cancelLeave.isEnabled = false

        binding.editLeave.setBackgroundResource(R.drawable.disabled_button)
        binding.cancelLeave.setBackgroundResource(R.drawable.disabled_button)

        binding.cancelText.setTextColor(Color.WHITE)
        binding.editText.setTextColor(Color.WHITE)
    }
    private fun enablePendingLeaveButtons() {
        binding.editLeave.isEnabled = true
        binding.cancelLeave.isEnabled = true
    }
}