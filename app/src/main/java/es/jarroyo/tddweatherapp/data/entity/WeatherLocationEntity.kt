package es.jarroyo.tddweatherapp.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocation

fun WeatherLocationEntity.toDomain (): WeatherLocation = WeatherLocation(id, cityName)

@Entity(tableName = "WEATHER_LOCATION")
class WeatherLocationEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,
        var cityName: String = "")
