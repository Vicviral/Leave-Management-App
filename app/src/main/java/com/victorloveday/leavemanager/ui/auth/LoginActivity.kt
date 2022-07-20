package com.victorloveday.leavemanager.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.victorloveday.leavemanager.api.RetrofitInstance
import com.victorloveday.leavemanager.database.model.LoginResponse
import com.victorloveday.leavemanager.databinding.ActivityLoginBinding
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var response: Response<LoginResponse>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields() {
        val userId = binding.userId.text.toString()
        val password = binding.password.text.toString()

        if (userId.isNotEmpty() || password.isNotEmpty()) {
            loginUser(userId, password)
        } else {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginUser(userId: String, password: String) {
        lifecycleScope.launchWhenCreated {
            response = try {
                RetrofitInstance.api.loginUser("5", "12345")

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
                    //upsert response to local data base
                    val result = response.body()!!.info

                } else {
                    Toast.makeText(this@LoginActivity, "Failed", Toast.LENGTH_SHORT).show()
                }

            } else { Toast.makeText(this@LoginActivity, "Failed: ${response.body()?.status}", Toast.LENGTH_SHORT).show()

                return@launchWhenCreated
            }

        }

    }
}