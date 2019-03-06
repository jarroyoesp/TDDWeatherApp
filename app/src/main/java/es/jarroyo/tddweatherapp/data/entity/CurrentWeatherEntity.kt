package es.jarroyo.tddweatherapp.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import es.jarroyo.tddweatherapp.data.entity.typeConverter.WeatherEntityConverter
import es.jarroyo.tddweatherapp.domain.model.currentWeather.*

@Entity(tableName = "TABLE_CURRENT_WEATHER")
data class CurrentWeatherEntity(
    var base: String,
    @Embedded(prefix = "clouds_")
    var clouds: CloudsEntity?,
    var cod: Int,
    @Embedded(prefix = "coord_")
    var coord: CoordEntity?,
    var dt: Int,
    @PrimaryKey
    var id: Int,
    @Embedded(prefix = "main_")
    var main: MainEntity?,
    var name: String,
    @Embedded(prefix = "rain_")
    var rain: RainEntity?,
    @Embedded(prefix = "sys_")
    var sys: SysEntity?,
    @TypeConverters(WeatherEntityConverter::class)
    var weather: MutableList<WeatherEntity>?,
    @Embedded(prefix = "wind_")
    var wind: WindEntity?
) {
    fun toModel(): CurrentWeather {
        return CurrentWeather(
            base,
            clouds?.toModel(),
            cod,
            coord?.toModel(),
            dt,
            id,
            main?.toModel(),
            name,
            rain?.toModel(),
            sys?.toModel(),
            WeatherEntity.convertiListToModel(weather),
            wind?.toModel() )
    }

    companion object {
        fun convertiListToModel(entityList: MutableList<CurrentWeatherEntity>?): MutableList<CurrentWeather> {
            var list = mutableListOf<CurrentWeather>()
            if (entityList != null) {
                for (entity in entityList) {
                    list.add(entity.toModel())
                }
            }
            return list
        }
    }
}

data class MainEntity(
    var humidity: Double,
    var pressure: Double,
    var temp: Double,
    var temp_max: Double,
    var temp_min: Double
) {
    fun toModel(): Main {
        return Main(humidity, pressure, temp, temp_max, temp_min)
    }
}

data class WindEntity(
    var deg: Double,
    var speed: Double
){
    fun toModel(): Wind {
        return Wind(deg, speed)
    }
}

data class WeatherEntity(
    var description: String,
    var icon: String,
    var id: Int,
    var main: String
){
    fun toModel(): Weather {
        return Weather(description, icon, id, main)
    }


    companion object {
        fun convertiListToModel(entityList: MutableList<WeatherEntity>?): MutableList<Weather> {
            var list = mutableListOf<Weather>()
            if (entityList != null) {
                for (entity in entityList) {
                    list.add(entity.toModel())
                }
            }
            return list
        }

    }
}

data class RainEntity(
    var h: Int // Todo 3h
) {
    fun toModel(): Rain {
        return Rain(h)
    }
}

data class SysEntity(
    var country: String,
    var id: Int,
    var message: Double,
    var sunrise: Int,
    var sunset: Int,
    var type: Int
) {
    fun toModel(): Sys {
        return Sys(country, id, message, sunrise, sunset, type)
    }
}

data class CloudsEntity(
    var all: Int
) {
    fun toModel(): Clouds {
        return Clouds(all)
    }
}

data class CoordEntity(
    var lat: Double,
    var lon: Double
) {
    fun toModel(): Coord {
        return Coord(lat, lon)
    }
}