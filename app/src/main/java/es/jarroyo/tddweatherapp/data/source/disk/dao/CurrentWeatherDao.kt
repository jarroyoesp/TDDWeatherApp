package es.jarroyo.tddweatherapp.data.source.disk.dao

import android.arch.persistence.room.*
import es.jarroyo.tddweatherapp.data.entity.CurrentWeatherEntity

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrentWeather(currentWeatherEntity: CurrentWeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entityList: List<CurrentWeatherEntity>)

    @Delete
    fun deleteCurrentWeather(currentWeatherEntity: CurrentWeatherEntity)

    @Query("DELETE FROM TABLE_CURRENT_WEATHER")
    fun deleteAll()

    @Query("SELECT * FROM TABLE_CURRENT_WEATHER")
    fun getAllCurrentWeatherList():List<CurrentWeatherEntity>
}