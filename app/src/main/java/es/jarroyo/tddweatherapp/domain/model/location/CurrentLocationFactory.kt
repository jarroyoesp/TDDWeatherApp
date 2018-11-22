package es.jarroyo.tddweatherapp.domain.model.location

object CurrentLocationFactory {
    fun createCurrentLocationTest(): WeatherLocation {
        return WeatherLocation(0, "Zaragoza")
    }

}