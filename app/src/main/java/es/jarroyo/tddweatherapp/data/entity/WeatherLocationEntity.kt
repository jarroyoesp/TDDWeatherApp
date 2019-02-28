package es.jarroyo.tddweatherapp.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocation

fun WeatherLocationEntity.toDomain(): WeatherLocation = WeatherLocation(id, cityName)

@Entity(
    tableName = "WEATHER_LOCATION", foreignKeys = arrayOf(
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("userId"),
            onDelete = ForeignKey.CASCADE
        )
    )
)
class WeatherLocationEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var cityName: String = "",
    var userId: Long = 0
)
