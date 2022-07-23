package com.victorloveday.leavemanager.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.victorloveday.leavemanager.api.RetrofitInstance
import com.victorloveday.leavemanager.database.UserInfoManager
import com.victorloveday.leavemanager.database.model.LoginResponse
import com.victorloveday.leavemanager.databinding.ActivityLoginBinding
import com.victorloveday.leavemanager.ui.MainActivity
import com.victorloveday.leavemanager.ui.viewmodels.EmployeesViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var response: Response<LoginResponse>
    private lateinit var userInfoManager: UserInfoManager
    private lateinit var employeeViewModel: EmployeesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        employeeViewModel = ViewModelProvider(this).get(EmployeesViewModel::class.java)
        userInfoManager = UserInfoManager(this)

        userInfoManager.onLoginFlow.asLiveData().observe(this, {
            if (it == true) {
//                startActivity(Intent(this, MainActivity::class.java))
//                finish()
            }
        })

        binding.loginBtn.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields() {
        val userId = binding.userId.text.toString()
        val password = binding.password.text.toString()

        if (userId.isNotEmpty() || password.isNotEmpty()) {
//            loginUser(userId, password)
            loginUserLocally(userId, password)
        } else {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginUserLocally(userId: String, password: String) {

        employeeViewModel.getEmployeeByUserIdAndPassword(userId, password).observe(this, {
            if (it != null) {
                if (it.userId == userId && it.password == password) {
                    GlobalScope.launch {
                        userInfoManager.storeUser(it.name, it.userId, it.age, it.role, it.userType, it.gender, it.nationality, it.isOnLeave, it.isDeactivated)
                        userInfoManager.saveLoginStatus(true)
                    }
                    Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }else {
                Toast.makeText(this, "$userId does not exist", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun loginUser(userId: String, password: String) {
        lifecycleScope.launchWhenCreated {
            response = try {
                RetrofitInstance.api.loginUser(userId, password)

            } catch (e: IOException) {
                Toast.makeText(this@LoginActivity, "Check your internet and try again...", Toast.LENGTH_SHORT).show()

                return@launchWhenCreated
            } catch (e: HttpException) {
                Toast.makeText(this@LoginActivity, "Check your internet and try again...", Toast.LENGTH_SHORT).show()

                return@launchWhenCreated
            } catch (e: SocketTimeoutException) {
                Toast.makeText(this@LoginActivity, "Check your internet and try again...", Toast.LENGTH_SHORT).show()

                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {

                if (response.body()!!.status == 1) {
                    //upsert user info to local data store
                    val result = response.body()!!.info

                    val name = result.name
                    val userID = result.userId
                    val role = result.role
                    val age = result.age
                    val userType = result.userType
                    val gender = result.gender
                    val nationality = result.nationality
                    val isOnLeave = result.isOnLeave
                    val isDeactivated = result.isDeactivated

                    userInfoManager.storeUser(name, userID, age, role,userType, gender, nationality, isOnLeave, isDeactivated)

                    //navigate to main app
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()

                } else {
                    Toast.makeText(this@LoginActivity, "Failed", Toast.LENGTH_SHORT).show()
                }

            } else { Toast.makeText(this@LoginActivity, "Failed: ${response.body()?.status}", Toast.LENGTH_SHORT).show()

                return@launchWhenCreated
            }

        }

    }
}