package com.hcl.concurrentapicalls.api

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.hcl.concurrentapicalls.utils.RequestType
import com.hcl.concurrentapicalls.utils.RequestTypeMoreDetail

/*Data class to get the info about api and its parameters*/
data class ApiBean(
    val requestType: RequestType,
    val requestTypeMoreDetail: Any,
    val endPoint: String?,
    val url: String?,
    val requestPathString: String?,
    val paramMap: Map<String, String>?,
    val bodyJsonObject: JsonObject?,
    val bodyJsonArray: JsonArray?,
    var resultOfApi: JsonElement?
)