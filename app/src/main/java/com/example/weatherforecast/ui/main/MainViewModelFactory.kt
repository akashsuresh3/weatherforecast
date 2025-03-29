package com.example.weatherforecast.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecast.data.local.WeatherDatabase
import com.example.weatherforecast.data.remote.RetrofitInstance
import com.example.weatherforecast.data.repository.WeatherRepository

class MainViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val db = WeatherDatabase.getDatabase(context)
    private val forecastDao = db.forecastDao()
    private val savedLocationDao = db.savedLocationDao()
    private val weatherApi = RetrofitInstance.weatherApi
    private val geoApi = RetrofitInstance.geoApi
    private val repository = WeatherRepository(weatherApi, forecastDao, savedLocationDao, geoApi)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}

