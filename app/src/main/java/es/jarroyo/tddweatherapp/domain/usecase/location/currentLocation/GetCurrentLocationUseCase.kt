package es.jarroyo.tddweatherapp.domain.usecase.location.currentLocation

import es.jarroyo.tddweatherapp.data.repository.LocationRepository
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocation
import es.jarroyo.tddweatherapp.domain.usecase.base.BaseUseCase

open class GetCurrentLocationUseCase(val repository: LocationRepository) : BaseUseCase<Nothing, WeatherLocation>() {

    override suspend fun run(): Response<WeatherLocation> {
        return repository.getCurrentLocation()
    }
}