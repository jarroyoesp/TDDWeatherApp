package es.jarroyo.tddweatherapp.app.worker

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import es.jarroyo.tddweatherapp.app.di.component.ApplicationComponent
import es.jarroyo.tddweatherapp.app.di.subcomponent.worker.weather.WeatherWorkerModule
import es.jarroyo.tddweatherapp.app.notification.NotificationTDDManager
import es.jarroyo.tddweatherapp.data.repository.WeatherRepository
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.usecase.currentWeather.GetCurrentWeatherByNameRequest
import es.jarroyo.tddweatherapp.ui.App
import es.jarroyo.tddweatherapp.ui.home.activity.HomeActivity
import es.jarroyo.tddweatherapp.utils.launchSilent
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class WeatherWorker(context: Context, params: WorkerParameters): Worker(context, params) {
    @Inject
    lateinit var weatherRepository: WeatherRepository

    @Inject
    lateinit var coroutineContext: CoroutineContext

    @Inject
    lateinit var notificationTDDManager: NotificationTDDManager

    private var job: Job = Job()

    fun setupInjection(applicationComponent: ApplicationComponent) {
        applicationComponent.plus(WeatherWorkerModule(this))
                .injectTo(this)
    }

    override fun doWork(): Result {

        val context = applicationContext

        setupInjection((context?.applicationContext as App).getApplicationComponent())

        launchSilent(coroutineContext, job) {
            val request = GetCurrentWeatherByNameRequest("Zaragoza")
            val response = weatherRepository.getCurrentWeather(request)
            if (response is Response.Success) {
                Log.d(TAG, " Weather = ${response.data.name}")

                var intent = Intent (context, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                notificationTDDManager.showNotificationWorker(context, "${response.data.name}", "Temp: ${response.data.main?.temp} ºC", "WORKER", intent)
            }
        }

        return Result.failure()

    }

    companion object {
        val TAG = WeatherWorker::class.java.simpleName
    }

}