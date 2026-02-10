package com.example.miniapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DashboardActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var tvWelcome: TextView
    private lateinit var tvRole: TextView

    // Header Views (inside the menu)
    private lateinit var tvHeaderName: TextView
    private lateinit var tvHeaderRole: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // 1. Setup Toolbar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // 2. Link Variables
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        tvWelcome = findViewById(R.id.tvWelcome)
        tvRole = findViewById(R.id.tvRole)

        // 3. Access Header Views
        val headerView = navView.getHeaderView(0)
        tvHeaderName = headerView.findViewById(R.id.tvHeaderName)
        tvHeaderRole = headerView.findViewById(R.id.tvHeaderRole)

        // 4. Setup "Hamburger" Toggle
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // 5. Fetch Data
        fetchUserProfile()

        // 6. Handle Menu Clicks
        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_profile -> {
                    // Go to Profile Screen
                    startActivity(Intent(this, ProfileActivity::class.java))
                }
                R.id.nav_logout -> {
                    // Logout Logic
                    val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                    prefs.edit().clear().apply()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun fetchUserProfile() {
        val prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val token = prefs.getString("token", null)

        if (token == null) return

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.15:8080/") // <--- CHECK YOUR IP
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.getProfile("Bearer $token").enqueue(object : Callback<UserProfile> {
            override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                if (response.isSuccessful && response.body() != null) {
                    val user = response.body()!!

                    // Update Main Screen
                    tvWelcome.text = "Hello, ${user.username}!"
                    tvRole.text = "Role: ${user.role}"

                    // Update Drawer Header
                    tvHeaderName.text = user.username
                    tvHeaderRole.text = user.role
                }
            }

            override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                Toast.makeText(this@DashboardActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Close drawer if Back is pressed
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}