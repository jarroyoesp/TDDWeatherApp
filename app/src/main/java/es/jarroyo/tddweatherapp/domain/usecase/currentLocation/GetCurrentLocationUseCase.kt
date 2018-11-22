package es.jarroyo.tddweatherapp.domain.usecase.currentLocation

import android.location.Location
import es.jarroyo.tddweatherapp.data.repository.LocationRepository
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.usecase.base.BaseUseCase

open class GetCurrentLocationUseCase(val repository: LocationRepository) : BaseUseCase<Nothing, Location>() {

    override suspend fun run(): Response<Location> {
        return repository.getCurrentLocation()
    }
}