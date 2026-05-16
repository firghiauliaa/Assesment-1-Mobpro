package com.firghi0101.assesment1.ui.screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.firghi0101.assesment1.data.FuelDb
import com.firghi0101.assesment1.data.LayoutDataStore
import com.firghi0101.assesment1.model.FuelEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val fuelDao = FuelDb.getInstance(application).dao

    private val layoutStore = LayoutDataStore(application)

    val fuelList: StateFlow<List<FuelEntity>> = fuelDao.getAllFuel()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val isLinearLayout: StateFlow<Boolean> = layoutStore.layoutFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = true
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

    fun toggleLayout(isLinear: Boolean) {
        viewModelScope.launch {
            layoutStore.saveLayout(isLinear)
        }
    }
}