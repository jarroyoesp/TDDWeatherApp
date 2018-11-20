package es.jarroyo.tddweatherapp.ui.home.model

import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeather


sealed class ForecastState {
    abstract val response: Response<CurrentWeather>
}
data class DefaultForecastState(override val response: Response<CurrentWeather>) : ForecastState()
data class LoadingForecastState(override val response: Response<CurrentWeather>) : ForecastState()
data class ErrorForecastState(override val response: Response<CurrentWeather>) : ForecastState()