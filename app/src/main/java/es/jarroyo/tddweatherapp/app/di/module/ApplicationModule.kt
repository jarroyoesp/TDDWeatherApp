package es.jarroyo.tddweatherapp.app.di.module

import dagger.Module
import dagger.Provides
import es.jarroyo.tddweatherapp.app.navigator.Navigator
import es.jarroyo.tddweatherapp.ui.App
import javax.inject.Singleton

@Module
class ApplicationModule(val app: App) {
    @Provides @Singleton
    fun provideApp(): App = app

    @Provides @Singleton
    fun provideNavigator(): Navigator = Navigator()
}