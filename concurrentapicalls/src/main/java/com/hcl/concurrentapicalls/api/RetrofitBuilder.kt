package com.hcl.concurrentapicalls.api

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


var BASE_URL: String = ""

object RetrofitBuilder {

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .also {
                if (headers != null)
                    it.client(addIntercepterandHeader(headers = Headers.headersOf(
                           headers!!
                        )
                    ))
            }
            .build()
    }

    fun setHeader(headers:String) {
        this.headers = headers
    }

    private fun addIntercepterandHeader(headers: Headers): OkHttpClient {
        // Define the interceptor, add authentication headers
        val interceptor = Interceptor { chain ->
            val newRequest: Request =
                chain.request().newBuilder()
                    .headers(headers = headers)
                    .build()
            chain.proceed(newRequest)
        }

        // Add the interceptor to OkHttpClient
        val builder = OkHttpClient.Builder()
        builder.interceptors().add(interceptor)
        return builder.build()

    }

    private var headers:String? = null



    val apiService: ApiService = getRetrofit().create(ApiService::class.java)

}