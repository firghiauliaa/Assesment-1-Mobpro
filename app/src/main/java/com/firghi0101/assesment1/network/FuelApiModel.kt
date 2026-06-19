package com.firghi0101.assesment1.network

data class FuelApiModel(
    val id: String = "",
    val userId: String,
    val vehicleName: String,
    val distance: Double,
    val fuelConsumption: Double,
    val fuelPrice: Double,
    val totalCost: Double,
    val createdAt: String
)