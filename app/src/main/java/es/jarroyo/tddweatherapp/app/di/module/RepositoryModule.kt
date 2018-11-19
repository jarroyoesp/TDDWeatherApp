package es.jarroyo.tddweatherapp.app.di.module

import dagger.Module
import dagger.Provides
import es.jarroyo.tddweatherapp.data.repository.WeatherRepository
import es.jarroyo.tddweatherapp.data.source.disk.DiskDataSource
import es.jarroyo.tddweatherapp.data.source.network.INetworkDataSource
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideForecastRepository(
        networkDataSource: INetworkDataSource,
        diskDataSource: DiskDataSource
    ) = WeatherRepository(networkDataSource, diskDataSource)
}