package com.victorloveday.leavemanager.ui

import HistoryAdapter
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.victorloveday.leavemanager.R
import com.victorloveday.leavemanager.api.RetrofitInstance
import com.victorloveday.leavemanager.database.UserInfoManager
import com.victorloveday.leavemanager.database.model.EmployeesResponse
import com.victorloveday.leavemanager.database.model.LeaveApplicationResponse
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
    private lateinit var addEmployeeResponse: Response<LeaveApplicationResponse>

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

        binding.addEmployeeBS.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.create_employee_bottom_sheet, null)
            dialog.setCancelable(false)
            dialog.setContentView(view)
            dialog.show()

            view.findViewById<LinearLayout>(R.id.addEmployee).setOnClickListener {
                addEmployeeToRemoteServer(dialog, view)
            }
            view.findViewById<LinearLayout>(R.id.cancelAddEmployee).setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    private fun addEmployeeToRemoteServer(dialog: BottomSheetDialog, view: View) {
        val fullName = view.findViewById<EditText>(R.id.fullName_emp).text.toString()
        val role = view.findViewById<EditText>(R.id.role_emp).text.toString()
        val age = view.findViewById<EditText>(R.id.age_emp).text.toString()
        val gender = view.findViewById<EditText>(R.id.gender_emp).text.toString()
        val nationality = view.findViewById<EditText>(R.id.nationality_emp).text.toString()
        val userId = view.findViewById<EditText>(R.id.userId_emp).text.toString()
        val password = view.findViewById<EditText>(R.id.password_emp).text.toString()

        if (fullName.isNotEmpty() || role.isNotEmpty() || age.isNotEmpty() || gender.isNotEmpty() || nationality.isNotEmpty() || password.isNotEmpty() || userId.isNotEmpty()) {
            //for demo purpose
//            val emp1 = User(0, fullName, userId, age, role, "Employee" , false, false)
//            val emp2 = User(1, "Sani Umar", "sani123", "34", "Sales Manager", "Employee" , true, false)
//            val emp3 = User(2, "Bagi Joseph", "bagi123", "22", "Social Media Manager", "Employee" , false, false)
//            val emps : ArrayList<User> = arrayListOf(emp1, emp2, emp3)
//            employeesViewModel.saveEmployees(emps)
//            dialog.dismiss()

//            lifecycleScope.launchWhenCreated {
//                addEmployeeResponse = try {
//                    RetrofitInstance.api.addEmployee(
//                        "",
//                        fullName,
//                        role,
//                        age,
//                        gender,
//                        nationality,
//                        userId,
//                        password
//                    )
//
//                } catch (e: IOException) {
//                    Toast.makeText(this@EmployeeActivity, "Check your internet and try again...", Toast.LENGTH_SHORT).show()
//
//                    return@launchWhenCreated
//                } catch (e: HttpException) {
//                    Toast.makeText(this@EmployeeActivity, "Check your internet and try again...", Toast.LENGTH_SHORT).show()
//
//                    return@launchWhenCreated
//                } catch (e: SocketTimeoutException) {
//                    Toast.makeText(this@EmployeeActivity, "Check your internet and try again...", Toast.LENGTH_SHORT).show()
//
//                    return@launchWhenCreated
//                }
//
//                if (addEmployeeResponse.isSuccessful && addEmployeeResponse.body() != null) {
//
//                    if (addEmployeeResponse.body()!!.status == 1) {
//                        //leave has been successfully removed from remote server
//                        //remove leave from local db
//                        dialog.dismiss()
//                        Toast.makeText(this@EmployeeActivity, "Successfully Added", Toast.LENGTH_SHORT).show()
//
//                    } else {
//                        Toast.makeText(this@EmployeeActivity, "Failed ${addEmployeeResponse.body()}", Toast.LENGTH_SHORT).show()
//                    }
//
//                } else {
//                    Toast.makeText(this@EmployeeActivity, "Failed: ${addEmployeeResponse.body()?.status}", Toast.LENGTH_SHORT).show()
//
//                    return@launchWhenCreated
//                }
//
//            }

        }else {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
        }
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