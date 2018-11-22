package es.jarroyo.tddweatherapp.app.di.viewmodel

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import es.jarroyo.tddweatherapp.ui.home.viewmodel.CurrentLocationViewModel
import es.jarroyo.tddweatherapp.ui.home.viewmodel.CurrentWeatherViewModel
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrentWeatherViewModel::class)
    abstract fun bindCurrentWeatherViewModel(viewModel: CurrentWeatherViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CurrentLocationViewModel::class)
    abstract fun bindCurrentLocationViewModel(viewModel: CurrentLocationViewModel): ViewModel

}