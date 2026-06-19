package com.firghi0101.assesment1.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FuelApi {

    private const val BASE_URL =
        "GANTI_DENGAN_URL_MOCKAPI_KAMU/"

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