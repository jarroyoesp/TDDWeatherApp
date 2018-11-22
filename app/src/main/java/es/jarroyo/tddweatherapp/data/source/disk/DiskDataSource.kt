package es.jarroyo.tddweatherapp.data.source.disk

import android.app.Application
import android.content.Context
import es.jarroyo.tddweatherapp.data.entity.WeatherLocationEntity

class DiskDataSource(appContext: Context) {

    companion object {
        var database: Database? = null
    }

    init {
        database = Database.createInstance(appContext as Application)
    }

    /***********************************************************************************************
     * WEATHER LOCATION
     **********************************************************************************************/
    fun insertWeatherLocation(weatherLocationEntity: WeatherLocationEntity) = database?.weatherLocationDao()?.insertWeatherLocation(weatherLocationEntity)

    fun deleteAllUsers() = database?.weatherLocationDao()?.deleteAllWeatherLocation()

    fun deleteWeatherLocation(weatherLocationEntity: WeatherLocationEntity) = database?.weatherLocationDao()?.deleteWeatherLocation(weatherLocationEntity)

    fun getAllWeatherLocationList() = database?.weatherLocationDao()?.getAllWeatherLocationList()

}
