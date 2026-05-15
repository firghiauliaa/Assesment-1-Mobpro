package com.firghi0101.assesment1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fuel_history")
data class FuelEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val jarak: Float,
    val konsumsi: Float,
    val harga: Float,
    val totalBiaya: Double,
    val tanggal: Long = System.currentTimeMillis()
)