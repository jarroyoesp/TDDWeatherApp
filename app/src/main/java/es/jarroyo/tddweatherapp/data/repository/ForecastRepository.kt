package es.jarroyo.tddweatherapp.data.repository

import es.jarroyo.tddweatherapp.data.source.disk.DiskDataSource
import es.jarroyo.tddweatherapp.data.source.network.INetworkDataSource
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.forecast.Forecast
import es.jarroyo.tddweatherapp.domain.usecase.forecast.getForecast.GetForecastRequest

class ForecastRepository(
    private val networkDataSource: INetworkDataSource,
    private val diskDataSource: DiskDataSource
) {

    val TAG = ForecastRepository::class.java.simpleName

    /***********************************************************************************************
     * GET CURRENT WEATHER
     **********************************************************************************************/
    suspend fun getForecast(request: GetForecastRequest): Response<Forecast> {
        val result = networkDataSource.getForecast(request.cityName)
        return result
    }
}