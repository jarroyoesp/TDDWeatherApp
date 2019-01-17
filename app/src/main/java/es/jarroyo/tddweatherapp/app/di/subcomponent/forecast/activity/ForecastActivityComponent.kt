package es.jarroyo.tddweatherapp.app.di.subcomponent.forecast.activity

import dagger.Subcomponent
import es.jarroyo.tddweatherapp.ui.forecast.ForecastActivity

@Subcomponent(modules = arrayOf(ForecastActivityModule::class))
interface ForecastActivityComponent {
    fun injectTo(activity: ForecastActivity)
}