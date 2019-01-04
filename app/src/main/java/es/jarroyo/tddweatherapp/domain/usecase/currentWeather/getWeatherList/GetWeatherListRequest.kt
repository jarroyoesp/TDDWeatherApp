package es.jarroyo.tddweatherapp.domain.usecase.currentWeather.getWeatherList

import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocation
import es.jarroyo.tddweatherapp.domain.usecase.base.BaseRequest

class GetWeatherListRequest(var locationList: List<WeatherLocation>) : BaseRequest {
    override fun validate(): Boolean {
        return true
    }
}