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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.victorloveday.leavemanager.R
import com.victorloveday.leavemanager.api.RetrofitInstance
import com.victorloveday.leavemanager.database.model.Leave
import com.victorloveday.leavemanager.database.model.LeaveApplicationResponse
import com.victorloveday.leavemanager.database.model.Notification
import com.victorloveday.leavemanager.database.model.NotificationResponse
import com.victorloveday.leavemanager.databinding.FragmentNotificationBinding
import com.victorloveday.leavemanager.ui.viewmodels.LeaveViewModel
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class NotificationFragment : Fragment(R.layout.fragment_notification) {

    private lateinit var binding: FragmentNotificationBinding

    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var leaveViewModel: LeaveViewModel
    private lateinit var response: Response<NotificationResponse>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotificationBinding.bind(view)

        notificationAdapter = NotificationAdapter(requireContext())
        leaveViewModel = ViewModelProvider(requireActivity()).get(LeaveViewModel::class.java)

        setupNotificationRecyclerView()
        fetchNotificationsFromRemoteServer()
    }

    private fun fetchNotificationsFromRemoteServer() {
        lifecycleScope.launchWhenCreated {
            response = try {
                RetrofitInstance.api.getNotifications("employeeNotification", "5")

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
                    leaveViewModel.saveNotification(result)
                    Toast.makeText(requireContext(), "Successfully Updated", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(requireContext(), "Failed ${response.body()}", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(requireContext(), "Failed: ${response.body()?.status}", Toast.LENGTH_SHORT).show()

                return@launchWhenCreated
            }

        }

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