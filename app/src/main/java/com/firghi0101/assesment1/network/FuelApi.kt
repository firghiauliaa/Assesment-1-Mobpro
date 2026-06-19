package com.firghi0101.assesment1.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FuelApi {

    private const val BASE_URL =
        "https://6a350930f957779fdb300042.mockapi.io/api/"

    val service: FuelApiService by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(FuelApiService::class.java)
    }
}