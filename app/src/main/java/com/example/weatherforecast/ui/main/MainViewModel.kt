package com.example.weatherforecast.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.data.remote.dto.ForecastItem
import com.example.weatherforecast.data.remote.dto.GeoLocation
import com.example.weatherforecast.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _locations = MutableStateFlow<List<GeoLocation>>(emptyList())
    val locations: StateFlow<List<GeoLocation>> = _locations

    private val _selectedLocation = MutableStateFlow<GeoLocation?>(null)
    val selectedLocation: StateFlow<GeoLocation?> = _selectedLocation

    private val _selectedLocationOffline = MutableStateFlow<String?>(null)
    val selectedLocationOffline: StateFlow<String?> = _selectedLocationOffline

    private val _forecast = MutableStateFlow<List<ForecastItem>>(emptyList())
    val forecast: StateFlow<List<ForecastItem>> = _forecast

    private val _isLoadingWeather = MutableStateFlow(false)
    val isLoadingWeather: StateFlow<Boolean> = _isLoadingWeather

    private val _isLocationSearchEmpty = MutableStateFlow(false)
    val isLocationSearchEmpty: StateFlow<Boolean> = _isLocationSearchEmpty

    fun searchCity(city: String, apiKey: String, isOnline: Boolean) {
        viewModelScope.launch {
            _forecast.value = emptyList()
            _locations.value = emptyList()
            _isLocationSearchEmpty.value = false
            _selectedLocation.value = null
            _selectedLocationOffline.value = null

            val cityKey = city.trim().lowercase()

            if (!isOnline) {
                // Offline — Room
                val cachedForecast = repository.getForecastOfflineByCity(cityKey)
                if (cachedForecast.isNotEmpty()) {
                    _selectedLocationOffline.value = cityKey
                    _forecast.value = cachedForecast
                } else {
                    _isLocationSearchEmpty.value = true
                    _selectedLocation.value = null
                    _selectedLocationOffline.value = null
                }
                return@launch
            }

            // Online — API
            try {
                val result = repository.searchCityByName(city, apiKey)
                _locations.value = result
                _selectedLocationOffline.value = null
                _isLocationSearchEmpty.value = result.isEmpty()
            } catch (e: Exception) {
                e.printStackTrace()
                _isLocationSearchEmpty.value = true
                _selectedLocation.value = null
                _selectedLocationOffline.value = null
            }
        }
    }

    fun selectLocationAndFetchForecast(location: GeoLocation, apiKey: String) {
        _selectedLocation.value = location
        _locations.value = emptyList()

        val cityKey = location.name.trim().lowercase()
        val displayName = "${location.name}, ${location.country}"

        fetchForecast(
            cityName = cityKey,
            displayName = displayName,
            lat = location.lat,
            lon = location.lon,
            apiKey = apiKey
        )
    }

    fun refreshWeather(apiKey: String) {
        selectedLocation.value?.let {
            val cityKey = it.name.trim().lowercase()
            val displayName = "${it.name}, ${it.country}"
            fetchForecast(cityKey, displayName, it.lat, it.lon, apiKey)
        }
    }

    private fun fetchForecast(cityName: String, displayName: String, lat: Double, lon: Double, apiKey: String) {
        viewModelScope.launch {
            _isLoadingWeather.value = true
            try {
                val data = repository.fetchAndCacheForecast(cityName, displayName, lat, lon, apiKey)
                _forecast.value = data
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoadingWeather.value = false
            }
        }
    }
}
