package es.jarroyo.tddweatherapp.app.di.subcomponent.worker.weather

import dagger.Subcomponent
import es.jarroyo.tddweatherapp.app.worker.WeatherWorker

@Subcomponent(modules = arrayOf(WeatherWorkerModule::class))
interface WeatherWorkerComponent {
    fun injectTo(weatherWorker: WeatherWorker)
}