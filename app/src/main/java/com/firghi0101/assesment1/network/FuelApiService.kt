package com.firghi0101.assesment1.network

import retrofit2.http.*

interface FuelApiService {

    @GET("fuelhistory")
    suspend fun getFuelHistory(): List<FuelApiModel>

    @POST("fuelhistory")
    suspend fun addFuel(
        @Body fuel: FuelApiModel
    ): FuelApiModel

    @PUT("fuelhistory/{id}")
    suspend fun updateFuel(
        @Path("id") id: String,
        @Body fuel: FuelApiModel
    ): FuelApiModel

    @DELETE("fuelhistory/{id}")
    suspend fun deleteFuel(
        @Path("id") id: String
    )
}