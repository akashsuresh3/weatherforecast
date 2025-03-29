package com.example.weatherforecast.data.remote.dto

data class GeoLocation(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String? = null
)
