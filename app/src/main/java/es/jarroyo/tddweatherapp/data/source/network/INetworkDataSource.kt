package es.jarroyo.tddweatherapp.data.source.network

import com.microhealth.lmc.utils.NetworkSystemAbstract
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeather

open abstract class INetworkDataSource(private val networkSystem: NetworkSystemAbstract) {

    /**
     * GET CURRENT WEATHER
     */
    abstract suspend fun getCurrentWeather(): CurrentWeather?

}