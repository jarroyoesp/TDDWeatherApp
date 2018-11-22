package es.jarroyo.tddweatherapp.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
@Entity(tableName = "WEATHER_LOCATION")
class WeatherLocationEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,
        var cityName: String = "")