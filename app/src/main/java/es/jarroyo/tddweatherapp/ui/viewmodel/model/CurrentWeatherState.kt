package es.jarroyo.tddweatherapp.ui.viewmodel.model

import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeather


sealed class CurrentWeatherState {
    abstract val response: Response<CurrentWeather>
}
data class DefaultCurrentWeatherState(override val response: Response<CurrentWeather>) : CurrentWeatherState()
data class LoadingCurrentWeatherState(override val response: Response<CurrentWeather>) : CurrentWeatherState()
data class ErrorCurrentWeatherState(override val response: Response<CurrentWeather>) : CurrentWeatherState()