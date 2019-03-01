package es.jarroyo.tddweatherapp.domain.model.user

import es.jarroyo.tddweatherapp.data.entity.UserEntity


data class User (
    var id: Long,
    var name: String
) {
    fun toEntity():UserEntity {
        return UserEntity(id, name)
    }

}