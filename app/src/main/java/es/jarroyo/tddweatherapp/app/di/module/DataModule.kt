package es.jarroyo.tddweatherapp.app.di.module

import com.microhealth.lmc.utils.NetworkSystemAbstract
import dagger.Module
import dagger.Provides
import es.jarroyo.tddweatherapp.data.source.disk.DiskDataSource
import es.jarroyo.tddweatherapp.data.source.network.INetworkDataSource
import es.jarroyo.tddweatherapp.data.source.network.NetworkDataSource
import es.jarroyo.tddweatherapp.ui.App
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideDiskDataSource(appContext: App) =
            DiskDataSource(appContext)

    @Provides @Singleton
    fun provideNetworkDataSource(networkSystemBase: NetworkSystemAbstract) =
            NetworkDataSource(networkSystemBase) as INetworkDataSource
}
