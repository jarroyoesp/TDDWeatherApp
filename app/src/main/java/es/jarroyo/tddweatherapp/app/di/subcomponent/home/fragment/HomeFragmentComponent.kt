package es.jarroyo.tddweatherapp.app.di.subcomponent.home.fragment

import dagger.Subcomponent
import es.jarroyo.tddweatherapp.ui.home.fragment.HomeFragment

@Subcomponent(modules = arrayOf(HomeFragmentModule::class))
interface HomeFragmentComponent {
    fun injectTo(fragment: HomeFragment)
}