package es.jarroyo.tddweatherapp.ui.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import com.nhaarman.mockitokotlin2.whenever
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocationFactory
import es.jarroyo.tddweatherapp.domain.usecase.location.currentLocation.GetCurrentLocationUseCase
import es.jarroyo.tddweatherapp.domain.usecase.location.deleteWeatherLocation.DeleteWeatherLocationUseCase
import es.jarroyo.tddweatherapp.domain.usecase.location.getAllWeatherLocationList.GetAllWeatherLocationListUseCase
import es.jarroyo.tddweatherapp.domain.usecase.location.saveWeatherLocation.SaveWeatherLocationUseCase
import es.jarroyo.tddweatherapp.ui.viewmodel.model.location.*
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



class WeatherLocationViewModelTest {
    @get: Rule
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit  var viewModel : LocationViewModel

    var coroutineContext: CoroutineContext = Dispatchers.Unconfined

    @Mock
    lateinit var getCurrentLocationUseCase: GetCurrentLocationUseCase

    @Mock
    lateinit var saveWeatherLocationUseCase: SaveWeatherLocationUseCase

    @Mock
    lateinit var getAllWeatherLocationListUseCase: GetAllWeatherLocationListUseCase

    @Mock
    lateinit var deleteWeatherLocationUseCase: DeleteWeatherLocationUseCase

    @Mock
    lateinit var observer: Observer<CurrentLocationState>

    @Mock
    lateinit var observerSaveWeatherLocation: Observer<SaveWeatherLocationState>

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
            val response = Response.Success(WeatherLocationFactory.createCurrentLocationTest())
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
            val response = Response.Success(WeatherLocationFactory.createCurrentLocationTest())
            whenever(getCurrentLocationUseCase.execute()).thenReturn(response)

            viewModel.currentLocationStateLiveData.observe(lifeCycleOwner, observer)

            viewModel.getCurrentLocation()

            Mockito.verify(observer).onChanged(
                DefaultCurrentLocationState(
                    response
                )
            )
        }
    }

    /**
     * Verify when error getting current location the state is ErrorState
     */
    @Test
    fun `when received error getting current location then CurrrentLocationState is ErrorState`() {
        runBlocking {
            val response = Response.Error(exception = IllegalArgumentException())
            whenever(getCurrentLocationUseCase.execute()).thenReturn(response)

            viewModel.currentLocationStateLiveData.observe(lifeCycleOwner, observer)

            viewModel.getCurrentLocation()

            Mockito.verify(observer).onChanged(
                ErrorCurrentLocationState(
                    response
                )
            )
        }
    }

    @Test
    fun `when call saveWeatherLocation(), SaveWeatherLocationUsecase is called once`() {
        runBlocking {
            val response = Response.Success(WeatherLocationFactory.createLocationList())
            whenever(saveWeatherLocationUseCase.execute()).thenReturn(response)

            viewModel.saveWeatherLocation(WeatherLocationFactory.createCurrentLocationTest())
            Mockito.verify(saveWeatherLocationUseCase, Mockito.times(1)).execute()
        }
    }

    @Test
    fun `when received success after call saveWeatherLocation(), we receive the same weather location`() {
        runBlocking {
            val response = Response.Success(WeatherLocationFactory.createLocationList())
            whenever(saveWeatherLocationUseCase.execute()).thenReturn(response)

            viewModel.saveWeatherLocationdStateLiveData.observe(lifeCycleOwner, observerSaveWeatherLocation)

            viewModel.saveWeatherLocation(WeatherLocationFactory.createCurrentLocationTest())

            Mockito.verify(observerSaveWeatherLocation).onChanged(
                SuccessSaveWeatherLocationState(
                    response
                )
            )
        }
    }

    @Test
    fun `when received error after call saveWeatherLocation(), we set ErrorSaveWeatherLocationSatate`() {
        runBlocking {
            val response = Response.Error(exception = java.lang.IllegalArgumentException())
            whenever(saveWeatherLocationUseCase.execute()).thenReturn(response)

            viewModel.saveWeatherLocationdStateLiveData.observe(lifeCycleOwner, observerSaveWeatherLocation)

            viewModel.saveWeatherLocation(WeatherLocationFactory.createCurrentLocationTest())

            Mockito.verify(observerSaveWeatherLocation).onChanged(
                ErrorSaveWeatherLocationState(
                    response
                )
            )
        }
    }



    private fun prepareViewModel(){
        viewModel = LocationViewModel(getCurrentLocationUseCase,saveWeatherLocationUseCase, getAllWeatherLocationListUseCase, deleteWeatherLocationUseCase, coroutineContext)
    }


}