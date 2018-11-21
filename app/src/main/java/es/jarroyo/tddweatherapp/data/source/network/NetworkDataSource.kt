package es.jarroyo.tddweatherapp.data.source.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.microhealth.lmc.utils.NetworkSystemAbstract
import es.jarroyo.tddweatherapp.data.exception.NetworkConnectionException
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeather
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherRequest
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class NetworkDataSource(private val networkSystem: NetworkSystemAbstract) : INetworkDataSource(networkSystem) {

    /**
     * GET CURRENT WEATHER
     */
    override suspend fun getCurrentWeather(request: GetCurrentWeatherRequest): Response<CurrentWeather> {

        if (networkSystem.isNetworkAvailable()) {
            val retrofit = Retrofit.Builder().apply {
                baseUrl("https://api.openweathermap.org")
                client(okHttpClient)
                addConverterFactory(GsonConverterFactory.create())
                addCallAdapterFactory(CoroutineCallAdapterFactory())
            }.build()

            val openWeatherAPI = retrofit.create(OpenWeatherAPI::class.java)

            var response = Response<CurrentWeather>()
            try {
                val currentWeather =
                    openWeatherAPI.currentWeather(request.cityId.toString())
                        .await()

                response.data = currentWeather
            } catch (e: Exception){
                response.exception = e
            }
            return response

        } else {
            throw NetworkConnectionException()
        }
    }

    var okHttpClient = OkHttpClient.Builder()
        .addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val request = chain.request()
                val response = chain.proceed(request)

                // todo deal with the issues the way you need to
                if (response.code() == 500) {
                    return response
                }

                return response
            }
        })
        .build()

}