package es.jarroyo.tddweatherapp.app.di.module

import dagger.Module
import dagger.Provides
import es.jarroyo.tddweatherapp.data.mapper.location.WeatherLocationEntitytoWeatherLocationMapper
import es.jarroyo.tddweatherapp.data.mapper.location.WeatherLocationToWeatherLocationEntityMapper
import es.jarroyo.tddweatherapp.data.repository.ForecastRepository
import es.jarroyo.tddweatherapp.data.repository.LocationRepository
import es.jarroyo.tddweatherapp.data.repository.UserRepository
import es.jarroyo.tddweatherapp.data.repository.WeatherRepository
import es.jarroyo.tddweatherapp.data.source.disk.DiskDataSource
import es.jarroyo.tddweatherapp.data.source.network.INetworkDataSource
import es.jarroyo.tddweatherapp.ui.App
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(
        networkDataSource: INetworkDataSource,
        diskDataSource: DiskDataSource
    ) = WeatherRepository(networkDataSource, diskDataSource)

    @Provides
    @Singleton
    fun provideLocationRepository(
        app: App,
        diskDataSource: DiskDataSource,
        weatherLocationToWeatherLocationEntityMapper: WeatherLocationToWeatherLocationEntityMapper,
        weatherLocationEntitytoWeatherLocationMapper: WeatherLocationEntitytoWeatherLocationMapper,
        userRepository: UserRepository
    ) = LocationRepository(app, diskDataSource, weatherLocationToWeatherLocationEntityMapper, weatherLocationEntitytoWeatherLocationMapper, userRepository)

    @Provides
    @Singleton
    fun provideForecastRepository(
        networkDataSource: INetworkDataSource,
        diskDataSource: DiskDataSource
    ) = ForecastRepository(networkDataSource, diskDataSource)

    @Provides
    @Singleton
    fun provideUserRepository(
        networkDataSource: INetworkDataSource,
        diskDataSource: DiskDataSource
    ) = UserRepository(networkDataSource, diskDataSource)
}