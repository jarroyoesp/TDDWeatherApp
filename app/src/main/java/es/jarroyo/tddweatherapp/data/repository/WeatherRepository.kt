package es.jarroyo.tddweatherapp.data.repository

import es.jarroyo.tddweatherapp.data.source.disk.DiskDataSource
import es.jarroyo.tddweatherapp.data.source.network.INetworkDataSource
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeather
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherByNameRequest

class WeatherRepository(
    private val networkDataSource: INetworkDataSource,
    private val diskDataSource: DiskDataSource
) {

    val TAG = WeatherRepository::class.java.simpleName

    /***********************************************************************************************
     * GET REPOSITORIES LIST
     **********************************************************************************************/
    suspend fun getCurrentWeather(byNameRequest: GetCurrentWeatherByNameRequest): Response<CurrentWeather> {
        val result = networkDataSource.getCurrentWeatherByName(byNameRequest)
        return result
    }
}