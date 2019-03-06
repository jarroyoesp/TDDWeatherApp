package es.jarroyo.tddweatherapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.forecast.Forecast
import es.jarroyo.tddweatherapp.domain.usecase.forecast.getForecast.GetForecastRequest
import es.jarroyo.tddweatherapp.domain.usecase.forecast.getForecast.GetForecastUseCase
import es.jarroyo.tddweatherapp.ui.viewmodel.model.forecast.ErrorGetForecastState
import es.jarroyo.tddweatherapp.ui.viewmodel.model.forecast.GetForecastateState
import es.jarroyo.tddweatherapp.ui.viewmodel.model.forecast.LoadingGetForecastState
import es.jarroyo.tddweatherapp.ui.viewmodel.model.forecast.SuccessGetForecastState
import es.jarroyo.tddweatherapp.utils.launchSilent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ForecastViewModel
    @Inject
    constructor(private val getForecastUseCase: GetForecastUseCase,
                private val coroutineContext: CoroutineContext)
    : ViewModel() {

    private var job: Job = Job()

    var mGetForecastateStateLiveData = MutableLiveData<GetForecastateState>()

    init {
    }

    fun getForecast(cityName: String) = launchSilent(coroutineContext, job) {
        mGetForecastateStateLiveData.postValue(LoadingGetForecastState())

        val request = GetForecastRequest(cityName)
        val response = getForecastUseCase.execute(request)
        proccessForecast(response)
    }

    private fun proccessForecast(response: Response<Forecast>){
        if (response is Response.Success) {
            mGetForecastateStateLiveData.postValue(
                SuccessGetForecastState(
                    response
                )
            )
        } else if (response is Response.Error) {
            mGetForecastateStateLiveData.postValue(
                ErrorGetForecastState(
                    response
                )
            )
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}