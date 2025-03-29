package com.example.weatherforecast.data.repository

import android.util.Log
import com.example.weatherforecast.data.local.dao.ForecastDao
import com.example.weatherforecast.data.local.dao.SavedLocationDao
import com.example.weatherforecast.data.local.entity.ForecastEntity
import com.example.weatherforecast.data.local.entity.SavedLocationEntity
import com.example.weatherforecast.data.remote.GeocodingApiService
import com.example.weatherforecast.data.remote.WeatherApiService
import com.example.weatherforecast.data.remote.dto.ForecastItem
import com.example.weatherforecast.data.remote.dto.GeoLocation
import com.example.weatherforecast.data.remote.dto.MainInfo
import com.example.weatherforecast.data.remote.dto.WeatherInfo

class WeatherRepository(
    private val weatherApi: WeatherApiService,
    private val forecastDao: ForecastDao,
    private val savedLocationDao: SavedLocationDao,
    private val geoApi: GeocodingApiService
) {

    // Called online — saves forecast + city
    suspend fun fetchAndCacheForecast(
        cityName: String,
        displayName: String,
        lat: Double,
        lon: Double,
        apiKey: String
    ): List<ForecastItem> {
        return try {
            val response = weatherApi.getThreeDayForecast(lat, lon, apiKey)
            val filtered = response.list.filter { it.dt_txt.contains("12:00:00") }.take(3)

            // 1. Save city
            val savedCity = SavedLocationEntity(
                cityName = cityName,
                displayName = displayName,
                lat = lat,
                lon = lon
            )
            savedLocationDao.insertCity(savedCity)

            // 2. Save forecast
            forecastDao.clearForecastForCity(cityName)
            val entities = filtered.map {
                ForecastEntity(
                    cityName = cityName,
                    dateText = it.dt_txt,
                    temperature = it.main.temp,
                    condition = it.weather.firstOrNull()?.main ?: "Unknown",
                    icon = it.weather.firstOrNull()?.icon ?: ""
                )
            }
            forecastDao.insertForecast(entities)

            filtered
        } catch (e: Exception) {
            Log.d("WeatherRepo", "API failed: ${e.message}")
            e.printStackTrace()
            emptyList()
        }
    }

    // Called offline — uses city name to fetch from local DB
    suspend fun getForecastOfflineByCity(cityName: String): List<ForecastItem> {
        val savedCity = savedLocationDao.getCity(cityName)
        if (savedCity != null) {
            val cached = forecastDao.getForecastByCity(cityName)
            return cached.map {
                ForecastItem(
                    dt = 0L,
                    dt_txt = it.dateText,
                    main = MainInfo(
                        temp = it.temperature,
                        feels_like = 0.0,
                        temp_min = 0.0,
                        temp_max = 0.0,
                        pressure = 0,
                        humidity = 0
                    ),
                    weather = listOf(
                        WeatherInfo(
                            main = it.condition,
                            description = it.condition,
                            icon = it.icon
                        )
                    )
                )
            }
        } else {
            return emptyList()
        }
    }

    suspend fun searchCityByName(city: String, apiKey: String): List<GeoLocation> {
        return geoApi.getCityCoordinates(city, 5, apiKey)
    }
}
