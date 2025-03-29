package com.example.weatherforecast.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherforecast.data.local.entity.ForecastEntity

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecasts: List<ForecastEntity>)

    @Query("SELECT * FROM forecast WHERE cityName = :cityName")
    suspend fun getForecastByCity(cityName: String): List<ForecastEntity>

    @Query("DELETE FROM forecast WHERE cityName = :cityName")
    suspend fun clearForecastForCity(cityName: String)
}

