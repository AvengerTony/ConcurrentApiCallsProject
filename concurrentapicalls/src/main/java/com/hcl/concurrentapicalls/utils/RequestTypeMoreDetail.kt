package com.hcl.concurrentapicalls.utils

enum class RequestTypeMoreDetail {
    ;
    enum class GetRequestType {
        GET_CALL_SIMPLE,
        GET_CALL_WITH_ENDPOINT,
        GET_CALL_WITH_ENDPOINT_WITH_QUERYMAP,
        GET_CALL_ONLY_QUERYMAP,
        GET_CALL_WITH_ENDPOINT_WITH_PATH
    }
    enum class PostRequestType {
        POST_CALL_WITH_ENDPOINT_WITH_JSONOBJECT,
        POST_CALL_WITH_ENDPOINT_WITH_JSONARRAY,
        POST_CALL_WITH_ENDPOINT_WITH_FIELDMAP
    }
   enum class PutRequestType {
       PUT_CALL_WITH_ENDPOINT_WITH_JSONOBJECT,
    }
}