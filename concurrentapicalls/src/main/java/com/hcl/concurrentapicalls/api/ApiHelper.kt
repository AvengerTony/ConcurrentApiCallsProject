package com.hcl.concurrentapicalls.api


class ApiHelper(private val apiService: ApiService) {

    suspend fun getUsers() = apiService.getUsers()

    suspend fun getApiCallWithEndPont(apiBean: ApiBean) =
        apiService.getApiCallWithEndPont(endPoint = apiBean.endPoint!!)

}