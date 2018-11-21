package es.jarroyo.tddweatherapp.domain.usecase.currentWeather

import es.jarroyo.tddweatherapp.domain.usecase.base.BaseRequest

class GetCurrentWeatherByNameRequest(var cityName: String) : BaseRequest {
    override fun validate(): Boolean {
        return true
    }
}