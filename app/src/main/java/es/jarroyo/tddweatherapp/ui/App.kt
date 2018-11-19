package es.jarroyo.tddweatherapp.ui

import android.app.Application

import es.jarroyo.tddweatherapp.app.di.component.ApplicationComponent
import es.jarroyo.tddweatherapp.app.di.component.DaggerApplicationComponent
import es.jarroyo.tddweatherapp.app.di.module.ApplicationModule

open class App : Application() {
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