package com.example.miniapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etUsername = findViewById<EditText>(R.id.etRegUsername)
        val etPassword = findViewById<EditText>(R.id.etRegPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvLoginLink = findViewById<TextView>(R.id.tvLoginLink)

        // 1. Setup Retrofit (SAME IP ADDRESS)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.15:8080/") // <--- CHECK YOUR IP!
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)

        // 2. Register Button Click
        btnRegister.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create User Object
            val newUser = User(username, password)

            // Call API
            apiService.register(newUser).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@RegisterActivity, "Account Created! Please Login.", Toast.LENGTH_LONG).show()
                        finish() // Go back to Login Screen
                    } else {
                        Toast.makeText(this@RegisterActivity, "Registration Failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // 3. Go back to Login if clicked
        tvLoginLink.setOnClickListener {
            finish()
        }
    }
}