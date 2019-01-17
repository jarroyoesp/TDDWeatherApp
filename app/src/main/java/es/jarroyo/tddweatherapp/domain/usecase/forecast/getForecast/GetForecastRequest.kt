package es.jarroyo.tddweatherapp.domain.usecase.forecast.getForecast

import es.jarroyo.tddweatherapp.domain.usecase.base.BaseRequest

class GetForecastRequest(var cityName: String) : BaseRequest {
    override fun validate(): Boolean {
        return true
    }
}