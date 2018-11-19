package es.jarroyo.tddweatherapp.domain.usecase.currentWeather

import es.jarroyo.tddweatherapp.domain.usecase.base.BaseRequest

class GetCurrentWeatherRequest(var cityId: Int) : BaseRequest {
    override fun validate(): Boolean {
        return true
    }
}