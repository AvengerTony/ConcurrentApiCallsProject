package com.hcl.concurrentapicalls.utils

import com.hcl.concurrentapicalls.api.ApiBean

interface ApiResponse {
    fun onSuccessApiResult(listWithResponse: Map<RequestKey, ApiBean>)
    fun onErrorApiResult()
    fun onLoadingApiResult()
}