package es.jarroyo.tddweatherapp.data.mapper.location

import es.jarroyo.tddweatherapp.data.entity.WeatherLocationEntity
import es.jarroyo.tddweatherapp.data.mapper.Mapper
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocation


class WeatherLocationToWeatherLocationEntityMapper : Mapper<WeatherLocation, WeatherLocationEntity> {
    override fun map(input: WeatherLocation): WeatherLocationEntity =
        WeatherLocationEntity(
            getId(input),
            getCityName(input),
            getUserId(input)
        )

    private fun getId(input: WeatherLocation): Long {
        return input.id
    }

    private fun getCityName(input: WeatherLocation): String {
        return input.cityName
    }

    private fun getUserId(input: WeatherLocation): Long {
        return input.userId
    }

    fun mapList(alarmsEntityList: List<WeatherLocation>?): MutableList<WeatherLocationEntity> {
        var alarmsList = mutableListOf<WeatherLocationEntity>()
        if (alarmsEntityList != null) {
            for (alarmEntity in alarmsEntityList) {
                val alarm = map(alarmEntity)
                alarmsList.add(alarm)
            }
        }
        return alarmsList
    }
}