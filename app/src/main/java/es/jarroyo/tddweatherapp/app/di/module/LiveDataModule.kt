package es.jarroyo.tddweatherapp.app.di.module

import android.arch.lifecycle.MutableLiveData
import dagger.Module
import dagger.Provides
import es.jarroyo.tddweatherapp.ui.home.model.ForecastState

@Module
class LiveDataModule {

    @Provides
    fun provideForecaststateLiveData(): MutableLiveData<ForecastState> =
            MutableLiveData<ForecastState>()
}
