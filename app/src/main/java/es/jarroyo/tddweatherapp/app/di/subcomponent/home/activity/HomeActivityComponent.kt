package es.jarroyo.tddweatherapp.app.di.subcomponent.home.activity

import dagger.Subcomponent
import es.jarroyo.tddweatherapp.ui.home.activity.HomeActivity

@Subcomponent(modules = arrayOf(HomeActivityModule::class))
interface HomeActivityComponent {
    fun injectTo(activity: HomeActivity)
}