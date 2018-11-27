package es.jarroyo.tddweatherapp.domain.model.location

object WeatherLocationFactory {
    val locationTest = "Zaragoza"

    fun createCurrentLocationTest(): WeatherLocation {
        return WeatherLocation(0, locationTest)
    }

}