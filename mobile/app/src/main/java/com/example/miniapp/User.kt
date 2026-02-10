package com.example.miniapp

data class User(
    val username: String,
    val password: String,
    val role: String = "USER" // Default to USER
)