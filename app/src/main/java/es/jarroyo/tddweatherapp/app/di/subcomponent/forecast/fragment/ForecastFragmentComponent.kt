package es.jarroyo.tddweatherapp.app.di.subcomponent.forecast.fragment

import dagger.Subcomponent
import es.jarroyo.tddweatherapp.ui.forecast.fragment.ForecastFragment

@Subcomponent(modules = arrayOf(ForecastFragmentModule::class))
interface ForecastFragmentComponent {
    fun injectTo(fragment: ForecastFragment)
}