package es.jarroyo.tddweatherapp.data.repository

import es.jarroyo.tddweatherapp.data.source.disk.DiskDataSource
import es.jarroyo.tddweatherapp.data.source.network.INetworkDataSource
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeather
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherByNameRequest
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.getWeatherList.GetWeatherListRequest
import kotlinx.coroutines.channels.Channel

class WeatherRepository(
    private val networkDataSource: INetworkDataSource,
    private val diskDataSource: DiskDataSource
) {

    val TAG = WeatherRepository::class.java.simpleName

    /***********************************************************************************************
     * GET CURRENT WEATHER
     **********************************************************************************************/
    suspend fun getCurrentWeather(request: GetCurrentWeatherByNameRequest): Response<CurrentWeather> {
        val result = networkDataSource.getCurrentWeatherByName(request.cityName)

        // Save in database
        if (result is Response.Success) {
            val currentWeather = (result as Response.Success).data
            diskDataSource.insertCurrentWeather(currentWeather.toEntity())
        }

        return result
    }

    /***********************************************************************************************
     * GET WEATHER LIST
     **********************************************************************************************/
    suspend fun getWeatherList(request: GetWeatherListRequest, channel: Channel<Response<List<Response<CurrentWeather>>>>): Response<List<Response<CurrentWeather>>> {
        /*val result = networkDataSource.getCurrentWeatherByName(byNameRequest)
        return result*/

        // FROM DISK DATA SOURCE
        val currentWeatherLocalList = diskDataSource.getAllCurrentWeatherList()
        val list = mutableListOf<Response<CurrentWeather>>()
        if (currentWeatherLocalList != null) {
            for (currentWeatherEntity in currentWeatherLocalList) {
                list.add(Response.Success(currentWeatherEntity.toModel()))
            }
            channel.send(Response.Success(list))
        }

        // FROM REMOTE
        var weatherList = mutableListOf<Response<CurrentWeather>>()

        for (location in request.locationList) {
            val result = networkDataSource.getCurrentWeatherByName(location.cityName)
            weatherList.add(result)

            // Save in database
            if (result is Response.Success) {
                val currentWeather = (result as Response.Success).data
                diskDataSource.insertCurrentWeather(currentWeather.toEntity())
            }

        }

        channel.send(Response.Success(weatherList))

        return Response.Success(weatherList)
    }
}