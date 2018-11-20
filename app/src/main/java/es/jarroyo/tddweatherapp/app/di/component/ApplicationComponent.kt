package es.jarroyo.tddweatherapp.app.di.component

import dagger.Component
import es.jarroyo.tddweatherapp.app.di.module.*
import es.jarroyo.tddweatherapp.app.di.subcomponent.home.activity.HomeActivityComponent
import es.jarroyo.tddweatherapp.app.di.subcomponent.home.activity.HomeActivityModule
import es.jarroyo.tddweatherapp.app.di.subcomponent.home.fragment.HomeFragmentComponent
import es.jarroyo.tddweatherapp.app.di.subcomponent.home.fragment.HomeFragmentModule
import es.jarroyo.tddweatherapp.app.di.viewmodel.ViewModelFactoryModule
import es.jarroyo.tddweatherapp.app.di.viewmodel.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        ApplicationModule::class,
        UtilsModule::class,
        RepositoryModule::class,
        DataModule::class,
        DomainModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class
    )
)
interface ApplicationComponent {
    /**
     * UI - ACTIVITY
     */
    fun plus(module: HomeActivityModule): HomeActivityComponent
    fun plus(module: HomeFragmentModule): HomeFragmentComponent

}