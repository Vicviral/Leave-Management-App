package com.victorloveday.leavemanager.ui

import HistoryAdapter
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var historyAdapter: HistoryAdapter

    var toolbarMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        historyAdapter = HistoryAdapter(this)

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

        //set visibility of toolbar icons
        setVisibilityStateForToolbarIcons()

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

    private fun setVisibilityStateForToolbarIcons() {
        binding.bottomNavigationView.menu.findItem(R.id.homeFragment)?.setOnMenuItemClickListener {
            toolbarMenu?.findItem(R.id.searchLeaves)?.isVisible = false
            toolbarMenu?.findItem(R.id.searchNotifications)?.isVisible = false
            toolbarMenu?.findItem(R.id.filterLeaves)?.isVisible = false
            toolbarMenu?.findItem(R.id.deleteLeave)?.isVisible = false

            return@setOnMenuItemClickListener false
        }
        binding.bottomNavigationView.menu.findItem(R.id.historyFragment)?.setOnMenuItemClickListener {
            toolbarMenu?.findItem(R.id.searchLeaves)?.isVisible = true
            toolbarMenu?.findItem(R.id.searchNotifications)?.isVisible = false
            toolbarMenu?.findItem(R.id.filterLeaves)?.isVisible = true
            toolbarMenu?.findItem(R.id.deleteLeave)?.isVisible = false

            return@setOnMenuItemClickListener false
        }
        binding.bottomNavigationView.menu.findItem(R.id.notificationFragment)?.setOnMenuItemClickListener {
            toolbarMenu?.findItem(R.id.searchLeaves)?.isVisible = false
            toolbarMenu?.findItem(R.id.searchNotifications)?.isVisible = true
            toolbarMenu?.findItem(R.id.filterLeaves)?.isVisible = true
            toolbarMenu?.findItem(R.id.deleteLeave)?.isVisible = false

            return@setOnMenuItemClickListener false
        }
        binding.bottomNavigationView.menu.findItem(R.id.profileFragment)?.setOnMenuItemClickListener {
            toolbarMenu?.findItem(R.id.searchLeaves)?.isVisible = false
            toolbarMenu?.findItem(R.id.searchNotifications)?.isVisible = false
            toolbarMenu?.findItem(R.id.filterLeaves)?.isVisible = false
            toolbarMenu?.findItem(R.id.deleteLeave)?.isVisible = false

            return@setOnMenuItemClickListener false
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        toolbarMenu = menu
        return true
    }

//    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
//
//        return super.onPrepareOptionsMenu(menu)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
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
