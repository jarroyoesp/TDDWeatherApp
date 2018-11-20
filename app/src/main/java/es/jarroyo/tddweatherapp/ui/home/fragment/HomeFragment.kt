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
import android.widget.Toast
import es.jarroyo.tddweatherapp.R
import es.jarroyo.tddweatherapp.app.di.component.ApplicationComponent
import es.jarroyo.tddweatherapp.app.di.subcomponent.home.fragment.HomeFragmentModule
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeather
import es.jarroyo.tddweatherapp.ui.base.BaseFragment
import es.jarroyo.tddweatherapp.ui.home.model.DefaultForecastState
import es.jarroyo.tddweatherapp.ui.home.model.ErrorForecastState
import es.jarroyo.tddweatherapp.ui.home.model.ForecastState
import es.jarroyo.tddweatherapp.ui.home.model.LoadingForecastState
import es.jarroyo.tddweatherapp.ui.home.viewmodel.CurrentWeatherViewModel
import javax.inject.Inject

class HomeFragment : BaseFragment() {
    override var layoutId = R.layout.fragment_home

    private var listener: OnFragmentInteractionListener? = null

    // View model
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: CurrentWeatherViewModel

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
        observeViewModel()
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }
    /****************************************************************************
     * OBSERVER
     ***************************************************************************/
    private fun observeViewModel() {
        viewModel.stateLiveData.observe(this, stateObserver)
        viewModel.initialize()
    }

    private val stateObserver = Observer<ForecastState> { state ->
        state?.let {
            when (state) {
                is DefaultForecastState -> {
                    isLoading = false
                    //hideLoading()
                    showCurrentWeather(it.response.data)
                }
                is LoadingForecastState -> {
                    isLoading = true
                    /*showLoading()*/
                }
                is ErrorForecastState -> {
                    isLoading = false
                    /*hideLoading()*/
                    showError((it as ErrorForecastState))
                }
            }
        }
    }

    /**
     * SHOW CURRENT WEATHER
     */
    private fun showCurrentWeather(currentWeather: CurrentWeather?){
        if (currentWeather != null) {
            Toast.makeText(context, currentWeather.name, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * SHOW ERROR
     */
    private fun showError(errorForecastState: ErrorForecastState){
        Toast.makeText(context, errorForecastState.response.exception?.message, Toast.LENGTH_SHORT).show()
    }

}
