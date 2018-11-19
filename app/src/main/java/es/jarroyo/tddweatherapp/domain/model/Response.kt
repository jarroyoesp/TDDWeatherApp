package es.jarroyo.tddweatherapp.domain.model

class Response<T>(
    var data: T? = null,
    var error: String? = null,
    var exception: Exception? = null
)