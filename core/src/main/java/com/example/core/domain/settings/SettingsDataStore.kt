package com.example.core.domain.settings

import kotlinx.coroutines.flow.Flow

/**
 * Interface for the data store that handles the settings for application
 */
interface SettingsDataStore {
    suspend fun putBoolean(key: String, value: Boolean)
    suspend fun putString(key: String, value: String)
    suspend fun putDouble(key: String, value: Double)
    suspend fun putInt(key: String, value: Int)
    suspend fun putLong(key: String, value: Long)
    suspend fun getBoolean(key: String): Boolean?
    suspend fun getString(key: String): String?
    suspend fun getDouble(key: String): Double?
    suspend fun getInt(key: String): Int?
    suspend fun getLong(key: String): Long?
    fun getBooleanFlow(key: String): Flow<Boolean?>
    fun getStringFlow(key: String): Flow<String?>
    fun getIntFlow(key: String): Flow<Int?>
    fun getDoubleFlow(key: String): Flow<Double?>
    suspend fun deleteBoolean(key: String)
    suspend fun deleteString(key: String)
    suspend fun deleteInt(key: String)
    suspend fun clear(keys: List<String>)
    suspend fun clearPreferences()
}