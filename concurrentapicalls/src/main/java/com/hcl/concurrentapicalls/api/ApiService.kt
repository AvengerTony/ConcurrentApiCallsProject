package com.hcl.concurrentapicalls.api

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.*
import java.util.*


interface ApiService {

    @GET
    suspend fun getApiCallWithEndPont(@Url endPoint: String): JsonElement

    @GET
    suspend fun getApiCall(): JsonElement

    @GET
    suspend fun getApiCallWithParamQueryMap(@QueryMap queryMap:Map<String,String>): JsonElement

    @GET
    suspend fun getApiCallWithEndPointNQueryMap(@Url endPoint: String,@QueryMap queryMap:Map<String,String>): JsonElement

    @GET("{endPoint}/{id}")
    suspend fun getApiCallWithEndPointNPath(@Path("endPoint") endPoint: String,@Path("id") id: String): JsonElement



    @POST
    fun postApiCallWithEndPont(@Url endPoint: String,@Body body: Objects?): JsonElement


//    @FormUrlEncoded
//    @POST
//    fun someEndpoint(@Url endPoint: String,@FieldMap names: Map<String?, String?>?): Call<JsonElement?>?


    @Headers("Content-Type: application/json")
    @POST("users")
    fun addUser(@Body userData: Objects): Call<Objects>

    //@GET
    //suspend fun getApiCallWithID(@Url endPoint:String, @Path("id") id: String): Any
}