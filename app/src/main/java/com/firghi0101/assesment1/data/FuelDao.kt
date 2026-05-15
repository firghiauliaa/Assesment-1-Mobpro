package com.firghi0101.assesment1.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.firghi0101.assesment1.model.FuelEntity
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