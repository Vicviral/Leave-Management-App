package com.victorloveday.leavemanager.ui

import HistoryAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.victorloveday.leavemanager.R
import com.victorloveday.leavemanager.api.RetrofitInstance
import com.victorloveday.leavemanager.database.UserInfoManager
import com.victorloveday.leavemanager.database.model.EmployeesResponse
import com.victorloveday.leavemanager.database.model.User
import com.victorloveday.leavemanager.databinding.ActivityEmployeeBinding
import com.victorloveday.leavemanager.ui.viewmodels.EmployeesViewModel
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class EmployeeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployeeBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var userInfoManager: UserInfoManager
    private lateinit var employeesViewModel: EmployeesViewModel
    private lateinit var employeesResponse: Response<EmployeesResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        historyAdapter = HistoryAdapter(this)
        userInfoManager = UserInfoManager(this)
        employeesViewModel = ViewModelProvider(this).get(EmployeesViewModel::class.java)

        //setup nav host
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.adminNavHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        //setup app bar configuration
        appBarConfig = AppBarConfiguration(
            setOf(R.id.homeFragment)
        )

        //setup toolbar
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupActionBarWithNavController(navController, appBarConfig)


        fetchAllEmployeesDataFromRemoteServer()
    }

    private fun fetchAllEmployeesDataFromRemoteServer() {
        lifecycleScope.launchWhenCreated {
            employeesResponse = try {
                RetrofitInstance.api.getEmployees("employees", "5")

            } catch (e: IOException) {
                Toast.makeText(this@EmployeeActivity, "Check your internet and try again...", Toast.LENGTH_SHORT).show()

                return@launchWhenCreated
            } catch (e: HttpException) {
                Toast.makeText(this@EmployeeActivity, "Check your internet and try again...", Toast.LENGTH_SHORT).show()

                return@launchWhenCreated
            } catch (e: SocketTimeoutException) {
                Toast.makeText(this@EmployeeActivity, "Check your internet and try again...", Toast.LENGTH_SHORT).show()

                return@launchWhenCreated
            }

            if (employeesResponse.isSuccessful && employeesResponse.body() != null) {

                if (employeesResponse.body()!!.status == 1) {
                    //upsert response to local data base
                    val result = employeesResponse.body()!!.info
                    saveEmployeesToDatabase(result)

                } else {
                    Toast.makeText(this@EmployeeActivity, "Failed ${employeesResponse.body()}", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this@EmployeeActivity, "Failed: ${employeesResponse.body()?.status}", Toast.LENGTH_SHORT).show()

                return@launchWhenCreated
            }

        }

    }

    private fun saveEmployeesToDatabase(employees: List<User>) {
        employeesViewModel.saveEmployees(employees)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        overridePendingTransition(0, R.anim.slide_out_bottom)
        return true
    }

}