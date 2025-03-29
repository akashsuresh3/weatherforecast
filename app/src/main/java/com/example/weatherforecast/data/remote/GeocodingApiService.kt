package com.example.weatherforecast.data.remote

import com.example.weatherforecast.data.remote.dto.GeoLocation
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApiService {

    @GET("geo/1.0/direct")
    suspend fun getCityCoordinates(
        @Query("q") city: String,
        @Query("limit") limit: Int = 5,
        @Query("appid") apiKey: String
    ): List<GeoLocation>
}
