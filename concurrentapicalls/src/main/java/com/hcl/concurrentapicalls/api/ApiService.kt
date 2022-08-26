package com.hcl.concurrentapicalls.api

import com.google.gson.JsonElement
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<Any>

    @GET
    suspend fun getApiCallWithEndPont(@Url endPoint: String): JsonElement

    //@GET
    //suspend fun getApiCallWithID(@Url endPoint:String, @Path("id") id: String): Any
}