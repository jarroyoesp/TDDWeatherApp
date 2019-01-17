package es.jarroyo.tddweatherapp.ui.forecast.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.jarroyo.tddweatherapp.R
import es.jarroyo.tddweatherapp.app.di.component.ApplicationComponent
import es.jarroyo.tddweatherapp.app.di.subcomponent.forecast.fragment.ForecastFragmentModule
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.forecast.Forecast
import es.jarroyo.tddweatherapp.ui.base.BaseFragment
import es.jarroyo.tddweatherapp.ui.base.toast
import es.jarroyo.tddweatherapp.ui.forecast.fragment.adapter.ForecastListRVAdapter
import es.jarroyo.tddweatherapp.ui.viewmodel.ForecastViewModel
import es.jarroyo.tddweatherapp.ui.viewmodel.model.forecast.ErrorGetForecastState
import es.jarroyo.tddweatherapp.ui.viewmodel.model.forecast.GetForecastateState
import es.jarroyo.tddweatherapp.ui.viewmodel.model.forecast.LoadingGetForecastState
import es.jarroyo.tddweatherapp.ui.viewmodel.model.forecast.SuccessGetForecastState
import kotlinx.android.synthetic.main.fragment_forecast.*
import javax.inject.Inject

class ForecastFragment : BaseFragment() {
    override var layoutId = R.layout.fragment_forecast

    //DATA
    private lateinit var cityName: String

    // View model
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mForecastViewModel: ForecastViewModel

    // RV Adapter
    private var mLayoutManager: LinearLayoutManager? = null
    private var mRvAdapter: ForecastListRVAdapter? = null


    override fun setupInjection(applicationComponent: ApplicationComponent) {
        applicationComponent.plus(ForecastFragmentModule(this)).injectTo(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getExtras()
        return inflateView(inflater, container)
    }

    private fun getExtras() {
        cityName = arguments?.getString(ARG_EXTRA_CITY_NAME) ?: ""
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configRecyclerView()
        prepareViewModel()
        getData()
    }

    private fun prepareViewModel() {
        //Observer
        mForecastViewModel = ViewModelProviders.of(this, viewModelFactory).get(ForecastViewModel::class.java)
        observeLocationListViewModel()
    }

    private fun getData() {
        mForecastViewModel.getForecast(cityName)
    }

    /**
     * CONFIG VIEW
     */
    fun configRecyclerView() {
        mLayoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        fragment_forecast_rv.layoutManager = mLayoutManager

        mRvAdapter = ForecastListRVAdapter()

        fragment_forecast_rv.adapter = mRvAdapter
    }

    /****************************************************************************
     * OBSERVER
     ***************************************************************************/
    /** FORECAST OBSERVER **/
    private fun observeLocationListViewModel() {
        mForecastViewModel.mGetForecastateStateLiveData.observe(this, mGetForecastStateObserver)
    }

    private val mGetForecastStateObserver = Observer<GetForecastateState> { state ->
        state?.let {
            when (state) {
                is SuccessGetForecastState -> {
                    hideLoading()
                    hideError()
                    val success = it.response as Response.Success
                    showForecast(success.data)
                }
                is LoadingGetForecastState -> {
                    showLoading()
                    hideError()
                }
                is ErrorGetForecastState -> {
                    hideLoading()
                    showError((it as ErrorGetForecastState))
                }
            }
        }
    }

    private fun showLoading(){

    }

    private fun hideLoading(){

    }

    private fun showError(errorGetForecastState: ErrorGetForecastState) {
        val error = errorGetForecastState.response as Response.Error
        toast(error.exception.message)
    }

    private fun hideError(){

    }

    private fun showForecast(forecast: Forecast) {
        mRvAdapter?.setForecast(forecast)
        mRvAdapter?.notifyDataSetChanged()
    }

    companion object {
        val ARG_EXTRA_CITY_NAME = "ARG_EXTRA_CITY_NAME"

        fun newInstance(cityName: String): ForecastFragment {
            return ForecastFragment().apply {
                arguments = Bundle().apply {
                        putString(ARG_EXTRA_CITY_NAME, cityName)
                }
            }
        }
    }


}
