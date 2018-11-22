package es.jarroyo.tddweatherapp.data.repository

import es.jarroyo.tddweatherapp.data.source.disk.DiskDataSource
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.location.CurrentLocation
import es.jarroyo.tddweatherapp.domain.model.location.CurrentLocationFactory

class LocationRepository(
    private val diskDataSource: DiskDataSource
) {

    val TAG = LocationRepository::class.java.simpleName

    /***********************************************************************************************
     * GET REPOSITORIES LIST
     **********************************************************************************************/
    suspend fun getCurrentLocation(): Response<CurrentLocation> {
        // Todo get current location
        return Response(CurrentLocationFactory.createCurrentLocationTest())
    }
}