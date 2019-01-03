package es.jarroyo.tddweatherapp.domain.usecase.location.saveWeatherLocation

import es.jarroyo.tddweatherapp.data.repository.LocationRepository
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocation
import es.jarroyo.tddweatherapp.domain.usecase.base.BaseUseCase

open class SaveWeatherLocationUseCase(val repository: LocationRepository) : BaseUseCase<SaveWeatherLocationRequest, List<WeatherLocation>>() {

    override suspend fun run(): Response<List<WeatherLocation>> {
        return repository.saveWeatherLocationList(request!!)
    }
}