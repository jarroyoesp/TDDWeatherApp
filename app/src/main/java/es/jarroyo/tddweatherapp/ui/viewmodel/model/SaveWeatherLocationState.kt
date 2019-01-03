package es.jarroyo.tddweatherapp.ui.viewmodel.model

import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocation


sealed class SaveWeatherLocationState {
    abstract val response: Response<List<WeatherLocation>>
}
data class SuccessSaveWeatherLocationState(override val response: Response<List<WeatherLocation>>) : SaveWeatherLocationState()
data class LoadingSaveWeatherLocationState(override val response: Response<List<WeatherLocation>>) : SaveWeatherLocationState()
data class ErrorSaveWeatherLocationState(override val response: Response<List<WeatherLocation>>) : SaveWeatherLocationState()