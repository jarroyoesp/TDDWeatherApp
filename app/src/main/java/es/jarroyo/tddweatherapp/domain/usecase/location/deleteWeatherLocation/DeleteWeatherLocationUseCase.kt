package es.jarroyo.tddweatherapp.domain.usecase.location.deleteWeatherLocation

import es.jarroyo.tddweatherapp.data.repository.LocationRepository
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocation
import es.jarroyo.tddweatherapp.domain.usecase.base.BaseUseCase

open class DeleteWeatherLocationUseCase(val repository: LocationRepository) : BaseUseCase<DeleteWeatherLocationRequest, List<WeatherLocation>>() {

    override suspend fun run(): Response<List<WeatherLocation>> {
        return repository.deleteWeatherLocation(request!!)
    }
}