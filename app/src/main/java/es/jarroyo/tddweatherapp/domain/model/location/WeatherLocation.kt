package es.jarroyo.tddweatherapp.domain.model.location

import es.jarroyo.tddweatherapp.data.entity.WeatherLocationEntity

data class WeatherLocation(var id: Long = 0,
                                var cityName: String = "",
                           var userId: Long = 0) {
}

fun WeatherLocation.toEntity (): WeatherLocationEntity = WeatherLocationEntity(id, cityName, userId)