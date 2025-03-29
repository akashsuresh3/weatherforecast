package com.example.weatherforecast.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.data.remote.dto.ForecastItem
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ForecastItemView(item: ForecastItem) {
    val date = remember(item.dt_txt) {
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formatter = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
        try {
            val parsedDate = parser.parse(item.dt_txt)
            parsedDate?.let { formatter.format(it) } ?: item.dt_txt
        } catch (e: Exception) {
            item.dt_txt
        }
    }

    val condition = item.weather.firstOrNull()?.main.orEmpty()
    val icon = when (condition.lowercase()) {
        "clear" -> "☀️"
        "sunny" -> "🌞"
        "clouds" -> "☁️"
        "rain" -> "🌧️"
        "snow" -> "🌨️"
        else -> "❓"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = date, style = MaterialTheme.typography.titleMedium)
            Text(text = "Temp: ${item.main.temp}°C")
            Text(text = "Condition:  $icon $condition")
        }
    }
}
