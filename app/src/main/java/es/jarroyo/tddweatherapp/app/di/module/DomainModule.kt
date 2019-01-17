package es.jarroyo.tddweatherapp.app.di.module

import dagger.Module
import dagger.Provides
import es.jarroyo.tddweatherapp.data.repository.ForecastRepository
import es.jarroyo.tddweatherapp.data.repository.LocationRepository
import es.jarroyo.tddweatherapp.data.repository.WeatherRepository
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherByNameUseCase
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.getWeatherList.GetWeatherListUseCase
import es.jarroyo.tddweatherapp.domain.usecase.forecast.getForecast.GetForecastUseCase
import es.jarroyo.tddweatherapp.domain.usecase.location.currentLocation.GetCurrentLocationUseCase
import es.jarroyo.tddweatherapp.domain.usecase.location.deleteWeatherLocation.DeleteWeatherLocationUseCase
import es.jarroyo.tddweatherapp.domain.usecase.location.getAllWeatherLocationList.GetAllWeatherLocationListUseCase
import es.jarroyo.tddweatherapp.domain.usecase.location.saveWeatherLocation.SaveWeatherLocationUseCase
import javax.inject.Singleton


@Module
class DomainModule {

    /**
     * WEATHER
     */
    @Provides
    @Singleton
    fun provideGetCurrentWeatherUseCase(repository: WeatherRepository) = GetCurrentWeatherByNameUseCase(repository)

    @Provides
    @Singleton
    fun provideGetWeatherListUseCase(repository: WeatherRepository) = GetWeatherListUseCase(repository)


    /**
     * LOCATION
     */
    @Provides
    @Singleton
    fun provideGetCurrentLocationUseCase(repository: LocationRepository) = GetCurrentLocationUseCase(repository)

    @Provides
    @Singleton
    fun provideGetAllWeatherLocationListUseCase(repository: LocationRepository) = GetAllWeatherLocationListUseCase(repository)

    @Provides
    @Singleton
    fun provideSaveWeatherLocationUseCase(repository: LocationRepository) = SaveWeatherLocationUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteWeatherLocationUseCase(repository: LocationRepository) = DeleteWeatherLocationUseCase(repository)

    /**
     * FORECAST
     */
    @Provides
    @Singleton
    fun provideGetForecastUseCase(repository: ForecastRepository) = GetForecastUseCase(repository)
}