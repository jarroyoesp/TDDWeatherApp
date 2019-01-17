package es.jarroyo.tddweatherapp.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeather
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherByNameRequest
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherByNameUseCase
import es.jarroyo.tddweatherapp.ui.viewmodel.model.forecast.GetForecastateState
import es.jarroyo.tddweatherapp.ui.viewmodel.model.forecast.LoadingGetForecastState
import es.jarroyo.tddweatherapp.utils.launchSilent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ForecastViewModel
    @Inject
    constructor(private val getCurrentWeatherByNameUseCase: GetCurrentWeatherByNameUseCase,
                private val coroutineContext: CoroutineContext)
    : ViewModel() {

    private var job: Job = Job()

    var mGetForecastateStateLiveData = MutableLiveData<GetForecastateState>()

    init {
        //currentWeatherStateLiveData.postValue(DefaultCurrentWeatherState(Response.Success(null)))
    }

    fun getForecast(cityName: String) = launchSilent(coroutineContext, job) {
        mGetForecastateStateLiveData.postValue(LoadingGetForecastState())

        val request = GetCurrentWeatherByNameRequest(cityName)
        val response = getCurrentWeatherByNameUseCase.execute(request)
        proccessCurrentWeather(response)
    }

    private fun proccessCurrentWeather(response: Response<CurrentWeather>){
        /*if (response is Response.Success) {
            currentWeatherStateLiveData.postValue(
                DefaultCurrentWeatherState(
                    response
                )
            )
        } else if (response is Response.Success) {
            currentWeatherStateLiveData.postValue(
                ErrorCurrentWeatherState(
                    response
                )
            )
        }*/
    }

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}