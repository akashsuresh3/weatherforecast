package com.example.weatherforecast.ui.location

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherforecast.data.remote.dto.GeoLocation

@Composable
fun LocationSearchScreen(
    apiKey: String,
    onLocationSelected: (GeoLocation) -> Unit,
    viewModel: LocationSearchViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val searchResults by viewModel.locations.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Enter city name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (searchQuery.isNotBlank()) {
                    viewModel.searchCity(searchQuery.trim(), apiKey)
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(searchResults) { location ->
                LocationItem(location = location) {
                    onLocationSelected(it)
                }
            }
        }
    }
}
