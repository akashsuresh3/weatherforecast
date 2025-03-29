package com.example.weatherforecast.data.remote

import com.example.weatherforecast.data.remote.dto.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast")
    suspend fun getThreeDayForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String,
        @Query("units") units: String = "metric"
//        @Query("exclude") exclude: String = "minutely,hourly,alerts",
//        @Query("units") units: String = "metric"
    ): ForecastResponse
}
