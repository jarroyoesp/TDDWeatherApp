package es.jarroyo.tddweatherapp.ui.forecast.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidplot.xy.*
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
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
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
        fragment_forecast_loading.visibility = View.VISIBLE
    }

    private fun hideLoading(){
        fragment_forecast_loading.visibility = View.GONE
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

        showTemperaturePlot(forecast)
    }

    private fun showTemperaturePlot(forecast: Forecast) {
        val labelsXList = mutableListOf<String>()
        val datesList = mutableListOf<Int>()
        val temperaturesList = mutableListOf<Long>()

        for (prediction in forecast.list) {
            datesList.add(prediction.dt)
            temperaturesList.add(Math.round(prediction.main.temp))
            labelsXList.add(DateUtils.formatDateTime(context, prediction.dt * 1000L, DateUtils.FORMAT_SHOW_TIME))
        }
        val seriesY = SimpleXYSeries(temperaturesList, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "")

        val series1Format = LineAndPointFormatter(context, R.xml.line_point_formatter_with_labels)
        series1Format.interpolationParams = CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal)

        fragment_forecast_plot.addSeries(seriesY, series1Format)

        fragment_forecast_plot.linesPerDomainLabel = 3
        fragment_forecast_plot.graph.domainGridLinePaint.color = Color.WHITE
        //fragment_forecast_plot.graph.domainSubGridLinePaint.color = Color.WHITE

        fragment_forecast_plot.legend.isVisible = false
        fragment_forecast_plot.setRangeStep(StepMode.INCREMENT_BY_VAL, 1.0)
        fragment_forecast_plot.setRangeStep(StepMode.SUBDIVIDE, 5.0)

        fragment_forecast_plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format = object : Format() {
            override fun format(obj: Any, toAppendTo: StringBuffer, pos: FieldPosition): StringBuffer {
                val i = Math.round((obj as Number).toFloat())
                return toAppendTo.append(labelsXList[i])
            }

            override fun parseObject(source: String, pos: ParsePosition): Any? {
                return null
            }
        }
        fragment_forecast_plot.invalidate()
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
