package es.jarroyo.tddweatherapp.domain.model.location

object CurrentLocationFactory {
    fun createCurrentLocationTest(): CurrentLocation {
        return CurrentLocation("Zaragoza")
    }

}