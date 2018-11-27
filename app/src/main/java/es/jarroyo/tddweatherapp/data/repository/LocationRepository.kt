package es.jarroyo.tddweatherapp.data.repository

import es.jarroyo.tddweatherapp.data.mapper.location.WeatherLocationEntitytoWeatherLocationMapper
import es.jarroyo.tddweatherapp.data.mapper.location.WeatherLocationToWeatherLocationEntityMapper
import es.jarroyo.tddweatherapp.data.source.disk.DiskDataSource
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocationFactory
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocation
import es.jarroyo.tddweatherapp.domain.usecase.location.saveWeatherLocation.SaveWeatherLocationRequest
import es.jarroyo.tddweatherapp.ui.App


class LocationRepository(
    private val app: App,
    private val diskDataSource: DiskDataSource,
    private val weatherLocationToWeatherLocationEntityMapper: WeatherLocationToWeatherLocationEntityMapper,
    private val weatherLocationEntitytoWeatherLocationMapper: WeatherLocationEntitytoWeatherLocationMapper
) {

    val TAG = LocationRepository::class.java.simpleName

    /***********************************************************************************************
     * GET CURRENT LOCATION
     **********************************************************************************************/
    fun getCurrentLocation(): Response<WeatherLocation> {
        // Todo get current location
        return Response(WeatherLocationFactory.createCurrentLocationTest())
    }

    /***********************************************************************************************
     * SAVE WEATHER LOCATION
     **********************************************************************************************/
    fun saveWeatherLocationList(request: SaveWeatherLocationRequest): Response<WeatherLocation> {
        diskDataSource.insertWeatherLocation(weatherLocationToWeatherLocationEntityMapper.map(request.weatherLocation))
        return Response(request.weatherLocation)
    }

    /***********************************************************************************************
     * GET ALL WEATHER LOCATION LIST
     **********************************************************************************************/
    fun getAllWeatherLocationList(): Response<List<WeatherLocation>> {
        val weatherLocationListEntity = diskDataSource.getAllWeatherLocationList()
        return Response(weatherLocationEntitytoWeatherLocationMapper.mapList(weatherLocationListEntity))
    }


/*    fun requestCurrentLocation() {
        if ( ContextCompat.checkSelfPermission( app, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {
            val locationManager = app.getSystemService(LOCATION_SERVICE) as LocationManager

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,100L, 2.0f,  object : LocationListener {
                override fun onLocationChanged(p0: Location?) {
                    Log.d(TAG, "onLocationChanged")
                }

                override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                    Log.d(TAG, "onLocationChanged")
                }

                override fun onProviderEnabled(p0: String?) {
                    Log.d(TAG, "onProviderEnabled")
                }

                override fun onProviderDisabled(p0: String?) {
                    Log.d(TAG, "onProviderDisabled")
                }
            })
            *//*locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER)
            val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)*//*

        } else {

        }
    }*/
}