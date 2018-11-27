package es.jarroyo.tddweatherapp.ui.home.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.OnClick
import es.jarroyo.tddweatherapp.R
import es.jarroyo.tddweatherapp.app.di.component.ApplicationComponent
import es.jarroyo.tddweatherapp.app.di.subcomponent.home.fragment.HomeFragmentModule
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeather
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocation
import es.jarroyo.tddweatherapp.ui.base.BaseFragment
import es.jarroyo.tddweatherapp.ui.home.model.*
import es.jarroyo.tddweatherapp.ui.home.viewmodel.CurrentLocationViewModel
import es.jarroyo.tddweatherapp.ui.home.viewmodel.CurrentWeatherViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment() {
    override var layoutId = R.layout.fragment_home

    private var listener: OnFragmentInteractionListener? = null

    // View model
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var currentLocationviewModel: CurrentLocationViewModel

    private var isLoading = false

    override fun setupInjection(applicationComponent: ApplicationComponent) {
        applicationComponent.plus(HomeFragmentModule(this)).injectTo(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflateView(inflater, container)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Observer
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)
        currentLocationviewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrentLocationViewModel::class.java)

        observeCurrentWeatherViewModel()
        observeCurrentLocationViewModel()
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    /****************************************************************************
     * ONCLICK
     ***************************************************************************/
    @OnClick(R.id.fragment_home_button_search)
    fun onClickSearch() {
        viewModel.getCityCurrentWeather(fragment_home_et_city.text.toString())
    }

    @OnClick(R.id.fragment_home_button_retry)
    fun onClickRetry() {
        viewModel.getCityCurrentWeather(fragment_home_et_city.text.toString())
    }

    /****************************************************************************
     * OBSERVER
     ***************************************************************************/

    /** CURRENT WEATHER OBSERVER **/
    private fun observeCurrentWeatherViewModel() {
        viewModel.currentWeatherStateLiveData.observe(this, currentWeatherstateObserver)
        viewModel.initialize()
    }

    private val currentWeatherstateObserver = Observer<CurrentWeatherState> { state ->
        state?.let {
            when (state) {
                is DefaultCurrentWeatherState -> {
                    isLoading = false
                    hideLoading()
                    hideError()
                    showCurrentWeather(it.response.data)
                }
                is LoadingCurrentWeatherState -> {
                    isLoading = true
                    showLoading()
                    hideError()
                }
                is ErrorCurrentWeatherState -> {
                    isLoading = false
                    hideLoading()
                    showError((it as ErrorCurrentWeatherState))
                }
            }
        }
    }

    /** CURRENT LOCATION OBSERVER **/
    private fun observeCurrentLocationViewModel() {
        currentLocationviewModel.currentLocationStateLiveData.observe(this, currentLocationstateObserver)
        currentLocationviewModel.getCurrentLocation()
    }

    private val currentLocationstateObserver = Observer<CurrentLocationState> { state ->
        state?.let {
            when (state) {
                is DefaultCurrentLocationState -> {
                    isLoading = false
                    hideLoading()
                    hideError()
                    showCurrentLocation(it.response.data)
                }
                is LoadingCurrentLocationState -> {
                    isLoading = true
                    showLoading()
                    hideError()
                }
                is ErrorCurrentLocationState -> {
                    isLoading = false
                    hideLoading()
                    //showError((it as ErrorCurrentWeatherState))
                }
            }
        }
    }

    /**
     * SHOW CURRENT LOCATION
     */
    private fun showCurrentLocation(weatherLocation: WeatherLocation?){
        if (weatherLocation != null) {
            val info = "Location: ${weatherLocation.cityName}"
            fragment_home_tv_current_location.text = info
            fragment_home_et_city.setText(weatherLocation.cityName)
        }
    }


    /**
     * SHOW CURRENT WEATHER
     */
    private fun showCurrentWeather(currentWeather: CurrentWeather?){
        if (currentWeather != null) {
            val info = "Temp: ${currentWeather.main.temp}\nTemp Max: ${currentWeather.main.temp_max}\nTemp Min: ${currentWeather.main.temp_min}\n"
            fragment_home_tv_current_weather.text = info
        }
    }

    /**
     * SHOW LOADING
     */
    private fun showLoading(){
        fragment_home_loading.visibility = View.VISIBLE
    }

    /**
     * HIDE LOADING
     */
    private fun hideLoading(){
        fragment_home_loading.visibility = View.GONE
    }

    /**
     * SHOW ERROR
     */
    private fun showError(errorForecastState: ErrorCurrentWeatherState){
        fragment_home_layout_error.visibility = View.VISIBLE
        fragment_home_tv_status.text = errorForecastState.response.exception?.message
    }

    /**
     * HIDE ERROR
     */
    private fun hideError(){
        fragment_home_layout_error.visibility = View.GONE
    }

}
