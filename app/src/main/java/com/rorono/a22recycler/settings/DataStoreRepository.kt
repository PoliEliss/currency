package com.rorono.a22recycler.settings

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.*
import java.io.IOException


const val THEME = "chosen_theme"

val Context.dataStore by preferencesDataStore(name = THEME)

private object PreferencesKey {
    val theme = stringPreferencesKey("theme")
    val language = stringPreferencesKey("language")
    val orientation = stringPreferencesKey("orientation")

}

class DataStoreRepository(context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun save(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        dataStore.edit { themes ->
            themes[dataStoreKey] = value
        }
    }



    val readFromThemeDataStore: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d("TEST", exception.message.toString())
                emit(emptyPreferences())
            } else throw exception
        }
        .map { preference ->
            val name = preference[PreferencesKey.theme] ?: "1"
            name

        }





    val readFromLanguageDataStore: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d("TEST", exception.message.toString())
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preference ->
            val name = preference[PreferencesKey.language] ?: "1"
            name
        }



    val readFromOrientationDataStore :Flow<String> = dataStore.data.map {
        it[PreferencesKey.orientation]?:"1"
    }
}









