package com.example.weatherforecast.data.remote.dto

data class ForecastResponse(
    val list: List<ForecastItem>,
    val city: CityInfo
)

data class ForecastItem(
    val dt: Long,
    val main: MainInfo,
    val weather: List<WeatherInfo>,
    val dt_txt: String
)

data class MainInfo(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
)

data class WeatherInfo(
    val main: String,
    val description: String,
    val icon: String
)

data class CityInfo(
    val name: String,
    val country: String
)
