package es.jarroyo.tddweatherapp.domain.model.location

import es.jarroyo.tddweatherapp.domain.model.user.UserFactory

object WeatherLocationFactory {
    val locationTest = "Zaragoza"

    fun createCurrentLocationTest(): WeatherLocation {
        return WeatherLocation(0, locationTest, UserFactory.userIdTest)
    }

    fun createLocationList(): List<WeatherLocation> {
        var locationList = mutableListOf<WeatherLocation>()
        locationList.add(createCurrentLocationTest())
        return locationList
    }

}