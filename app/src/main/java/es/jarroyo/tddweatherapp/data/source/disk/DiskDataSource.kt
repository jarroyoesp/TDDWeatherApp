package es.jarroyo.tddweatherapp.data.source.disk

import android.app.Application
import android.content.Context
import es.jarroyo.tddweatherapp.data.entity.CurrentWeatherEntity
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

    fun deleteAll() = database?.weatherLocationDao()?.deleteAll()

    fun deleteWeatherLocation(weatherLocationEntity: WeatherLocationEntity) = database?.weatherLocationDao()?.deleteWeatherLocation(weatherLocationEntity)

    fun getAllWeatherLocationList() = database?.weatherLocationDao()?.getAllWeatherLocationList()

    /***********************************************************************************************
     * CURRENT WEATHER
     **********************************************************************************************/
    fun insertCurrentWeather(currentWeatherEntity: CurrentWeatherEntity) = database?.currentWeatherDao()?.insertCurrentWeather(currentWeatherEntity)

    fun insertAll(currentWeatherEntityList: List<CurrentWeatherEntity>) = database?.currentWeatherDao()?.insertAll(currentWeatherEntityList)

    fun deleteAllCurrentWeather() = database?.currentWeatherDao()?.deleteAll()

    fun getAllCurrentWeatherList() = database?.currentWeatherDao()?.getAllCurrentWeatherList()


    /***********************************************************************************************
     * COMMON
     **********************************************************************************************/
    fun deleteAllTables() {
        database?.weatherLocationDao()?.deleteAll()
    }
}
