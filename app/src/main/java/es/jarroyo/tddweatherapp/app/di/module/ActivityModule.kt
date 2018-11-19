package es.jarroyo.tddweatherapp.app.di.module

import android.content.Context
import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides

@Module
abstract class ActivityModule(protected val activity: AppCompatActivity) {

    @Provides
    fun provideActivity(): AppCompatActivity = activity

    @Provides
    fun provideActiviyContext(): Context = activity.baseContext
}