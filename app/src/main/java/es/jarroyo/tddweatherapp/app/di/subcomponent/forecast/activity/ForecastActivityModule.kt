package es.jarroyo.tddweatherapp.app.di.subcomponent.forecast.activity

import dagger.Module
import es.jarroyo.tddweatherapp.app.di.module.ActivityModule
import es.jarroyo.tddweatherapp.ui.forecast.ForecastActivity

@Module
class ForecastActivityModule(activity: ForecastActivity) : ActivityModule(activity) {

}