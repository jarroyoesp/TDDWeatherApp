package es.jarroyo.tddweatherapp.data.mapper.location

import es.jarroyo.tddweatherapp.data.entity.WeatherLocationEntity
import es.jarroyo.tddweatherapp.data.mapper.Mapper
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocation


class WeatherLocationEntitytoWeatherLocationMapper : Mapper<WeatherLocationEntity, WeatherLocation> {
    override fun map(input: WeatherLocationEntity): WeatherLocation =
        WeatherLocation(
            getId(input),
            getCityName(input),
            getUserId(input)
        )

    private fun getId(input: WeatherLocationEntity): Long {
        return input.id
    }

    private fun getCityName(input: WeatherLocationEntity): String {
        return input.cityName
    }

    private fun getUserId(input: WeatherLocationEntity): Long {
        return input.userId
    }


    fun mapList(alarmsEntityList: List<WeatherLocationEntity>?): MutableList<WeatherLocation> {
        var alarmsList = mutableListOf<WeatherLocation>()
        if (alarmsEntityList != null) {
            for (alarmEntity in alarmsEntityList) {
                val alarm = map(alarmEntity)
                alarmsList.add(alarm)
            }
        }
        return alarmsList
    }
}