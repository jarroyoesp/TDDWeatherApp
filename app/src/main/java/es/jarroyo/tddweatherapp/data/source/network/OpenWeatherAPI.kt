package es.jarroyo.tddweatherapp.data.source.network

import es.jarroyo.tddweatherapp.BuildConfig
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeather
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface OpenWeatherAPI {

    @GET("/data/2.5/weather?id=2172797&APPID=${BuildConfig.OPEN_WEATHER_API_KEY}")
    fun currentWeather(
        //@Path("id") id: String
    ): Deferred<CurrentWeather>
}