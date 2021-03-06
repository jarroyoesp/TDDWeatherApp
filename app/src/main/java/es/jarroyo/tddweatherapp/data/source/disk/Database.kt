package es.jarroyo.tddweatherapp.data.source.disk

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import es.jarroyo.tddweatherapp.data.entity.CurrentWeatherEntity
import es.jarroyo.tddweatherapp.data.entity.UserEntity
import es.jarroyo.tddweatherapp.data.entity.WeatherLocationEntity
import es.jarroyo.tddweatherapp.data.entity.typeConverter.WeatherEntityConverter
import es.jarroyo.tddweatherapp.data.source.disk.dao.CurrentWeatherDao
import es.jarroyo.tddweatherapp.data.source.disk.dao.UserDao
import es.jarroyo.tddweatherapp.data.source.disk.dao.WeatherLocationDao

@Database(
    entities = arrayOf(
        WeatherLocationEntity::class,
        CurrentWeatherEntity::class,
        UserEntity::class
    ), version = 1
)
@TypeConverters(
    WeatherEntityConverter::class
)
abstract class Database : RoomDatabase() {

    abstract fun weatherLocationDao(): WeatherLocationDao
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun userDao(): UserDao

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
