package es.jarroyo.tddweatherapp.ui.home.viewmodel

import android.arch.lifecycle.MutableLiveData
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherUseCase
import es.jarroyo.tddweatherapp.ui.home.model.ForecastState
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CurrentViewViewModelTest {

    lateinit  var viewModel : CurrentWeatherViewModel

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
            Mockito.verify(getCurrentWeatherUseCase, Mockito.times(1)).execute(ArgumentMatchers.any())
        }
    }

    private fun prepareViewModel(){
        viewModel = CurrentWeatherViewModel(getCurrentWeatherUseCase, stateLiveData)
    }
}