package es.jarroyo.tddweatherapp.domain.model

import es.jarroyo.tddweatherapp.data.entity.UserEntity


data class User (
    var id: Long,
    var name: String
) {
    fun toEntity():UserEntity {
        return UserEntity(id, name)
    }

}