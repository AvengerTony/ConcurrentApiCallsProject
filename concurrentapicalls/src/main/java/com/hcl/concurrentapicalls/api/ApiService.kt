package com.hcl.concurrentapicalls.api

import com.google.gson.JsonElement
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*


interface ApiService {

    @GET
    suspend fun getApiCallWithEndPont(@Url endPoint: String): JsonElement

    @GET
    suspend fun getApiCall(): JsonElement

    @GET
    suspend fun getApiCallWithParamQueryMap(@QueryMap queryMap: Map<String, String>): JsonElement

    @GET
    suspend fun getApiCallWithEndPointNQueryMap(
        @Url endPoint: String,
        @QueryMap queryMap: Map<String, String>
    ): JsonElement

    @GET("{endPoint}/{id}")
    suspend fun getApiCallWithEndPointNPath(
        @Path("endPoint") endPoint: String,
        @Path("id") id: String
    ): JsonElement

    @POST
    suspend fun postApiCallWithEndPont(
        @Url endPoint: String,
    ): JsonElement

    @POST
    suspend fun postApiCallWithEndPontNJsonBody(
        @Url endPoint: String,
        @Body body: JsonElement?
    ): JsonElement

    @FormUrlEncoded
    @POST
    suspend fun postApiCallWithEndPontNFieldMap(
        @Url endPoint: String,
        @FieldMap fieldMap: Map<String, String>
    ): JsonElement

    @PUT
    suspend fun putApiCallWithEndPontNJsonObjectBody(
        @Url endPoint: String,
        @Body requestBody: JsonElement
    ): JsonElement

 @PUT
    suspend fun putApiCallWithEndPontNJsonArrayBody(
        @Url endPoint: String,
        @Body requestBody: JsonElement
    ): JsonElement


}