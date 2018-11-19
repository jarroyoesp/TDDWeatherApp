package es.jarroyo.tddweatherapp.ui.home.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherRequest
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherUseCase
import es.jarroyo.tddweatherapp.ui.home.model.DefaultForecastState
import es.jarroyo.tddweatherapp.ui.home.model.ForecastState
import es.jarroyo.tddweatherapp.utils.launchSilent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CurrentWeatherViewModel
    @Inject
    constructor(private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
                private var stateLiveData: MutableLiveData<ForecastState>,
                private val coroutineContext: CoroutineContext)
    : ViewModel() {

    private var job: Job = Job()

    init {
        stateLiveData.value = DefaultForecastState(Response(null))
    }

    fun initialize() = launchSilent(coroutineContext, job) {
        val request = GetCurrentWeatherRequest(1234) // Todo buscar un cityID
        getCurrentWeatherUseCase.execute(request)
    }


    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}