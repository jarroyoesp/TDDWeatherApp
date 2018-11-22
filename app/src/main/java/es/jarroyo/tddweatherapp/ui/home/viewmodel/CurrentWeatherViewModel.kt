package es.jarroyo.tddweatherapp.ui.home.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeather
import es.jarroyo.tddweatherapp.domain.usecase.currentLocation.GetCurrentLocationUseCase
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherByNameRequest
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherByNameUseCase
import es.jarroyo.tddweatherapp.ui.home.model.CurrentWeatherState
import es.jarroyo.tddweatherapp.ui.home.model.DefaultCurrentWeatherState
import es.jarroyo.tddweatherapp.ui.home.model.ErrorCurrentWeatherState
import es.jarroyo.tddweatherapp.ui.home.model.LoadingCurrentWeatherState
import es.jarroyo.tddweatherapp.utils.launchSilent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CurrentWeatherViewModel
    @Inject
    constructor(private val getCurrentWeatherByNameUseCase: GetCurrentWeatherByNameUseCase,
                private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
                private val coroutineContext: CoroutineContext)
    : ViewModel() {

    private var job: Job = Job()

    var currentWeatherStateLiveData = MutableLiveData<CurrentWeatherState>()

    init {
        currentWeatherStateLiveData.postValue(DefaultCurrentWeatherState(Response(null)))
    }

    fun initialize() = launchSilent(coroutineContext, job) {
        getCurrentLocation()
    }

    fun getCurrentLocation() = launchSilent(coroutineContext, job) {
        getCurrentLocationUseCase.execute()
    }

    fun getCityCurrentWeather(cityName: String) = launchSilent(coroutineContext, job) {
        currentWeatherStateLiveData.postValue(LoadingCurrentWeatherState(Response(null)))

        val request = GetCurrentWeatherByNameRequest(cityName)
        val response = getCurrentWeatherByNameUseCase.execute(request)
        proccessCurrentWeather(response)
    }

    private fun proccessCurrentWeather(response: Response<CurrentWeather>){
        if (response?.exception == null && response?.data != null) {
            currentWeatherStateLiveData.postValue(DefaultCurrentWeatherState(response))
        } else if (response?.exception != null) {
            currentWeatherStateLiveData.postValue(ErrorCurrentWeatherState(response))
        }
    }


    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}