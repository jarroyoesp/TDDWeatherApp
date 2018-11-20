package es.jarroyo.tddweatherapp.data.source.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.microhealth.lmc.utils.NetworkSystemAbstract
import es.jarroyo.tddweatherapp.data.exception.NetworkConnectionException
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeather
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NetworkDataSource(private val networkSystem: NetworkSystemAbstract) : INetworkDataSource(networkSystem) {

    /**
     * GET CURRENT WEATHER
     */
    override suspend fun getCurrentWeather(request: GetCurrentWeatherRequest): CurrentWeather? {

        if (networkSystem.isNetworkAvailable()) {
            val retrofit = Retrofit.Builder().apply {
                baseUrl("https://api.openweathermap.org")
                addConverterFactory(GsonConverterFactory.create())
                addCallAdapterFactory(CoroutineCallAdapterFactory())
            }.build()

            val github = retrofit.create(OpenWeatherAPI::class.java)
            val repositories =
                github.currentWeather()// request.cityId.toString()
                    .await()
            return repositories
        } else {
            throw NetworkConnectionException()
        }
    }

}