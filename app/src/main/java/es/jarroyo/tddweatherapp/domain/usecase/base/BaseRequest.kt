package es.jarroyo.tddweatherapp.domain.usecase.base

interface BaseRequest {
    fun validate(): Boolean
}
