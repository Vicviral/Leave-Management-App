package com.victorloveday.leavemanager.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.victorloveday.leavemanager.R
import com.victorloveday.leavemanager.api.RetrofitInstance
import com.victorloveday.leavemanager.database.model.LeaveApplicationResponse
import com.victorloveday.leavemanager.databinding.ActivityLeaveApplicationBinding
import com.victorloveday.leavemanager.utils.DateFormatter
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.*


class LeaveApplicationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeaveApplicationBinding
    private var leaveType = ""
    private var leaveTitle = ""
    private var leaveDescription = ""
    private var startDate = ""
    private var endDate = ""
    private var leaveDuration = ""
    private lateinit var response: Response<LeaveApplicationResponse>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaveApplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //select leave type
        binding.leaveTypeLayout.setOnClickListener {
            val popupMenu = PopupMenu(this, it)
            popupMenu.menuInflater.inflate(R.menu.leave_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.casual -> {
                        leaveType = item.title.toString()
                        binding.leaveType.text = leaveType
                    }
                    R.id.sick ->{
                        leaveType = item.title.toString()
                        binding.leaveType.text = leaveType
                    }
                }
                true
            }
            popupMenu.show()
        }

        binding.pickDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.dateRangePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")

            // Setting up the event for when ok is clicked
            datePicker.addOnPositiveButtonClickListener {
                //start date
                val st = datePicker.selection?.first
                val formatSt = DateFormatter().outputDateFormat.format(st)
                startDate = formatSt

                //end date
                val en = datePicker.selection?.second
                val formatEn =  DateFormatter().outputDateFormat.format(en)
                endDate = formatEn

                //leave duration
                leaveDuration = datePicker.headerText
                binding.leaveDuration.text = leaveDuration
            }

            // Setting up the event for when cancelled is clicked
            datePicker.addOnNegativeButtonClickListener {
                Toast.makeText(this, "${datePicker.headerText} is cancelled", Toast.LENGTH_LONG).show()
            }

            // Setting up the event for when back button is pressed
            datePicker.addOnCancelListener {
                //do nothing
            }
        }

        binding.applyForLeaveBtn.setOnClickListener {
            validateInput()
        }

    }

    private fun validateInput() {
        leaveTitle = binding.leaveTittle.text.toString()
        leaveDescription = binding.leaveDescription.text.toString()


        if (leaveType.isNullOrEmpty() || leaveTitle.isNullOrEmpty() || leaveDescription.isNullOrEmpty() || startDate.isNullOrEmpty() || endDate.isNullOrEmpty() || leaveDuration.isNullOrEmpty()) {
            Toast.makeText(this@LeaveApplicationActivity, "Please fill all fields", Toast.LENGTH_SHORT).show()
        } else {
            submitLeaveApplication()
        }

    }

    private fun submitLeaveApplication() {
        lifecycleScope.launchWhenCreated {
            response = try {
                RetrofitInstance.api.submitLeaveApplication(
                    "",
                    "5",
                    leaveType,
                    leaveDescription,
                    startDate,
                    endDate,
                    leaveTitle,
                    leaveDuration
                )

            } catch (e: IOException) {
                Toast.makeText(this@LeaveApplicationActivity, "Check your internet and try again...", Toast.LENGTH_SHORT).show()

                return@launchWhenCreated
            } catch (e: HttpException) {
                Toast.makeText(this@LeaveApplicationActivity, "Check your internet and try again...", Toast.LENGTH_SHORT).show()

                return@launchWhenCreated
            } catch (e: SocketTimeoutException) {
                Toast.makeText(this@LeaveApplicationActivity, "Check your internet and try again...", Toast.LENGTH_SHORT).show()

                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {

                if (response.body()!!.status == 1) {
                    //upsert response to data base
                    val result = response.body()!!.message
                    Toast.makeText(this@LeaveApplicationActivity, result, Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this@LeaveApplicationActivity, "Failed ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this@LeaveApplicationActivity, "Response not successful: ${response.body()?.status}", Toast.LENGTH_SHORT).show()

                return@launchWhenCreated
            }

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(0,  R.anim.slide_out_bottom)
    }
}

