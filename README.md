# Weather Forecast App

This is a simple Android weather forecast application built using Kotlin, Jetpack Compose, Retrofit, and Room. The app displays a 3-day weather forecast for a user-searched city and supports offline access by storing previously fetched forecast data locally.

---

## Features

- Search for any city using the OpenWeatherMap Geocoding API
- Display 3-day weather forecast using OpenWeatherMap Forecast API (`/data/2.5/forecast`)
- Clean and modern UI built with Jetpack Compose
- Forecast includes date, temperature, weather condition, and icon
- Offline support:
  - Stores forecasts for all searched cities in Room
  - If the user searches for a previously searched city while offline, cached forecast is shown
- Manual refresh support (only available when online)

---

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Architecture:** MVVM (Model-View-ViewModel)
- **Networking:** Retrofit
- **Local Storage:** Room Database
- **Dependency Injection:** (Optional) Hilt (not currently used)
- **Other Tools:** Coroutine Flows, LiveData, ViewModel, Material 3

---

## API Integration

The app uses the following endpoints from [OpenWeatherMap](https://openweathermap.org):

- **Geocoding API**  
  `https://api.openweathermap.org/geo/1.0/direct?q={city name}&limit=5&appid={API key}`

- **5-Day/3-Hour Forecast API**  
  `https://api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid={API key}`

---

