package es.jarroyo.tddweatherapp.ui.home.viewmodel

import android.arch.lifecycle.MutableLiveData
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherUseCase
import es.jarroyo.tddweatherapp.ui.home.model.ForecastState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import kotlin.coroutines.CoroutineContext

class CurrentViewViewModelTest {

    lateinit  var viewModel : CurrentWeatherViewModel

    var coroutineContext: CoroutineContext = Dispatchers.Unconfined

    @Mock
    lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

    @Mock
    lateinit var stateLiveData : MutableLiveData<ForecastState>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        prepareViewModel()
    }

    @Test
    fun `should request the current weather on start`() {
        runBlocking {
            viewModel.initialize()
            Mockito.verify(getCurrentWeatherUseCase, Mockito.times(1)).execute()
        }
    }

    private fun prepareViewModel(){
        viewModel = CurrentWeatherViewModel(getCurrentWeatherUseCase, stateLiveData, coroutineContext)
    }
}