package com.firghi0101.assesment1.data

import FuelEntity
import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FuelDao {
    @Insert
    suspend fun insert(fuel: FuelEntity)

    @Update
    suspend fun update(fuel: FuelEntity)

    @Query("SELECT * FROM fuel_history ORDER BY tanggal DESC")
    fun getAllFuel(): Flow<List<FuelEntity>>

    @Query("SELECT * FROM fuel_history WHERE id = :id")
    suspend fun getFuelById(id: Long): FuelEntity?

    @Delete
    suspend fun delete(fuel: FuelEntity)
}