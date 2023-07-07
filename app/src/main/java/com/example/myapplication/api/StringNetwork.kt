package com.example.myapplication.api

import com.example.myapplication.model.LoginBody
import com.example.myapplication.model.LoginResponse
import com.example.myapplication.model.StringResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface StringNetwork {
    @POST("v1/auth/user/signin")
    suspend fun doLogin(@Body body: LoginBody): LoginResponse

    @GET("v1/db/data/noco/p_yzjjxsypdoz7gc/strings/views/table-res")
    suspend fun fetch(
        @Header("xc-auth") token: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("where") where: String
    ): StringResponse
}
