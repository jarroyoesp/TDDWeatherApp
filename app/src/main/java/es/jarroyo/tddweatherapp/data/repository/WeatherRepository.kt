package es.jarroyo.tddweatherapp.data.repository

import es.jarroyo.tddweatherapp.data.source.disk.DiskDataSource
import es.jarroyo.tddweatherapp.data.source.network.INetworkDataSource
import es.jarroyo.tddweatherapp.domain.model.forecast.CurrentWeather

class WeatherRepository(
    private val networkDataSource: INetworkDataSource,
    private val diskDataSource: DiskDataSource
) {

    val TAG = WeatherRepository::class.java.simpleName

    /***********************************************************************************************
     * GET REPOSITORIES LIST
     **********************************************************************************************/
    suspend fun getCurrentWeather(): CurrentWeather {
        val result = networkDataSource.getCurrentWeather()
        return result!!
    }
}