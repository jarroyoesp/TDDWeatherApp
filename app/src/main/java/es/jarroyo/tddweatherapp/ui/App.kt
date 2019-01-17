package es.jarroyo.tddweatherapp.ui

import android.support.multidex.MultiDexApplication
import es.jarroyo.tddweatherapp.app.di.component.ApplicationComponent
import es.jarroyo.tddweatherapp.app.di.component.DaggerApplicationComponent
import es.jarroyo.tddweatherapp.app.di.module.ApplicationModule

open class App : MultiDexApplication() {
    companion object {
        lateinit var graph: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
        graph = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}