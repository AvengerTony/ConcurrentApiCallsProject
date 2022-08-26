package com.hcl.concurrentapicalls.api

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.hcl.concurrentapicalls.utils.RequestType

data class ApiBean(
    val requestType: RequestType,
    val endPoint: String?,
    val url: String?,
    val id: String?,
    val queryParam: Map<String, String>?,
    val bodyJsonObject: JsonObject?,
    val bodyJsonArray: JsonArray?,
    var resultOfApi: JsonElement?
)