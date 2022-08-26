package com.hcl.retrofit.coroutines.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.ViewModel
import com.hcl.concurrentapicalls.api.ApiBean
import com.hcl.concurrentapicalls.repository.MainRepository
import com.hcl.concurrentapicalls.utils.ApiResponse
import com.hcl.concurrentapicalls.utils.RequestKey
import com.hcl.concurrentapicalls.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(
    private val apiResponseListner: ApiResponse,
    private val mainRepository: MainRepository
) : ViewModel() {
    lateinit var mContext: LiveDataScope<Resource<String>>
    /*  fun getUsers() = liveData(Dispatchers.IO) {
          emit(Resource.loading(data = null))
          try {
              val time1 = measureTimeMillis {
                  emit(Resource.success(data = mainRepository.getUsers()))
              }
              println("debug: compeleted job2 in $time1 ms.")

          } catch (exception: Exception) {
              emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
          }
      }*/

    fun doSomeAPICalls(apiCallList: Map<RequestKey, ApiBean>)
    //= liveData(Dispatchers.IO)
    {
        // mContext = this@liveData
        // emit(Resource.loading(data = null))
        apiResponseListner.onLoadingApiResult()
        CoroutineScope(Dispatchers.IO).launch {
            mainRepository.returnSomeItems(apiCallList = apiCallList) { resultOfCalls ->
                Log.i("resultOfCalls", resultOfCalls.toString())
                // withContext(Dispatchers.Default){ emit(Resource.success(data = resultOfCalls))}
                //viewModelScope.launch {
                //    mContext.emit(Resource.success(data = resultOfCalls))
                //}
                CoroutineScope(Dispatchers.Main).launch {
                    apiResponseListner.onSuccessApiResult(resultOfCalls)
                }
            }
        }
        // emit(Resource.success(data = resultOfCall))

    }

}