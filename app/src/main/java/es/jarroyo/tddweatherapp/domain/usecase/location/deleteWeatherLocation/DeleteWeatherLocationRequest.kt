package es.jarroyo.tddweatherapp.domain.usecase.location.deleteWeatherLocation

import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocation
import es.jarroyo.tddweatherapp.domain.usecase.base.BaseRequest

class DeleteWeatherLocationRequest(var weatherLocation: WeatherLocation) : BaseRequest {
    override fun validate(): Boolean {
        return true
    }
}