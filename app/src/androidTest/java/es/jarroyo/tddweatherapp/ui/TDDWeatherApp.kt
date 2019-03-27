package es.jarroyo.tddweatherapp.ui

import es.jarroyo.tddweatherapp.app.di.DaggerTestApplicationComponent
import es.jarroyo.tddweatherapp.app.di.TestApplicationModule

open class TDDWeatherApp : App() {

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
        graph = DaggerTestApplicationComponent.builder()
                .testApplicationModule(TestApplicationModule(this))
                .build()
    }
}