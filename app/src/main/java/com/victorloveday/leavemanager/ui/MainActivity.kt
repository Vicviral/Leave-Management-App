package com.victorloveday.leavemanager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.victorloveday.leavemanager.R
import com.victorloveday.leavemanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(1).isEnabled = false
    }
}