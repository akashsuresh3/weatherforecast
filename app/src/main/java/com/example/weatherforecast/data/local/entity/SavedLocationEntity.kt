package com.example.weatherforecast.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_locations")
data class SavedLocationEntity(
    @PrimaryKey
    val cityName: String,
    val displayName: String,
    val lat: Double,
    val lon: Double
)
