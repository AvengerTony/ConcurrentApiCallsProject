package com.hcl.concurrentapicalls.repository

import android.util.Log
import com.google.gson.JsonElement
import com.hcl.concurrentapicalls.api.ApiBean
import com.hcl.concurrentapicalls.api.ApiHelper
import com.hcl.concurrentapicalls.utils.RequestKey
import com.hcl.concurrentapicalls.utils.RequestType
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()

    suspend fun returnSomeItems(
        apiCallList: Map<RequestKey, ApiBean>,
        result: (resultOfCalls: Map<RequestKey, ApiBean>) -> Unit
    ) {
        val time2 = measureTimeMillis {
            CoroutineScope(Dispatchers.IO).launch {

                val response = mutableMapOf<RequestKey, Deferred<JsonElement>>()

                apiCallList.forEach { (key, apiBean) ->
                    apiBean.let { data ->
                        when (data.requestType) {
                            RequestType.GET -> {
                                val time1 = measureTimeMillis {
                                    val results = async {
                                        apiHelper.getApiCallWithEndPont(apiBean)
                                    }
                                    response[key] = results
                                }

                                println("debug: compeleted job$key in $time1 ms." + Thread.currentThread().name)
                            }
                            RequestType.POST -> {

                            }
                            RequestType.MULTIPART -> {

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