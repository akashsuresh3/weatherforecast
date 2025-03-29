package com.example.weatherforecast.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast")
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val cityName: String,
    val dateText: String,
    val temperature: Double,
    val condition: String,
    val icon: String
)
