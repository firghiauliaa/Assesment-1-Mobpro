package com.firghi0101.assesment1.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LayoutDataStore(private val context: Context) {

    companion object {
        private val IS_GRID = booleanPreferencesKey("is_grid")
    }

    val layoutFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_GRID] ?: false
    }

    suspend fun saveLayout(isGrid: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_GRID] = isGrid
        }
    }
}