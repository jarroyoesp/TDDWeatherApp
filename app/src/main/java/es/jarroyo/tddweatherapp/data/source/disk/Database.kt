package es.jarroyo.tddweatherapp.data.source.disk

import android.app.Application
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import es.jarroyo.tddweatherapp.data.entity.WeatherLocationEntity
import es.jarroyo.tddweatherapp.data.source.disk.dao.WeatherLocationDao

@Database(entities = arrayOf(
        WeatherLocationEntity::class), version = 1)
abstract class Database : RoomDatabase() {

    abstract fun weatherLocationDao(): WeatherLocationDao

    companion object {
        private val DATABASE_NAME: String = "TDDWeatherApp_db"

        fun createInstance(appContext: Application):
                es.jarroyo.tddweatherapp.data.source.disk.Database = Room.databaseBuilder(appContext,
                es.jarroyo.tddweatherapp.data.source.disk.Database::class.java, DATABASE_NAME)
            .build()
    }



}
