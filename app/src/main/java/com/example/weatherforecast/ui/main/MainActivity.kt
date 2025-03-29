package com.example.weatherforecast.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherforecast.BuildConfig
import com.example.weatherforecast.ui.theme.WeatherForecastTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewModel: MainViewModel = viewModel(factory = MainViewModelFactory(applicationContext))
//            val apiKey = BuildConfig.OPENWEATHER_API_KEY

            WeatherForecastTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainScreen("21beb6df9e3d9c9e0889813ba98a058d", viewModel = viewModel)
                }
            }
        }
    }
}

