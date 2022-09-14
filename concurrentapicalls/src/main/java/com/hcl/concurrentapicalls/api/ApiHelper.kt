package com.hcl.concurrentapicalls.api

import com.google.gson.JsonElement
import com.hcl.concurrentapicalls.utils.RequestTypeMoreDetail


class ApiHelper(private val apiService: ApiService) {

    suspend fun getApiCall() = apiService.getApiCall()

    suspend fun getApiCallWithEndPont(apiBean: ApiBean) = apiService.getApiCallWithEndPont(endPoint = apiBean.endPoint!!)

    suspend fun getApiCallWithEndPointNQueryMap(apiBean: ApiBean) = apiService.getApiCallWithEndPointNQueryMap(endPoint = apiBean.endPoint!!, queryMap = apiBean.queryParam!!)

    suspend fun getApiCallWithEndPointNPath(apiBean: ApiBean) = apiService.getApiCallWithEndPointNPath(endPoint = apiBean.endPoint!!,id = apiBean.requesPathString!!)

    suspend fun getApiCallOnlyWithQueryMap(apiBean: ApiBean) = apiService.getApiCallWithParamQueryMap(queryMap = apiBean.queryParam!!)

    fun postApiCallWithEndPont(apiBean: ApiBean) =
        apiService.postApiCallWithEndPont(endPoint = apiBean.endPoint!!, body = null)

}