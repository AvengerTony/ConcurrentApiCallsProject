package com.hcl.concurrentapicalls.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hcl.concurrentapicalls.api.ApiHelper
import com.hcl.concurrentapicalls.repository.MainRepository
import com.hcl.concurrentapicalls.utils.ApiResponse
import com.hcl.concurrentapicalls.viewmodel.MainViewModel

class ViewModelFactory(
    private val apiResponseListner: ApiResponse,
    private val apiHelper: ApiHelper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(apiResponseListner, MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}

