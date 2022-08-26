package com.hcl.retrofit.coroutines.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.hcl.concurrentapicalls.api.ApiBean
import com.hcl.concurrentapicalls.api.ApiHelper
import com.hcl.concurrentapicalls.api.RetrofitBuilder
import com.hcl.concurrentapicalls.utils.ApiResponse
import com.hcl.concurrentapicalls.utils.RequestKey
import com.hcl.concurrentapicalls.utils.RequestType
import com.hcl.retrofit.coroutines.R
import com.hcl.retrofit.coroutines.data.model.User
import com.hcl.retrofit.coroutines.ui.base.ViewModelFactory
import com.hcl.retrofit.coroutines.ui.main.adapter.MainAdapter
import com.hcl.retrofit.coroutines.ui.main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Type

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
        RetrofitBuilder.BASE_URL="https://5e510330f2c0d300147c034c.mockapi.io/"
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

        val apiMap = mutableMapOf<RequestKey, ApiBean>()

        apiMap[RequestKey.REQUEST_FIRST] = ApiBean(
            requestType = RequestType.GET, endPoint = "users",
            null,
            null,
            null,
            null,
            null,
            null
        )
        apiMap[RequestKey.REQUEST_SECOND] = ApiBean(
            requestType = RequestType.GET, endPoint = "users",
            null,
            null,
            null,
            null,
            null,
            null
        )
        apiMap[RequestKey.REQUEST_THIRD] = ApiBean(
            requestType = RequestType.GET, endPoint = "users",
            null,
            null,
            null,
            null,
            null,
            null
        )
        apiMap[RequestKey.REQUEST_FOURTH] = ApiBean(
            requestType = RequestType.GET, endPoint = "users",
            null,
            null,
            null,
            null,
            null,
            null
        )

        viewModel.doSomeAPICalls(apiMap)
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

        adapter.apply {
            addUsers(toRpcResult(listWithResponse[RequestKey.REQUEST_FIRST]?.resultOfApi))
            notifyDataSetChanged()
        }
    }

    private fun toRpcResult(json: JsonElement?): List<User> {
        val collectionType: Type = object : TypeToken<List<User?>?>() {}.type
        return Gson().fromJson(json, collectionType)
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
