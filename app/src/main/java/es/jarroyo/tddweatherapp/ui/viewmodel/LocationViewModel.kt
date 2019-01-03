package es.jarroyo.tddweatherapp.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocation
import es.jarroyo.tddweatherapp.domain.usecase.location.currentLocation.GetCurrentLocationUseCase
import es.jarroyo.tddweatherapp.domain.usecase.location.getAllWeatherLocationList.GetAllWeatherLocationListUseCase
import es.jarroyo.tddweatherapp.domain.usecase.location.saveWeatherLocation.SaveWeatherLocationRequest
import es.jarroyo.tddweatherapp.domain.usecase.location.saveWeatherLocation.SaveWeatherLocationUseCase
import es.jarroyo.tddweatherapp.ui.viewmodel.model.*
import es.jarroyo.tddweatherapp.utils.launchSilent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LocationViewModel
    @Inject
    constructor(private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
                private val saveWeatherLocationUseCase: SaveWeatherLocationUseCase,
                private val getAllWeatherLocationListUseCase: GetAllWeatherLocationListUseCase,
                private val coroutineContext: CoroutineContext)
    : ViewModel() {

    private var job: Job = Job()

    var currentLocationStateLiveData = MutableLiveData<CurrentLocationState>()
    var saveWeatherLocationdStateLiveData = MutableLiveData<SaveWeatherLocationState>()
    var locationListLiveData = MutableLiveData<LocationListState>()

    init {
    }

    /**
     * GET CURRENT LOCATION
     */
    fun getCurrentLocation() = launchSilent(coroutineContext, job) {
        val response = getCurrentLocationUseCase.execute()
        processCurrentLocationResponse(response)
    }

    fun processCurrentLocationResponse(response: Response<WeatherLocation>){
        if (response is Response.Success) {
            currentLocationStateLiveData.postValue(DefaultCurrentLocationState(response))
        } else if (response is Response.Error) {
            currentLocationStateLiveData.postValue(ErrorCurrentLocationState(response))
        }
    }

    /**
     * SAVE WEATHER LOCATION
     */
    fun saveWeatherLocation(weatherLocation: WeatherLocation) = launchSilent(coroutineContext, job) {
        val request = SaveWeatherLocationRequest(weatherLocation)
        val response = saveWeatherLocationUseCase.execute(request)
        processSaveWeatherLocationResponse(response)
    }

    fun processSaveWeatherLocationResponse(response: Response<WeatherLocation>){
        if (response is Response.Success) {
            saveWeatherLocationdStateLiveData.postValue(DefaultSaveWeatherLocationState(response))
        } else if (response is Response.Error) {
            saveWeatherLocationdStateLiveData.postValue(ErrorSaveWeatherLocationState(response))
        }
    }

    /**
     * GET WEATHER LOCATION LIST
     */
    fun getWeatherLocationList() = launchSilent(coroutineContext, job) {
        val response = getAllWeatherLocationListUseCase.execute()
        processGetWeatherLocationListResponse(response)
    }

    fun processGetWeatherLocationListResponse(response: Response<List<WeatherLocation>>){
        if (response is Response.Success) {
            locationListLiveData.postValue(SuccessLocationListState(response))
        } else if (response is Response.Error) {
            locationListLiveData.postValue(ErrorLocationListState(response))
        }
    }
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}