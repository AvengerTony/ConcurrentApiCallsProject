package com.hcl.concurrentapicalls.repository

import android.util.Log
import com.google.gson.JsonElement
import com.hcl.concurrentapicalls.api.ApiBean
import com.hcl.concurrentapicalls.api.ApiHelper
import com.hcl.concurrentapicalls.utils.RequestKey
import com.hcl.concurrentapicalls.utils.RequestType
import com.hcl.concurrentapicalls.utils.RequestTypeMoreDetail
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun returnApiResults(
        apiCallList: Map<RequestKey, ApiBean>,
        result: (resultOfCalls: Map<RequestKey, ApiBean>) -> Unit
    ) {
        val time2 = measureTimeMillis {
            CoroutineScope(Dispatchers.IO).launch {

                val response = mutableMapOf<RequestKey, Deferred<JsonElement?>>()

                apiCallList.forEach { (key, apiBean) ->
                    apiBean.let { data ->
                        when (data.requestType) {
                            RequestType.GET -> {
                                val time1 = measureTimeMillis {
                                    val results = async {
                                        when (apiBean.requestTypeMoreDetail) {
                                            RequestTypeMoreDetail.GetRequestType.GET_CALL_SIMPLE -> {
                                                apiHelper.getApiCall()
                                            }
                                            RequestTypeMoreDetail.GetRequestType.GET_CALL_WITH_ENDPOINT -> {
                                                apiHelper.getApiCallWithEndPont(apiBean = apiBean)

                                            }
                                            RequestTypeMoreDetail.GetRequestType.GET_CALL_WITH_ENDPOINT_WITH_QUERYMAP -> {
                                                apiHelper.getApiCallWithEndPointNQueryMap(apiBean)


                                            }
                                            RequestTypeMoreDetail.GetRequestType.GET_CALL_ONLY_QUERYMAP -> {
                                                apiHelper.getApiCallOnlyWithQueryMap(apiBean)
                                            }
                                         RequestTypeMoreDetail.GetRequestType.GET_CALL_WITH_ENDPOINT_WITH_PATH -> {
                                                apiHelper.getApiCallWithEndPointNPath(apiBean)
                                            }

                                            else -> {apiHelper.getApiCallWithEndPont(apiBean)}
                                        }

                                    }
                                    response[key] = results
                                }

                                println("debug: compeleted job$key in $time1 ms." + Thread.currentThread().name)
                            }
                            RequestType.POST -> {
                                val time1 = measureTimeMillis {
                                    val results = async {
                                        when (apiBean.requestTypeMoreDetail) {
                                            RequestTypeMoreDetail.PostRequestType.POST_CALL_WITH_ENDPOINT_WITH_JSONOBJECT ->
                                            {
                                                apiHelper.postApiCallWithEndPontNJsonObjectBody(apiBean)
                                            }
                                            RequestTypeMoreDetail.PostRequestType.POST_CALL_WITH_ENDPOINT_WITH_JSONARRAY ->
                                            {
                                                apiHelper.postApiCallWithEndPontNJsonArrayBody(apiBean)
                                            }
                                            RequestTypeMoreDetail.PostRequestType.POST_CALL_WITH_ENDPOINT_WITH_FIELDMAP ->
                                            {
                                                apiHelper.postApiCallWithEndPontNFieldMap(apiBean)
                                            }
                                            else -> {apiHelper.postApiCallWithEndPont(apiBean)}
                                        }

                                    }
                                    response[key] = results
                                }

                                println("debug: compeleted job$key in $time1 ms." + Thread.currentThread().name)

                            }
                            RequestType.PUT -> {
                                val time1 = measureTimeMillis {
                                    val results = async {
                                        when (apiBean.requestTypeMoreDetail) {
                                            RequestTypeMoreDetail.PutRequestType.PUT_CALL_WITH_ENDPOINT_WITH_JSONOBJECT ->
                                            {
                                                apiHelper.putApiCallWithEndPontNJsonObjectBody(apiBean)
                                            }
                                             else -> {
                                                 apiHelper.putApiCallWithEndPontNJsonObjectBody(apiBean)
                                             }
                                        }

                                    }
                                    response[key] = results
                                }

                                println("debug: compeleted job$key in $time1 ms." + Thread.currentThread().name)

                            }
                        }
                    }


                }
                apiCallList.forEach { (key, _) ->
                    try {
                        val resultData = response[key]?.await()
                        println("response=$key: $resultData")
                        apiCallList[key].let { it?.resultOfApi = resultData }
                    } catch (e: Exception) {
                        Log.e("Error", e.toString())
                    }
                }
                result(apiCallList)
            }.join()

        }
        println("debug: compeleted final job in $time2 ms." + Thread.currentThread().name)

    }
}