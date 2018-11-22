package es.jarroyo.tddweatherapp.data.repository

import android.location.Location
import es.jarroyo.tddweatherapp.data.source.disk.DiskDataSource
import es.jarroyo.tddweatherapp.domain.model.Response

class LocationRepository(
    private val diskDataSource: DiskDataSource
) {

    val TAG = LocationRepository::class.java.simpleName

    /***********************************************************************************************
     * GET REPOSITORIES LIST
     **********************************************************************************************/
    suspend fun getCurrentLocation(): Response<Location> {
        // Todo get current location
        return Response(null)
    }
}