package com.hcl.concurrentapicalls.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.hcl.concurrentapicalls.api.ApiBean
import com.hcl.concurrentapicalls.repository.MainRepository
import com.hcl.concurrentapicalls.utils.ApiResponse
import com.hcl.concurrentapicalls.utils.RequestKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val apiResponseListner: ApiResponse,
    private val mainRepository: MainRepository
) : ViewModel() {

    fun doMultipleAPICalls(apiCallList: Map<RequestKey, ApiBean>) {
        apiResponseListner.onLoadingApiResult()
        CoroutineScope(Dispatchers.IO).launch {
            mainRepository.returnApiResults(apiCallList = apiCallList) { resultOfCalls ->
                Log.i("resultOfCalls", resultOfCalls.toString())
                CoroutineScope(Dispatchers.Main).launch {
                    apiResponseListner.onSuccessApiResult(resultOfCalls)
                }
            }
        }
    }

}