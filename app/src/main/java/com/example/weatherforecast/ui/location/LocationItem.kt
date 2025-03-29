package com.example.weatherforecast.ui.location

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.data.remote.dto.GeoLocation

@Composable
fun LocationItem(location: GeoLocation, onClick: (GeoLocation) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(location) }
            .padding(12.dp)
    ) {
        Text(text = location.name, style = MaterialTheme.typography.titleMedium)
        Text(
            text = "${location.state ?: "Unknown"}, ${location.country}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
