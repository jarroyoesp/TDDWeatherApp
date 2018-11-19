package es.jarroyo.tddweatherapp.app.di.module

import com.microhealth.lmc.utils.NetworkSystem
import com.microhealth.lmc.utils.NetworkSystemAbstract
import dagger.Module
import dagger.Provides
import es.jarroyo.tddweatherapp.ui.App
import javax.inject.Singleton

@Module
class UtilsModule {
    @Provides
    @Singleton
    fun provideNetworkSystem(app: App) =
            NetworkSystem(app) as NetworkSystemAbstract
}