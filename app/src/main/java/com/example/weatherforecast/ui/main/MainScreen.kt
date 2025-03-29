package com.example.weatherforecast.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherforecast.data.remote.dto.ForecastItem
import com.example.weatherforecast.data.remote.dto.GeoLocation
import com.example.weatherforecast.ui.location.LocationItem
import com.example.weatherforecast.utils.NetworkUtils

@Composable
fun MainScreen(
    apiKey: String,
    viewModel: MainViewModel = viewModel()
) {
    var cityQuery by remember { mutableStateOf("") }
    val locationResults by viewModel.locations.collectAsState()
    val forecastList by viewModel.forecast.collectAsState()
    val selectedCity by viewModel.selectedLocation.collectAsState()
    val selectedCityOffline by viewModel.selectedLocationOffline.collectAsState()
    val isLoadingWeather by viewModel.isLoadingWeather.collectAsState()
    val isLocationSearchEmpty by viewModel.isLocationSearchEmpty.collectAsState()
    val context = LocalContext.current
    var isOnline by remember { mutableStateOf(NetworkUtils.isOnline(context)) }
    LaunchedEffect(Unit) {
        snapshotFlow { NetworkUtils.isOnline(context) }
            .collect { online ->
                isOnline = online
            }
    }

    Column(modifier = Modifier.padding(16.dp)) {

        OutlinedTextField(
            value = cityQuery,
            onValueChange = { cityQuery = it },
            label = { Text("Enter city name") },
            modifier = Modifier.fillMaxWidth().padding(0.dp, 28.dp, 0.dp, 0.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))


        Button(
            onClick = {
                if (cityQuery.isNotBlank()) {
                    val isOnline = NetworkUtils.isOnline(context)
                    viewModel.searchCity(cityQuery.trim(), apiKey, isOnline)
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLocationSearchEmpty) {
            Text("No results found.", style = MaterialTheme.typography.bodyMedium)
        }


        LazyColumn {
            items(locationResults) { location ->
                LocationItem(location = location) {
                    viewModel.selectLocationAndFetchForecast(location, apiKey)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        selectedCity?.let {
            Text(
                text = "Weather Forecast for ${it.name}, ${it.country}",
                style = MaterialTheme.typography.titleMedium
            )
        }

        selectedCityOffline?.let {
            Text(
                text = "Weather Forecast for ${it}",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


        if (isLoadingWeather) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn {
                items(forecastList) { forecast ->
                    ForecastItemView(forecast)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Refresh Button
            if (selectedCity != null && isOnline) {
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = { viewModel.refreshWeather(apiKey) }
                ) {
                    Text("Refresh Forecast")
                }
            }
        }
    }
}
