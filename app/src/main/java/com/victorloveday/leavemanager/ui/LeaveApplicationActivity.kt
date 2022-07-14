package com.victorloveday.leavemanager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import com.victorloveday.leavemanager.R
import com.victorloveday.leavemanager.databinding.ActivityLeaveApplicationBinding

class LeaveApplicationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeaveApplicationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaveApplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

