package com.example.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherforecast.data.local.entity.SavedLocationEntity

@Dao
interface SavedLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(location: SavedLocationEntity)

    @Query("SELECT * FROM saved_locations WHERE cityName = :cityName")
    suspend fun getCity(cityName: String): SavedLocationEntity?
}
