package es.jarroyo.tddweatherapp.ui.home.model

import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.location.CurrentLocation


sealed class CurrentLocationState {
    abstract val response: Response<CurrentLocation>
}
data class DefaultCurrentLocationState(override val response: Response<CurrentLocation>) : CurrentLocationState()
data class LoadingCurrentLocationState(override val response: Response<CurrentLocation>) : CurrentLocationState()
data class ErrorCurrentLocationState(override val response: Response<CurrentLocation>) : CurrentLocationState()