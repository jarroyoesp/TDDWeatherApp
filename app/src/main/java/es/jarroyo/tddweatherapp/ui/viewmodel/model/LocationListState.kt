package es.jarroyo.tddweatherapp.ui.viewmodel.model

import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocation


sealed class LocationListState {
    abstract val response: Response<List<WeatherLocation>>
}
data class SuccessLocationListState(override val response: Response<List<WeatherLocation>>) : LocationListState()
data class LoadingLocationListState(override val response: Response<List<WeatherLocation>>) : LocationListState()
data class ErrorLocationListState(override val response: Response<List<WeatherLocation>>) : LocationListState()