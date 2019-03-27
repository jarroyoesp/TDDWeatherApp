package es.jarroyo.tddweatherapp.app.di.subcomponent.worker.weather

import dagger.Module
import es.jarroyo.tddweatherapp.app.worker.WeatherWorker

@Module
class WeatherWorkerModule(val weatherWorker: WeatherWorker) {

}