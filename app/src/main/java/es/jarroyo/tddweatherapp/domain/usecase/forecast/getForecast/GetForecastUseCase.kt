package es.jarroyo.tddweatherapp.domain.usecase.forecast.getForecast

import es.jarroyo.tddweatherapp.data.repository.ForecastRepository
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.forecast.Forecast
import es.jarroyo.tddweatherapp.domain.usecase.base.BaseUseCase

open class GetForecastUseCase(val repository: ForecastRepository) : BaseUseCase<GetForecastRequest, Forecast>() {

    override suspend fun run(): Response<Forecast> {
        return repository.getForecast(request!!)
    }
}