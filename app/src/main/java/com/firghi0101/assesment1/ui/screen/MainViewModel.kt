package com.firghi0101.assesment1.ui.screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.firghi0101.assesment1.data.FuelDb
import com.firghi0101.assesment1.model.FuelEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val fuelDao = FuelDb.getInstance(application).dao

    val fuelList = fuelDao.getAllFuel()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

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
}