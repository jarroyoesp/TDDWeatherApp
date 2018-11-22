package es.jarroyo.tddweatherapp.domain.usecase.location.getAllWeatherLocationList

import es.jarroyo.tddweatherapp.data.repository.LocationRepository
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocation
import es.jarroyo.tddweatherapp.domain.usecase.base.BaseUseCase

open class GetAllWeatherLocationListUseCase(val repository: LocationRepository) : BaseUseCase<Nothing, List<WeatherLocation>>() {

    override suspend fun run(): Response<List<WeatherLocation>> {
        return repository.getAllWeatherLocationList()
    }
}