package es.jarroyo.tddweatherapp.ui.home.activity

import android.net.Uri
import android.os.Bundle
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import es.jarroyo.tddweatherapp.R
import es.jarroyo.tddweatherapp.app.di.component.ApplicationComponent
import es.jarroyo.tddweatherapp.app.di.subcomponent.home.activity.HomeActivityModule
import es.jarroyo.tddweatherapp.app.worker.WeatherWorker
import es.jarroyo.tddweatherapp.ui.base.BaseActivity
import es.jarroyo.tddweatherapp.ui.home.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity(), HomeFragment.OnFragmentInteractionListener {

    override var layoutId = R.layout.activity_home

    override fun setupInjection(applicationComponent: ApplicationComponent) {
        applicationComponent.plus(HomeActivityModule(this)).injectTo(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity_home_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        //initPeriodicWorker()
    }

    /**
     * FRAGMENT INTERACTION
     */
    override fun onFragmentInteraction(uri: Uri) {

    }

    /**
     * NAVIGATION BOTTOM
     */
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                cleanFragmentBackStack()
                return@OnNavigationItemSelectedListener true
            }
            /*R.id.navigation_dashboard -> {
                *//*if (activity_main_navigation.getSelectedItemId() != R.id.navigation_dashboard) {
                    navigator.addSurveyCompletedListFragment(R.id.activity_main_layout_main)
                }*//*
                return@OnNavigationItemSelectedListener true
            }*/
            R.id.navigation_account -> {
                if (activity_home_navigation.getSelectedItemId() != R.id.navigation_account) {
                    navigator.addAccountFragment(R.id.activity_home_layout_main)
                }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    /**
     * ON BACK PRESSED
     */
    override fun onBackPressed() {
        val homeItem = activity_home_navigation.getMenu().getItem(0)
        if (activity_home_navigation.getSelectedItemId() !== homeItem.getItemId()) {
            // Select home item
            activity_home_navigation.setSelectedItemId(homeItem.getItemId())
        } else {
            super.onBackPressed()
        }
    }

    private fun initPeriodicWorker() {
        val mWorkManager = WorkManager.getInstance()
        mWorkManager?.cancelAllWorkByTag(WeatherWorker.TAG)

        val periodicBuilder = PeriodicWorkRequest.Builder(WeatherWorker::class.java, 15, java.util.concurrent.TimeUnit.MINUTES)
        val myWork = periodicBuilder.addTag(WeatherWorker.TAG).build()
        mWorkManager?.enqueueUniquePeriodicWork(WeatherWorker.TAG, ExistingPeriodicWorkPolicy.KEEP, myWork)

   }
}
