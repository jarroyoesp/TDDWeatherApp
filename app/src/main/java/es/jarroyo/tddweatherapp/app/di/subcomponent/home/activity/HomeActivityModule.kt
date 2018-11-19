package es.jarroyo.tddweatherapp.app.di.subcomponent.home.activity

import dagger.Module
import es.jarroyo.tddweatherapp.app.di.module.ActivityModule
import es.jarroyo.tddweatherapp.ui.home.activity.HomeActivity

@Module
class HomeActivityModule(activity: HomeActivity) : ActivityModule(activity) {

}