package com.example.libnews

import com.example.libnews.converters.EnumConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NewsClient {

//        val newsURL = "https://newsapi.org/v2/top-headlines?country=in&apiKey=730a60dec330429c8fc1a2d3eeec28fd"
//        // My New API  ->  f95c36ef152b4180833e911e855b0222
//        // My Old API  ->  730a60dec330429c8fc1a2d3eeec28fd
//        // Anuj's API -> 1f4a12d2698e432ea9cf18126dcc7acd

    private const val BASE_URL = "https://newsapi.org/v2/"
    private const val API_KEY = "730a60dec330429c8fc1a2d3eeec28fd"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addConverterFactory(EnumConverterFactory())
            .build()
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor(Interceptor {
            val request = it.request().newBuilder()
                .header("Authorization", API_KEY)
                .build()
            it.proceed(request)
        }).build()
    }

    fun <T> buildApi(api: Class<T>): T = retrofit.create(api)
}