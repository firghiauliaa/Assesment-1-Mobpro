package com.firghi0101.assesment1.network

import retrofit2.http.*

interface FuelApiService {

    @GET("FuelCost")
    suspend fun getFuelHistory(): List<FuelApiModel>

    @POST("FuelCost")
    suspend fun addFuel(
        @Body fuel: FuelApiModel
    ): FuelApiModel

    @PUT("FuelCost/{id}")
    suspend fun updateFuel(
        @Path("id") id: String,
        @Body fuel: FuelApiModel
    ): FuelApiModel

    @DELETE("FuelCost/{id}")
    suspend fun deleteFuel(
        @Path("id") id: String
    )
}