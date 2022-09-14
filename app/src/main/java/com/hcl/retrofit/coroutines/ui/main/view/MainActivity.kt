package com.hcl.retrofit.coroutines.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonElement
import com.hcl.concurrentapicalls.api.ApiBean
import com.hcl.concurrentapicalls.api.ApiHelper
import com.hcl.concurrentapicalls.api.BASE_URL
import com.hcl.concurrentapicalls.api.RetrofitBuilder
import com.hcl.concurrentapicalls.utils.ApiResponse
import com.hcl.concurrentapicalls.utils.RequestKey
import com.hcl.concurrentapicalls.utils.RequestType
import com.hcl.concurrentapicalls.utils.RequestTypeMoreDetail
import com.hcl.retrofit.coroutines.R
import com.hcl.retrofit.coroutines.data.model.User
import com.hcl.retrofit.coroutines.ui.base.ViewModelFactory
import com.hcl.retrofit.coroutines.ui.main.adapter.MainAdapter
import com.hcl.retrofit.coroutines.ui.main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), ApiResponse {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupUI()
        setupObservers()
    }


    private fun setupViewModel() {
        BASE_URL = "https://6319f70d8e51a64d2bf1e612.mockapi.io/"  //Add Base URL

        //Add Headers if require
        val headerMap= "Accept: application/json\n" +
               "User-Agent: Your-App-Name\n" +
               "Cache-Control: max-age=640000"
        RetrofitBuilder.setHeader(headerMap)

        //Initializing retrofit ViewModel
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(this, ApiHelper(RetrofitBuilder.apiService))
        )[MainViewModel::class.java]

    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }


    @SuppressLint("NewApi")
    private fun setupObservers() {

        val apiMap = mutableMapOf<RequestKey, ApiBean>()  //List of Request Api's concurrently

        //Get Api which need only endPoint if is not then we can pass empty as well
        apiMap[RequestKey.REQUEST_FIRST] = ApiBean(
            requestType = RequestType.GET,
            requestTypeMoreDetail = RequestTypeMoreDetail.GET_CALL_WITH_ENDPOINT,
            endPoint = "users",
            url = null,
            requesPathString = null,
            queryParam = null,
            bodyJsonObject = null,
            bodyJsonArray = null,
            resultOfApi = null
        )

        //Get Api with different endPoint if is not then we can pass empty as well
        apiMap[RequestKey.REQUEST_SECOND] = ApiBean(
            requestType = RequestType.GET,
            requestTypeMoreDetail = RequestTypeMoreDetail.GET_CALL_WITH_ENDPOINT,
            endPoint = "blogs",
            url = null,
            requesPathString = null,
            queryParam = null,
            bodyJsonObject = null,
            bodyJsonArray = null,
            resultOfApi = null
        )

        //Get Api which needs endPoint and path string
        apiMap[RequestKey.REQUEST_THIRD] = ApiBean(
            requestType = RequestType.GET,
            requestTypeMoreDetail = RequestTypeMoreDetail.GET_CALL_WITH_ENDPOINT_WITH_PATH,
            endPoint = "users",
            url = null,
            requesPathString = "1",
            queryParam = null,
            bodyJsonObject = null,
            bodyJsonArray = null,
            resultOfApi = null
        )

        //Get Api which needs endPoint and query parameters
        val queryMap = mapOf("page" to "1", "limit" to "10")
        apiMap[RequestKey.REQUEST_FOURTH] = ApiBean(
            requestType = RequestType.GET,
            requestTypeMoreDetail = RequestTypeMoreDetail.GET_CALL_WITH_ENDPOINT_WITH_QUERYMAP,
            endPoint = "users",
            url = null,
            requesPathString = null,
            queryParam = queryMap,
            bodyJsonObject = null,
            bodyJsonArray = null,
            resultOfApi = null
        )

        val jsonObject = JSONObject()
        jsonObject.put("name", "Vinay")
        jsonObject.put("email", "vinay@gmail.com")

        apiMap[RequestKey.REQUEST_FIFTH] = ApiBean(
            requestType = RequestType.POST,
            requestTypeMoreDetail = RequestTypeMoreDetail.POST_CALL_WITH_ENDPOINT_WITH_JSONOBJECT,
            endPoint = "users",
            url = null,
            requesPathString = null,
            queryParam = queryMap,
            bodyJsonObject = jsonObject,
            bodyJsonArray = null,
            resultOfApi = null
        )


        viewModel.doMultipleAPICalls(apiMap)
        /* .observe(this, Observer {
         it?.let { resource ->
             when (resource.status) {
                 SUCCESS -> {
                     recyclerView.visibility = View.VISIBLE
                     progressBar.visibility = View.GONE
                     resource.data?.let { users ->
                         retrieveList(users)
                     }
                 }
                 ERROR -> {
                     recyclerView.visibility = View.VISIBLE
                     progressBar.visibility = View.GONE
                     Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                 }
                 LOADING -> {
                     progressBar.visibility = View.VISIBLE
                     recyclerView.visibility = View.GONE
                 }
             }
         }
     })*/

        /* viewModel.getUsers().observe(this, Observer {
             it?.let { resource ->
                when (resource.status) {
                     SUCCESS -> {
                         recyclerView.visibility = View.VISIBLE
                         progressBar.visibility = View.GONE
                         resource.data?.let { users  -> retrieveList(users ) }
                     }
                     ERROR -> {
                         recyclerView.visibility = View.VISIBLE
                         progressBar.visibility = View.GONE
                         Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                     }
                     LOADING -> {
                         progressBar.visibility = View.VISIBLE
                         recyclerView.visibility = View.GONE
                     }
                 }
             }
         })*/
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun retrieveList(listWithResponse: Map<RequestKey, ApiBean>) {
        val data = toRpcResult(listWithResponse[RequestKey.REQUEST_SECOND]?.resultOfApi)
        /*if (data != null && data is List<*>) {
            jsonData.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE

            adapter.apply {
                addUsers(data as List<User>)
                notifyDataSetChanged()
            }

        } else {*/
            jsonData.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            jsonData.text = data.toString()
       // }
    }

    private fun toRpcResult(json: JsonElement?): Any? {
        /* try {
            val collectionType: Type = object : TypeToken<List<User?>?>() {}.type
             return  Gson().fromJson(json, collectionType)
        } catch (e:Exception) {
            e.printStackTrace()
            //json
        }*/
        return json
    }

    override fun onSuccessApiResult(listWithResponse: Map<RequestKey, ApiBean>) {
        recyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        retrieveList(listWithResponse)
    }

    override fun onErrorApiResult() {
        recyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun onLoadingApiResult() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }
}
