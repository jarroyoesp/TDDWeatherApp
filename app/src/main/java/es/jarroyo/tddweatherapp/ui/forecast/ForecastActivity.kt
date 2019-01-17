package es.jarroyo.tddweatherapp.ui.forecast

import es.jarroyo.tddweatherapp.app.di.component.ApplicationComponent
import es.jarroyo.tddweatherapp.app.di.subcomponent.forecast.activity.ForecastActivityModule
import es.jarroyo.tddweatherapp.ui.base.BaseBackActivity

class ForecastActivity : BaseBackActivity() {

    override fun setupInjection(applicationComponent: ApplicationComponent) {
        applicationComponent.plus(ForecastActivityModule(this)).injectTo(this)
    }

    companion object {
        val ARG_EXTRA_CITY_NAME = "ARG_EXTRA_CITY_NAME"
    }
}
