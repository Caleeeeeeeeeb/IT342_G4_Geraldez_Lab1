package com.example.miniapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth/login")
    fun login(@Body body: Map<String, String>): Call<Map<String, String>>

    @GET("api/auth/profile")
    fun getProfile(@Header("Authorization") token: String): Call<UserProfile>

    @POST("api/auth/register")
    fun register(@Body user: User): Call<User>
}