package es.jarroyo.tddweatherapp.app.di


import dagger.Module
import dagger.Provides
import es.jarroyo.tddweatherapp.app.navigator.Navigator
import es.jarroyo.tddweatherapp.ui.App
import es.jarroyo.tddweatherapp.ui.TDDWeatherApp
import javax.inject.Singleton


@Module
class TestApplicationModule(val app: TDDWeatherApp){
    @Provides @Singleton
    fun provideApp(): App = app

    @Provides @Singleton
    fun provideNavigator(): Navigator = Navigator()


}