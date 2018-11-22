package es.jarroyo.tddweatherapp.domain.usecase.location.saveWeatherLocation

import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocation
import es.jarroyo.tddweatherapp.domain.usecase.base.BaseRequest

class SaveWeatherLocationRequest(var weatherLocation: WeatherLocation) : BaseRequest {
    override fun validate(): Boolean {
        return true
    }
}