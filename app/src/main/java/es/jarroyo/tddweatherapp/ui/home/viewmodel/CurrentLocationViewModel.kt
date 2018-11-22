package es.jarroyo.tddweatherapp.ui.home.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.location.CurrentLocation
import es.jarroyo.tddweatherapp.domain.usecase.currentLocation.GetCurrentLocationUseCase
import es.jarroyo.tddweatherapp.ui.home.model.CurrentLocationState
import es.jarroyo.tddweatherapp.ui.home.model.DefaultCurrentLocationState
import es.jarroyo.tddweatherapp.ui.home.model.ErrorCurrentLocationState
import es.jarroyo.tddweatherapp.utils.launchSilent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CurrentLocationViewModel
    @Inject
    constructor(private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
                private val coroutineContext: CoroutineContext)
    : ViewModel() {

    private var job: Job = Job()

    var currentLocationStateLiveData = MutableLiveData<CurrentLocationState>()

    init {
    }

    fun getCurrentLocation() = launchSilent(coroutineContext, job) {
        val response = getCurrentLocationUseCase.execute()
        processCurrentLocationResponse(response)
    }

    fun processCurrentLocationResponse(response: Response<CurrentLocation>){
        if (response.data != null) {
            currentLocationStateLiveData.postValue(DefaultCurrentLocationState(response))
        } else if (response.exception != null) {
            currentLocationStateLiveData.postValue(ErrorCurrentLocationState(response))
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}