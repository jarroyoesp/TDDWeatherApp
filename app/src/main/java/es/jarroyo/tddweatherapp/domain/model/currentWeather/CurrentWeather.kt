package es.jarroyo.tddweatherapp.domain.model.currentWeather

import es.jarroyo.tddweatherapp.data.entity.*

data class CurrentWeather(
    val base: String,
    val clouds: Clouds?,
    val cod: Int,
    val coord: Coord?,
    val dt: Int,
    val id: Int,
    val main: Main?,
    val name: String,
    val rain: Rain?,
    val sys: Sys?,
    val weather: MutableList<Weather>?,
    val wind: Wind?
) {
    fun toEntity(): CurrentWeatherEntity {
        return CurrentWeatherEntity(
            base,
            clouds?.toEntity(),
            cod,
            coord?.toEntity(),
            dt,
            id,
            main?.toEntity(),
            name,
            rain?.toEntity(),
            sys?.toEntity(),
            Weather.convertiListToEntity(weather),
            wind?.toEntity() )
    }

    companion object {
        fun convertiListToEntity(entityList: MutableList<CurrentWeather>?): MutableList<CurrentWeatherEntity> {
            var list = mutableListOf<CurrentWeatherEntity>()
            if (entityList != null) {
                for (entity in entityList) {
                    list.add(entity.toEntity())
                }
            }
            return list
        }
    }
}

data class Main(
    val humidity: Double,
    val pressure: Double,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
) {
    fun toEntity(): MainEntity {
        return MainEntity(humidity, pressure, temp, temp_max, temp_min)
    }
}
data class Wind(
    val deg: Double,
    val speed: Double
){
    fun toEntity(): WindEntity {
        return WindEntity(deg, speed)
    }
}

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
){
    fun toEntity(): WeatherEntity {
        return WeatherEntity(description, icon, id, main)
    }

    companion object {
        fun convertiListToEntity(entityList: MutableList<Weather>?): MutableList<WeatherEntity> {
            var list = mutableListOf<WeatherEntity>()
            if (entityList != null) {
                for (entity in entityList) {
                    list.add(entity.toEntity())
                }
            }
            return list
        }
    }
}

data class Rain(
    val h: Int // Todo 3h
){
    fun toEntity(): RainEntity {
        return RainEntity(h)
    }
}

data class Sys(
    val country: String,
    val id: Int,
    val message: Double,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
){
    fun toEntity(): SysEntity {
        return SysEntity(country, id, message, sunrise, sunset, type)
    }
}

data class Clouds(
    val all: Int
){
    fun toEntity(): CloudsEntity {
        return CloudsEntity(all)
    }
}

data class Coord(
    val lat: Double,
    val lon: Double
){
    fun toEntity(): CoordEntity {
        return CoordEntity(lat, lon)
    }
}