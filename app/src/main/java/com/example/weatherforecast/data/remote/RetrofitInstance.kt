package com.example.weatherforecast.data.remote

import com.example.weatherforecast.data.remote.dto.GeoLocation
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // Base URL for weather data
//    private const val WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"

    private const val WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"

    // Base URL for geocoding API
    private const val GEO_BASE_URL = "https://api.openweathermap.org/"

    val weatherApi: WeatherApiService by lazy {
        Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }

    val geoApi: GeocodingApiService by lazy {
        Retrofit.Builder()
            .baseUrl(GEO_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeocodingApiService::class.java)
    }
}
