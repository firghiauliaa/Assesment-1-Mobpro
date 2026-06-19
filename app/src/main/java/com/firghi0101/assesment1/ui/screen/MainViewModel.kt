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

                println("Jumlah data dari API: ${result.size}")

                _status.value = ApiStatus.SUCCESS

            } catch (e: Exception) {

                e.printStackTrace()

                _status.value = ApiStatus.FAILED
            }
        }
    }

    fun insertFuel(fuel: FuelEntity) {
        viewModelScope.launch {
            fuelDao.insert(fuel)
        }
    }

    fun deleteFuel(fuel: FuelEntity) {
        viewModelScope.launch {
            fuelDao.delete(fuel)
        }
    }

    fun toggleLayout(isGrid: Boolean) {
        viewModelScope.launch {
            layoutStore.saveLayout(isGrid)
        }
    }
}