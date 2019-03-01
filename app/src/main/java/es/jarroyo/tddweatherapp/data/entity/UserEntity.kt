package es.jarroyo.tddweatherapp.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import es.jarroyo.tddweatherapp.domain.model.user.User


@Entity(tableName = "USER_TABLE")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var name: String
) {
    fun toModel() = User(id, name)
}