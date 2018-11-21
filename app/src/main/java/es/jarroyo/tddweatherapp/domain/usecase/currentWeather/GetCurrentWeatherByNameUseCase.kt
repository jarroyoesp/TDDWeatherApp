package es.jarroyo.tddweatherapp.domain.usecase.currentWeather

import es.jarroyo.tddweatherapp.data.repository.WeatherRepository
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeather
import es.jarroyo.tddweatherapp.domain.usecase.base.BaseUseCase

open class GetCurrentWeatherByNameUseCase(val repository: WeatherRepository) : BaseUseCase<GetCurrentWeatherByNameRequest, CurrentWeather>() {

    override suspend fun run(): Response<CurrentWeather> {
        return repository.getCurrentWeather(request!!)
    }
}