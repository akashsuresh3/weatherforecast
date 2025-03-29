package com.example.weatherforecast.ui.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.data.remote.RetrofitInstance
import com.example.weatherforecast.data.remote.dto.GeoLocation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationSearchViewModel : ViewModel() {

    private val _locations = MutableStateFlow<List<GeoLocation>>(emptyList())
    val locations: StateFlow<List<GeoLocation>> = _locations

    fun searchCity(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.geoApi.getCityCoordinates(city, 5, apiKey)
                _locations.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
