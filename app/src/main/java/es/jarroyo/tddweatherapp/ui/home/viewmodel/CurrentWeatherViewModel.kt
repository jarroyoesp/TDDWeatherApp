package es.jarroyo.tddweatherapp.ui.home.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherUseCase
import es.jarroyo.tddweatherapp.ui.home.model.DefaultForecastState
import es.jarroyo.tddweatherapp.ui.home.model.ForecastState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CurrentWeatherViewModel
    @Inject
    constructor(private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
                private val stateLiveData: MutableLiveData<ForecastState>)
    : ViewModel() {

    private var job: Job = Job()
    private var coroutineContext: CoroutineContext = Dispatchers.Main

    init {
        stateLiveData.value = DefaultForecastState(Response(null))
    }

    fun initialize() {

    }


    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}