package es.jarroyo.tddweatherapp.ui.viewmodel.model.forecast

import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.forecast.Forecast


sealed class GetForecastateState {
    abstract val response: Response<Forecast>?
}
data class SuccessGetForecastState(override val response: Response<Forecast>) : GetForecastateState()
data class LoadingGetForecastState(override val response: Response<Forecast>? = null) : GetForecastateState()
data class ErrorGetForecastState(override val response: Response<Forecast>) : GetForecastateState()