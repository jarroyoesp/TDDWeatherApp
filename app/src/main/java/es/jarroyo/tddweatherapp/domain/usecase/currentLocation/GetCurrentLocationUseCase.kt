package es.jarroyo.tddweatherapp.domain.usecase.currentLocation

import es.jarroyo.tddweatherapp.data.repository.LocationRepository
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.location.CurrentLocation
import es.jarroyo.tddweatherapp.domain.usecase.base.BaseUseCase

open class GetCurrentLocationUseCase(val repository: LocationRepository) : BaseUseCase<Nothing, CurrentLocation>() {

    override suspend fun run(): Response<CurrentLocation> {
        return repository.getCurrentLocation()
    }
}