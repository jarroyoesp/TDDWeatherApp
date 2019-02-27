package es.jarroyo.tddweatherapp.data.source.disk

import android.app.Application
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import es.jarroyo.tddweatherapp.data.entity.CurrentWeatherEntity
import es.jarroyo.tddweatherapp.data.entity.WeatherLocationEntity
import es.jarroyo.tddweatherapp.data.entity.typeConverter.WeatherEntityConverter
import es.jarroyo.tddweatherapp.data.source.disk.dao.CurrentWeatherDao
import es.jarroyo.tddweatherapp.data.source.disk.dao.WeatherLocationDao

@Database(
    entities = arrayOf(
        WeatherLocationEntity::class,
        CurrentWeatherEntity::class
    ), version = 1
)
@TypeConverters(
    WeatherEntityConverter::class
)
abstract class Database : RoomDatabase() {

    abstract fun weatherLocationDao(): WeatherLocationDao
    abstract fun currentWeatherDao(): CurrentWeatherDao

    companion object {
        private val DATABASE_NAME: String = "TDDWeatherApp_db"

        fun createInstance(appContext: Application):
                es.jarroyo.tddweatherapp.data.source.disk.Database = Room.databaseBuilder(
            appContext,
            es.jarroyo.tddweatherapp.data.source.disk.Database::class.java, DATABASE_NAME
        )
            .build()
    }


}
