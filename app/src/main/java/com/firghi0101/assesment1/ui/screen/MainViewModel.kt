package com.firghi0101.assesment1.ui.screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.firghi0101.assesment1.data.FuelDb
import com.firghi0101.assesment1.data.LayoutDataStore
import com.firghi0101.assesment1.model.FuelEntity
import com.firghi0101.assesment1.network.ApiStatus
import com.firghi0101.assesment1.network.FuelApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.firghi0101.assesment1.network.FuelApiModel

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val fuelDao = FuelDb.getInstance(application).dao

    private val layoutStore = LayoutDataStore(application)

    private val _status = MutableStateFlow(ApiStatus.LOADING)
    val status = _status.asStateFlow()

    val fuelList: StateFlow<List<FuelEntity>> = fuelDao.getAllFuel()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val isGridLayout: StateFlow<Boolean> = layoutStore.layoutFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = true
        )

    init {
        retrieveData()
    }

    fun retrieveData() {
        viewModelScope.launch {

            _status.value = ApiStatus.LOADING

            try {

                val result = FuelApi.service.getFuelHistory()

                android.util.Log.d(
                    "API_SUCCESS",
                    result.toString()
                )

                _status.value = ApiStatus.SUCCESS

            } catch (e: Exception) {

                android.util.Log.e(
                    "API_ERROR",
                    e.stackTraceToString()
                )

                _status.value = ApiStatus.FAILED
            }
        }
    }

    fun insertFuel(fuel: FuelEntity) {
        viewModelScope.launch {
            fuelDao.insert(fuel)
        }
    }

    fun deleteFuelApi(id: String) {
        viewModelScope.launch {
            try {
                FuelApi.service.deleteFuel(id)
                retrieveData()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun toggleLayout(isGrid: Boolean) {
        viewModelScope.launch {
            layoutStore.saveLayout(isGrid)
        }
    }

    fun updateFuelApi(fuel: FuelApiModel) {
        viewModelScope.launch {
            try {
                FuelApi.service.updateFuel(
                    fuel.id,
                    fuel
                )
                retrieveData()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addFuelToApi(
        jarak: Double,
        konsumsi: Double,
        harga: Double,
        total: Double
    ) {

        viewModelScope.launch {

            try {

                val fuel = FuelApiModel(
                    userId = "firghi",
                    vehicleName = "Motor",
                    distance = jarak,
                    fuelConsumption = konsumsi,
                    fuelPrice = harga,
                    totalCost = total,
                    createdAt = System.currentTimeMillis().toString()
                )

                val response = FuelApi.service.addFuel(fuel)

                android.util.Log.d(
                    "POST_SUCCESS",
                    response.toString()
                )

                retrieveData()

            } catch (e: Exception) {

                android.util.Log.e(
                    "POST_ERROR",
                    e.stackTraceToString()
                )

                _status.value = ApiStatus.FAILED
            }
        }
    }
}