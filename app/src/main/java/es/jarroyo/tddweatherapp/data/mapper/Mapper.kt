package es.jarroyo.tddweatherapp.data.mapper

import es.jarroyo.tddweatherapp.data.exception.MapperException


internal interface Mapper<in I, out O> {

    @Throws(MapperException::class)
    fun map(input: I): O
}
