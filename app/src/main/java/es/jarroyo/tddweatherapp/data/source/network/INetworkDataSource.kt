package es.jarroyo.tddweatherapp.data.source.network

import com.microhealth.lmc.utils.NetworkSystemAbstract
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeather
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherByNameRequest

open abstract class INetworkDataSource(private val networkSystem: NetworkSystemAbstract) {

    /**
     * GET CURRENT WEATHER
     */
    abstract suspend fun getCurrentWeatherByName(byNameRequest: GetCurrentWeatherByNameRequest): Response<CurrentWeather>

}