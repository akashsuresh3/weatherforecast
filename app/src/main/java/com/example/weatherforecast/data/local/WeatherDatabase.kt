package com.example.weatherforecast.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherforecast.data.local.dao.ForecastDao
import com.example.weatherforecast.data.local.dao.SavedLocationDao
import com.example.weatherforecast.data.local.entity.ForecastEntity
import com.example.weatherforecast.data.local.entity.SavedLocationEntity

@Database(
    entities = [ForecastEntity::class, SavedLocationEntity::class],
    version = 2,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun forecastDao(): ForecastDao
    abstract fun savedLocationDao(): SavedLocationDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "weather_db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
