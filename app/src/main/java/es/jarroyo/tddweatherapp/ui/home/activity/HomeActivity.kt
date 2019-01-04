package es.jarroyo.tddweatherapp.ui.home.activity

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import es.jarroyo.tddweatherapp.R
import es.jarroyo.tddweatherapp.app.di.component.ApplicationComponent
import es.jarroyo.tddweatherapp.app.di.subcomponent.home.activity.HomeActivityModule
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
}
