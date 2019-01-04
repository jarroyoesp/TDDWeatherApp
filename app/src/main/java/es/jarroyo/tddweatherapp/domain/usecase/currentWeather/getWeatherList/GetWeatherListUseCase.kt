package es.jarroyo.tddweatherapp.domain.usecase.currentWeather.getWeatherList

import es.jarroyo.tddweatherapp.data.repository.WeatherRepository
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeather
import es.jarroyo.tddweatherapp.domain.usecase.base.BaseUseCase

open class GetWeatherListUseCase(val repository: WeatherRepository) : BaseUseCase<GetWeatherListRequest, List<Response<CurrentWeather>>>() {

    override suspend fun run(): Response<List<Response<CurrentWeather>>> {
        return repository.getWeatherList(request!!)
    }
}