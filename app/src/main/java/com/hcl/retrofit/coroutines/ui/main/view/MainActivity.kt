package com.hcl.retrofit.coroutines.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import com.hcl.concurrentapicalls.api.ApiBean
import com.hcl.concurrentapicalls.api.ApiHelper
import com.hcl.concurrentapicalls.api.BASE_URL
import com.hcl.concurrentapicalls.api.RetrofitBuilder
import com.hcl.concurrentapicalls.base.ViewModelFactory
import com.hcl.concurrentapicalls.utils.ApiResponse
import com.hcl.concurrentapicalls.utils.RequestKey
import com.hcl.concurrentapicalls.utils.RequestType
import com.hcl.concurrentapicalls.utils.RequestTypeMoreDetail
import com.hcl.concurrentapicalls.viewmodel.MainViewModel
import com.hcl.retrofit.coroutines.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ApiResponse {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupObservers()
    }


    private fun setupViewModel() {
        addBaseUrl()    //Add Base URL
        addHeaders()   //Add Headers If Require


        //Initializing retrofit ViewModel
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(this, ApiHelper(RetrofitBuilder.apiService))
        )[MainViewModel::class.java]

    }


    /*--------- Setting Base URL----------*/
    private fun addBaseUrl() {
        BASE_URL = "https://6319f70d8e51a64d2bf1e612.mockapi.io/"
        //BASE_URL = "https://reqres.in/"
    }

    /*--------- Setting Headers----------*/
    private fun addHeaders() {
        //Add Headers if require
        val headerMap = "Accept: application/json\n" +
                "User-Agent: Your-App-Name\n" +
                "Cache-Control: max-age=640000"
        RetrofitBuilder.setHeader(headerMap)
    }

    @SuppressLint("NewApi")
    private fun setupObservers() {
        val apiMap = mutableMapOf<RequestKey, ApiBean>()  //List of Request Api's concurrently


        //Getting users list, GET Api which need only endPoint if is not then we can pass empty as well
        apiMap[RequestKey.REQUEST_FIRST] = ApiBean(
            requestType = RequestType.GET,
            requestTypeMoreDetail = RequestTypeMoreDetail.GetRequestType.GET_CALL_WITH_ENDPOINT,
            endPoint = "users",
            url = null,
            requestPathString = null,
            paramMap = null,
            bodyJsonObject = null,
            bodyJsonArray = null,
            resultOfApi = null
        )
        /*Data class (ApiBean) used to get the basic info about api and its parameters*/


        //Getting blogs list, Get Api with different endPoint if is not then we can pass empty as well
        apiMap[RequestKey.REQUEST_SECOND] = ApiBean(
            requestType = RequestType.GET,
            requestTypeMoreDetail = RequestTypeMoreDetail.GetRequestType.GET_CALL_WITH_ENDPOINT,
            endPoint = "blogs",
            url = null,
            requestPathString = null,
            paramMap = null,
            bodyJsonObject = null,
            bodyJsonArray = null,
            resultOfApi = null
        )

        //Getting User which have id 1,GET Api which needs endPoint and path string
        apiMap[RequestKey.REQUEST_THIRD] = ApiBean(
            requestType = RequestType.GET,
            requestTypeMoreDetail = RequestTypeMoreDetail.GetRequestType.GET_CALL_WITH_ENDPOINT_WITH_PATH,
            endPoint = "users",
            url = null,
            requestPathString = "1",
            paramMap = null,
            bodyJsonObject = null,
            bodyJsonArray = null,
            resultOfApi = null
        )

        //Get Api which needs endPoint and query parameters
        val queryMap = mapOf("page" to "1", "limit" to "10")
        apiMap[RequestKey.REQUEST_FOURTH] = ApiBean(
            requestType = RequestType.GET,
            requestTypeMoreDetail = RequestTypeMoreDetail.GetRequestType.GET_CALL_WITH_ENDPOINT_WITH_QUERYMAP,
            endPoint = "users",
            url = null,
            requestPathString = null,
            paramMap = queryMap,
            bodyJsonObject = null,
            bodyJsonArray = null,
            resultOfApi = null
        )


        val jsonObject = JsonObject()
        jsonObject.addProperty("name", "Vinay")
        jsonObject.addProperty("job", "engineer")

        apiMap[RequestKey.REQUEST_FIFTH] = ApiBean(
            requestType = RequestType.POST,
            requestTypeMoreDetail = RequestTypeMoreDetail.PostRequestType.POST_CALL_WITH_ENDPOINT_WITH_JSON,
            endPoint = "https://reqres.in/api/users",
            url = null,
            requestPathString = null,
            paramMap = null,
            bodyJsonObject = jsonObject,
            bodyJsonArray = null,
            resultOfApi = null
        )

        val fieldMap = mapOf("email" to "eve.holt@reqres.in", "password" to "pistol")
      /*  apiMap[RequestKey.REQUEST_SIX] = ApiBean(
            requestType = RequestType.POST,
            requestTypeMoreDetail = RequestTypeMoreDetail.PostRequestType.POST_CALL_WITH_ENDPOINT_WITH_FIELDMAP,
            endPoint = "https://reqres.in/api/register",
            url = null,
            requestPathString = null,
            paramMap = fieldMap,
            bodyJsonObject = null,
            bodyJsonArray = null,
            resultOfApi = null
        )*/

        viewModel.doMultipleAPICalls(apiMap)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun retrieveList(listWithResponse: Map<RequestKey, ApiBean>) {

        // To parsing data we can use toJsonParsing() function

        val data =
            listWithResponse[RequestKey.REQUEST_SIX]?.resultOfApi  // getting json data in response

        jsonData.visibility = View.VISIBLE
        jsonData.text = data.toString()
    }

    //Provide data class in place of MODEL_CLASS
    /* private fun toJsonParsing(json: JsonElement?): List<MODEL_CLASS?>? {
         return try {
             val collectionType: Type = object : TypeToken<List<MODEL_CLASS?>?>() {}.type
             Gson().fromJson(json, collectionType)
         } catch (e:Exception) {
             e.printStackTrace()
              null
         }
     }*/


    /*---------Combine Result of All Api's----------*/
    override fun onSuccessApiResult(listWithResponse: Map<RequestKey, ApiBean>) {
        recyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        retrieveList(listWithResponse)
    }

    /*---------Error Result of All Api's----------*/
    override fun onErrorApiResult() {
        recyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    /*---------Loading/Progressing of All Api's----------*/
    override fun onLoadingApiResult() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }
}
