package es.jarroyo.tddweatherapp.domain.usecase.location.saveWeatherLocation

import es.jarroyo.tddweatherapp.data.repository.LocationRepository
import es.jarroyo.tddweatherapp.data.repository.WeatherRepository
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeather
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocation
import es.jarroyo.tddweatherapp.domain.usecase.base.BaseUseCase
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherByNameRequest

open class SaveWeatherLocationUseCase(val repository: LocationRepository) : BaseUseCase<SaveWeatherLocationRequest, WeatherLocation>() {

    override suspend fun run(): Response<WeatherLocation> {
        return repository.saveWeatherLocationList(request!!)
    }
}