package com.victorloveday.leavemanager.ui.fragments

import HistoryAdapter
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.victorloveday.leavemanager.R
import com.victorloveday.leavemanager.api.RetrofitInstance
import com.victorloveday.leavemanager.database.UserInfoManager
import com.victorloveday.leavemanager.database.model.HistoryResponse
import com.victorloveday.leavemanager.database.model.Leave
import com.victorloveday.leavemanager.database.model.LeaveApplicationResponse
import com.victorloveday.leavemanager.databinding.FragmentHistoryBinding
import com.victorloveday.leavemanager.ui.viewmodels.LeaveViewModel
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var leaveViewModel: LeaveViewModel
    private lateinit var response: Response<LeaveApplicationResponse>
    private lateinit var historyResponse: Response<HistoryResponse>
    private lateinit var userInfoManager: UserInfoManager


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHistoryBinding.bind(view)

        //entry animation
        val slideFromRight = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_right)
        binding.historyRecyclerView.startAnimation(slideFromRight)


        historyAdapter = HistoryAdapter(requireContext())
        userInfoManager = UserInfoManager(requireContext())
        leaveViewModel = ViewModelProvider(requireActivity()).get(LeaveViewModel::class.java)

        setupHistoryRecyclerView()
        deleteLeave()
        switchTabs()
    }

    private fun deleteLeave() {

        historyAdapter.setOnLeaveClickListener { leave ->
            historyAdapter.onDeleteItem {

                if (it == true) {
                    val dialog = BottomSheetDialog(requireContext())
                    val view = layoutInflater.inflate(R.layout.delete_leave_bottom_sheet, null)
                    dialog.setCancelable(false)
                    dialog.setContentView(view)
                    dialog.show()

                    view.findViewById<LinearLayout>(R.id.deleteLeaveBS).setOnClickListener {
                        //this function only hides the deleted leave from the user's interface
                        //all data remains persistent on the remote database
                        deleteRowFromRemoteDatabase(leave, dialog)
                    }
                    view.findViewById<LinearLayout>(R.id.cancelLeaveBS).setOnClickListener {
                        dialog.dismiss()

                    }

                }

            }

        }


    }

    private fun deleteRowFromRemoteDatabase(leave: Leave, dialog: BottomSheetDialog) {
        lifecycleScope.launchWhenCreated {
            response = try {
                RetrofitInstance.api.deleteLeave("leave_application_deletion", "${leave.id}")

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
                    //leave has been successfully removed from remote server
                    //remove leave from local db
                    leaveViewModel.deleteLeave(leave)
                    dialog.dismiss()
                    Toast.makeText(requireContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(requireContext(), "Failed ${response.body()}", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(requireContext(), "Failed: ${response.body()?.status}", Toast.LENGTH_SHORT).show()

                return@launchWhenCreated
            }

        }

    }

    private fun setupHistoryRecyclerView() = binding.historyRecyclerView.apply {

        adapter = historyAdapter
        layoutManager = LinearLayoutManager(requireContext())
        setPadding(0, 0, 0, 100)

        userInfoManager.userIdFlow.asLiveData().observe(viewLifecycleOwner, { userId ->
            leaveViewModel.getAllLeaveHistoryByUserId(userId).observe(viewLifecycleOwner, {

                if (it.isNotEmpty()) {
                    historyAdapter.setData(it)

                } else {
                    Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
                }

            })

        })

    }

    private fun switchTabs() {
        binding.tabs.setOnCheckedChangeListener { group, selectedTb ->

            val slideFromBottom = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_bottom)

            userInfoManager.userIdFlow.asLiveData().observe(viewLifecycleOwner, { userId ->
                when(selectedTb) {
                    R.id.tab1 -> {
                        leaveViewModel.getAllLeaveHistoryByUserId(userId).observe(viewLifecycleOwner, {
                            binding.historyRecyclerView.startAnimation(slideFromBottom)
                            historyAdapter.setData(it)
                        })
                    }
                    R.id.tab2 -> {
                        leaveViewModel.getLeaveHistoryByLeaveTypeAndUserId("Casual", userId).observe(viewLifecycleOwner, {
                            binding.historyRecyclerView.startAnimation(slideFromBottom)
                            historyAdapter.setData(it)
                        })
                    }
                    R.id.tab3 -> {
                        leaveViewModel.getLeaveHistoryByLeaveTypeAndUserId("Sick", userId).observe(viewLifecycleOwner, {
                            binding.historyRecyclerView.startAnimation(slideFromBottom)
                            historyAdapter.setData(it)
                        })
                    }
                }

            })
        }

    }


}