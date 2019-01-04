package es.jarroyo.tddweatherapp.ui.home.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.OnClick
import es.jarroyo.tddweatherapp.R
import es.jarroyo.tddweatherapp.app.di.component.ApplicationComponent
import es.jarroyo.tddweatherapp.app.di.subcomponent.home.fragment.HomeFragmentModule
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeather
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocation
import es.jarroyo.tddweatherapp.ui.base.BaseFragment
import es.jarroyo.tddweatherapp.ui.home.fragment.adapter.HomeListRVAdapter
import es.jarroyo.tddweatherapp.ui.viewmodel.LocationViewModel
import es.jarroyo.tddweatherapp.ui.viewmodel.WeatherViewModel
import es.jarroyo.tddweatherapp.ui.viewmodel.model.*
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment() {
    override var layoutId = R.layout.fragment_home

    private var listener: OnFragmentInteractionListener? = null

    // View model
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var locationViewModel: LocationViewModel

    private var isLoading = false

    // RV Adapter
    private var mLayoutManager: LinearLayoutManager? = null
    private var mRvAdapter: HomeListRVAdapter? = null

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

        configRecyclerView()

        //Observer
        weatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel::class.java)
        locationViewModel = ViewModelProviders.of(this, viewModelFactory).get(LocationViewModel::class.java)

        observeLocationListViewModel()
        observeWeatherListViewModel()

        getLocationList()
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    /**
     * CONFIG RV VIEW
     */
    fun configRecyclerView() {
        mLayoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        fragment_home_rv.layoutManager = mLayoutManager

        mRvAdapter = HomeListRVAdapter(
            listenerAddLocationClicked = {

            },
            listenerWeatherClicked = {

            }
        )

        fragment_home_rv.adapter = mRvAdapter
        fragment_home_swipe_refresh_rv.setOnRefreshListener {
            getLocationList()
        }
    }

    /****************************************************************************
     * ONCLICK
     ***************************************************************************/
    @OnClick(R.id.fragment_home_button_retry)
    fun onClickRetry() {
    }

    /****************************************************************************
     * OBSERVER
     ***************************************************************************/
    /** LOCATION LIST OBSERVER **/
    private fun observeLocationListViewModel() {
        locationViewModel.locationListLiveData.observe(this, locationListStateObserver)
    }

    private fun getLocationList() {
        locationViewModel.getWeatherLocationList()
    }

    private val locationListStateObserver = Observer<LocationListState> { state ->
        state?.let {
            when (state) {
                is SuccessLocationListState -> {
                    isLoading = false
                    hideLoading()
                    hideError()
                    val success = it.response as Response.Success
                    getWeatherForLocationList(success.data)
                }
                is LoadingLocationListState -> {
                    isLoading = true
                    showLoading()
                    hideError()
                }
                is ErrorLocationListState -> {
                    isLoading = false
                    hideLoading()
                    //showError((it as ErrorCurrentWeatherState))
                }
            }
        }
    }


    private fun observeWeatherListViewModel() {
        weatherViewModel.weatherListStateLiveData.observe(this, weatherListStateObserver)
    }

    private val weatherListStateObserver = Observer<List<CurrentWeatherState>> { weatherStateList ->
        weatherStateList?.let {
            val weatherList = mutableListOf<CurrentWeather>()
            for (weatherState in weatherStateList) {
                when (weatherState) {
                    is DefaultCurrentWeatherState -> {
                        val success = weatherState.response as Response.Success
                        weatherList.add(success.data)
                    }
                }
            }

            showInRVWeatherList(weatherList)
        }
    }


    /**
     * GET WEATHER LOCATION LIST
     */
    private fun getWeatherForLocationList(weatherLocationList: List<WeatherLocation>?){
        if (weatherLocationList != null) {
            weatherViewModel.getWeatherList(weatherLocationList)
        }
    }


    /**
     * SHOW CURRENT WEATHER
     */
    private fun showInRVWeatherList(weatherList: List<CurrentWeather>?){
        mRvAdapter?.setWeatherList(weatherList)
        mRvAdapter?.notifyDataSetChanged()
    }

    /**
     * SHOW LOADING
     */
    private fun showLoading(){
        fragment_home_loading.visibility = View.VISIBLE
        fragment_home_swipe_refresh_rv.isRefreshing = true
    }

    /**
     * HIDE LOADING
     */
    private fun hideLoading(){
        fragment_home_loading.visibility = View.GONE
        fragment_home_swipe_refresh_rv.isRefreshing = false
    }

    /**
     * SHOW ERROR
     */
    private fun showError(errorForecastState: ErrorCurrentWeatherState){
        fragment_home_layout_error.visibility = View.VISIBLE
        val error = errorForecastState.response as Response.Error
        fragment_home_tv_status.text = error.exception.message
    }

    /**
     * HIDE ERROR
     */
    private fun hideError(){
        fragment_home_layout_error.visibility = View.GONE
    }

}
