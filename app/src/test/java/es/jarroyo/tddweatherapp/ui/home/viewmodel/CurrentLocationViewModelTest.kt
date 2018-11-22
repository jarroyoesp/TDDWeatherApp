package es.jarroyo.tddweatherapp.ui.home.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import com.nhaarman.mockitokotlin2.whenever
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.location.CurrentLocation
import es.jarroyo.tddweatherapp.domain.model.location.CurrentLocationFactory
import es.jarroyo.tddweatherapp.domain.usecase.currentLocation.GetCurrentLocationUseCase
import es.jarroyo.tddweatherapp.ui.home.model.CurrentLocationState
import es.jarroyo.tddweatherapp.ui.home.model.DefaultCurrentLocationState
import es.jarroyo.tddweatherapp.ui.home.model.ErrorCurrentLocationState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.coroutines.CoroutineContext



class CurrentLocationViewModelTest {
    @get: Rule
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit  var viewModel : CurrentLocationViewModel

    var coroutineContext: CoroutineContext = Dispatchers.Unconfined

    @Mock
    lateinit var getCurrentLocationUseCase: GetCurrentLocationUseCase

    @Mock
    lateinit var observer: Observer<CurrentLocationState>

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
     * Verify when Initilize viewModel, the first action is getCurrentLocation
     */
    @Test
    fun `when viewModel is initilize then get currentLocation`() {
        runBlocking {
            val response = Response(CurrentLocationFactory.createCurrentLocationTest())
            whenever(getCurrentLocationUseCase.execute()).thenReturn(response)

            viewModel.getCurrentLocation()
            Mockito.verify(getCurrentLocationUseCase, Mockito.times(1)).execute()
        }
    }

    /**
     * Verify when success getting current location the state is DefaultState
     */
    @Test
    fun `when received current location then CurrrentLocationState is DefaultState`() {
        runBlocking {
            val response = Response(CurrentLocationFactory.createCurrentLocationTest())
            whenever(getCurrentLocationUseCase.execute()).thenReturn(response)

            viewModel.currentLocationStateLiveData.observe(lifeCycleOwner, observer)

            viewModel.getCurrentLocation()

            Mockito.verify(observer).onChanged(DefaultCurrentLocationState(response))
        }
    }

    /**
     * Verify when error getting current location the state is ErrorState
     */
    @Test
    fun `when received error getting current location then CurrrentLocationState is ErrorState`() {
        runBlocking {
            val response = Response<CurrentLocation>(exception = IllegalArgumentException())
            whenever(getCurrentLocationUseCase.execute()).thenReturn(response)

            viewModel.currentLocationStateLiveData.observe(lifeCycleOwner, observer)

            viewModel.getCurrentLocation()

            Mockito.verify(observer).onChanged(ErrorCurrentLocationState(response))
        }
    }

    private fun prepareViewModel(){
        viewModel = CurrentLocationViewModel(getCurrentLocationUseCase, coroutineContext)
    }
}