package es.jarroyo.tddweatherapp.app.di

import dagger.Component
import es.jarroyo.tddweatherapp.app.di.component.ApplicationComponent
import es.jarroyo.tddweatherapp.app.di.module.DataModule
import es.jarroyo.tddweatherapp.app.di.module.DomainModule
import es.jarroyo.tddweatherapp.app.di.module.RepositoryModule
import es.jarroyo.tddweatherapp.app.di.module.UtilsModule
import es.jarroyo.tddweatherapp.app.di.viewmodel.ViewModelFactoryModule
import es.jarroyo.tddweatherapp.app.di.viewmodel.ViewModelModule
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(TestApplicationModule::class,
        UtilsModule::class,
        DomainModule::class,
        DataModule::class,
        RepositoryModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class)
)
interface TestApplicationComponent: ApplicationComponent {
}