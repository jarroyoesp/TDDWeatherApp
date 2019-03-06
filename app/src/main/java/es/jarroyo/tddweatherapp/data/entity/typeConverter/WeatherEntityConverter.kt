package es.jarroyo.tddweatherapp.data.entity.typeConverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.jarroyo.tddweatherapp.data.entity.WeatherEntity
import java.util.*

class WeatherEntityConverter {
    private val gson = Gson()
    @TypeConverter
    fun stringToList(data: String?): List<WeatherEntity> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<WeatherEntity>>() {

        }.getType()

        return gson.fromJson<List<WeatherEntity>>(data, listType)
    }

    @TypeConverter
    fun ListToString(someObjects: List<WeatherEntity>): String {
        return gson.toJson(someObjects)
    }
}