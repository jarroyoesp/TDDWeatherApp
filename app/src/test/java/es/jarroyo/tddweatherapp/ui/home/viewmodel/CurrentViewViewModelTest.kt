package es.jarroyo.tddweatherapp.ui.home.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import com.nhaarman.mockitokotlin2.whenever
import es.jarroyo.tddweatherapp.data.exception.NetworkConnectionException
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeather
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeatherFactory
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherRequest
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherUseCase
import es.jarroyo.tddweatherapp.ui.home.model.DefaultForecastState
import es.jarroyo.tddweatherapp.ui.home.model.ErrorForecastState
import es.jarroyo.tddweatherapp.ui.home.model.ForecastState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import kotlin.coroutines.CoroutineContext

class CurrentViewViewModelTest {

    @get: Rule
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit  var viewModel : CurrentWeatherViewModel

    var coroutineContext: CoroutineContext = Dispatchers.Unconfined

    @Mock
    lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

    @Mock
    lateinit var observer: Observer<ForecastState>

    @Mock
    lateinit var lifeCycleOwner: LifecycleOwner

    lateinit var lifeCycle: LifecycleRegistry

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        prepareViewModel()

        lifeCycle = LifecycleRegistry(lifeCycleOwner)
        `when` (lifeCycleOwner.lifecycle).thenReturn(lifeCycle)
        lifeCycle.handleLifecycleEvent(Lifecycle.Event.ON_START)

    }

    /**
     * Verify that "getCurrentWeatherUseCase" is executed once when the viewModel is initilized
     */
    @Test
    fun `should request the current weather on start`() {
        runBlocking {
            val response = Response(CurrentWeatherFactory.createCurrentWeatherTest())
            val request = GetCurrentWeatherRequest(1234)
            whenever(getCurrentWeatherUseCase.execute(request)).thenReturn(response)

            viewModel.initialize()
            Mockito.verify(getCurrentWeatherUseCase, Mockito.times(1)).execute()
        }
    }

    /**
     * Verify when is success getting currentWeather the liveData is changed with this new values
     */
    @Test
    fun `should show current weather when current weather info is received`() {
        runBlocking {
            val response = Response(CurrentWeatherFactory.createCurrentWeatherTest())
            val request = GetCurrentWeatherRequest(1234)
            whenever(getCurrentWeatherUseCase.execute(request)).thenReturn(response)

            viewModel.stateLiveData.observe(lifeCycleOwner, observer)

            viewModel.initialize()

            verify(observer).onChanged(DefaultForecastState(response))
        }

    }

    /**
     * Test that checks ErrorState is set when there is no Internet connection
     */
    @Test
    fun `should show error when no Internet connection is available`() {
        runBlocking {
            val response = Response<CurrentWeather>(exception = NetworkConnectionException())
            val request = GetCurrentWeatherRequest(1234)
            whenever(getCurrentWeatherUseCase.execute(request)).thenReturn(response)

            viewModel.stateLiveData.observe(lifeCycleOwner, observer)

            viewModel.initialize()

            verify(observer).onChanged(ErrorForecastState(response))
        }
    }

    private fun prepareViewModel(){
        viewModel = CurrentWeatherViewModel(getCurrentWeatherUseCase, coroutineContext)
    }
}