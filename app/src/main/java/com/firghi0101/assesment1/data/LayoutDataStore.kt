package com.firghi0101.assesment1.data

import android.content.Context
import androidx.datastore.core.DataStore
import java.util.prefs.Preferences

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "layout_settings")