package es.jarroyo.tddweatherapp.ui.home.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeather
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherRequest
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherUseCase
import es.jarroyo.tddweatherapp.ui.home.model.DefaultForecastState
import es.jarroyo.tddweatherapp.ui.home.model.ErrorForecastState
import es.jarroyo.tddweatherapp.ui.home.model.ForecastState
import es.jarroyo.tddweatherapp.utils.launchSilent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CurrentWeatherViewModel
    @Inject
    constructor(private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
                private val coroutineContext: CoroutineContext)
    : ViewModel() {

    private var job: Job = Job()

    var stateLiveData = MutableLiveData<ForecastState>()

    init {
        stateLiveData.postValue(DefaultForecastState(Response(null)))
    }

    fun initialize() = launchSilent(coroutineContext, job) {
        val request = GetCurrentWeatherRequest(2172798) // Todo buscar un cityID 2172797
        val response = getCurrentWeatherUseCase.execute(request)
        proccessCurrentWeather(response)
    }

    private fun proccessCurrentWeather(response: Response<CurrentWeather>){
        if (response.error == null && response.data != null) {
            stateLiveData.postValue(DefaultForecastState(response))
        } else if (response.exception != null) {
            stateLiveData.postValue(ErrorForecastState(response))
        }
    }


    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}