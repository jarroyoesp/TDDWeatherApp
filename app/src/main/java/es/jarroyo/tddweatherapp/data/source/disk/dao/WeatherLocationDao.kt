package es.jarroyo.tddweatherapp.data.source.disk.dao

import android.arch.persistence.room.*
import es.jarroyo.tddweatherapp.data.entity.WeatherLocationEntity

@Dao
interface WeatherLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherLocation(weatherLocation: WeatherLocationEntity)

    @Delete
    fun deleteWeatherLocation(weatherLocation: WeatherLocationEntity)

    @Query("DELETE FROM WEATHER_LOCATION")
    fun deleteAllWeatherLocation()

    @Query("SELECT * FROM WEATHER_LOCATION")
    fun getAllWeatherLocationList():List<WeatherLocationEntity>
}