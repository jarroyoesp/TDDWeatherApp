package es.jarroyo.tddweatherapp.ui.forecast

import android.os.Bundle
import es.jarroyo.tddweatherapp.app.di.component.ApplicationComponent
import es.jarroyo.tddweatherapp.app.di.subcomponent.forecast.activity.ForecastActivityModule
import es.jarroyo.tddweatherapp.ui.base.BaseBackActivity
import es.jarroyo.tddweatherapp.ui.forecast.fragment.ForecastFragment

class ForecastActivity : BaseBackActivity() {

    //Data
    private var cityName: String = ""

    override fun setupInjection(applicationComponent: ApplicationComponent) {
        applicationComponent.plus(ForecastActivityModule(this)).injectTo(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getExtras()
        initView()
    }

    private fun getExtras() {
        cityName = intent.extras.getString(ARG_EXTRA_CITY_NAME)
    }

    private fun initView() {
        setTitleToolbar("$cityName forecast")
        addFragmentToMainLayout(ForecastFragment.newInstance(cityName))
    }

    companion object {
        val ARG_EXTRA_CITY_NAME = "ARG_EXTRA_CITY_NAME"
    }
}
