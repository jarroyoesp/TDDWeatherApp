package es.jarroyo.tddweatherapp.data.repository

import es.jarroyo.tddweatherapp.data.source.disk.DiskDataSource
import es.jarroyo.tddweatherapp.data.source.network.INetworkDataSource
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeather
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherByNameRequest
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.getWeatherList.GetWeatherListRequest

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
    suspend fun getWeatherList(request: GetWeatherListRequest): Response<List<Response<CurrentWeather>>> {
        /*val result = networkDataSource.getCurrentWeatherByName(byNameRequest)
        return result*/

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

        val fromDisk = diskDataSource.getAllCurrentWeatherList()

        return Response.Success(weatherList)
    }
}