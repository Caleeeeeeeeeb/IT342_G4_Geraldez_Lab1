package com.example.miniapp // Make sure this matches your folder

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // this is for registration.
        val tvRegisterLink = findViewById<android.widget.TextView>(R.id.tvRegisterLink)

        tvRegisterLink.setOnClickListener {
            val intent = android.content.Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // 1. Link Variables
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        // 2. Setup Retrofit
        val retrofit = Retrofit.Builder()
            // CHANGE THIS LINE: Use the IP you found (192.168.1.15)
            .baseUrl("http://192.168.1.15:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)



        // 3. Button Click Listener
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(apiService, username, password)
            }
        }
    }

    private fun loginUser(apiService: ApiService, username: String, password: String) {
        val loginRequest = mapOf("username" to username, "password" to password)

        apiService.login(loginRequest).enqueue(object : Callback<Map<String, String>> {
            override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()!!["token"]

                    val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                    prefs.edit().putString("token", token).apply()


                    val intent = android.content.Intent(this@MainActivity, DashboardActivity::class.java)


                    startActivity(intent)


                    finish()
                    Toast.makeText(this@MainActivity, "Login Successful! Token saved.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@MainActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}