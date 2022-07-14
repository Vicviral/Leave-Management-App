package com.victorloveday.leavemanager.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.victorloveday.leavemanager.R
import com.victorloveday.leavemanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setup nav host
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        //setup app bar configuration
        appBarConfig = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.historyFragment, R.id.notificationFragment, R.id.profileFragment)
        )

        //setup toolbar
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfig)

        // setup bottom nav
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.bottomNavigationView.setOnItemReselectedListener {
            return@setOnItemReselectedListener
        }

        //feeds icon badge badge
        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.homeFragment)
        badge.isVisible = true

        //set bottom nav color to invisible && disable middle icon on bottom nav
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false
        binding.bottomNavigationView.itemIconTintList = null

        binding.applyForLeave.setOnClickListener {
            startActivity(Intent(this, LeaveApplicationActivity::class.java))
            overridePendingTransition(R.anim.slide_in_bottom,  R.anim.slide_out_top)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        binding.bottomAppBar.visibility = View.VISIBLE
        binding.bottomAppBar.startAnimation(slideUp)
    }
}