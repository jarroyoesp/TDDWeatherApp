package es.jarroyo.tddweatherapp.ui.viewmodel.model

import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocation


sealed class CurrentLocationState {
    abstract val response: Response<WeatherLocation>
}
data class DefaultCurrentLocationState(override val response: Response<WeatherLocation>) : CurrentLocationState()
data class LoadingCurrentLocationState(override val response: Response<WeatherLocation>) : CurrentLocationState()
data class ErrorCurrentLocationState(override val response: Response<WeatherLocation>) : CurrentLocationState()