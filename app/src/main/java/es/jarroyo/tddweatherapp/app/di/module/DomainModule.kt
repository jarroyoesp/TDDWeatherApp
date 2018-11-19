package es.jarroyo.tddweatherapp.app.di.module

import dagger.Module
import dagger.Provides
import es.jarroyo.tddweatherapp.data.repository.WeatherRepository
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherUseCase
import javax.inject.Singleton


@Module
class DomainModule {

    @Provides
    @Singleton
    fun provideGetCurrentWeatherUseCase(repository: WeatherRepository) = GetCurrentWeatherUseCase(repository)


}