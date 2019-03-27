package es.jarroyo.tddweatherapp.ui

import androidx.multidex.MultiDexApplication
import com.squareup.leakcanary.LeakCanary
import es.jarroyo.tddweatherapp.app.di.component.ApplicationComponent
import es.jarroyo.tddweatherapp.app.di.component.DaggerApplicationComponent
import es.jarroyo.tddweatherapp.app.di.module.ApplicationModule

open class App : MultiDexApplication() {
    companion object {
        lateinit var graph: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()

        initLeakCanary()
    }

    private fun initializeDagger() {
        graph = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }

    fun getApplicationComponent() = graph
}