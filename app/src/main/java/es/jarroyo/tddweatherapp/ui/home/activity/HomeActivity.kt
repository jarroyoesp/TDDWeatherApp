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
    }

    /**
     * FRAGMENT INTERACTION
     */
    override fun onFragmentInteraction(uri: Uri) {
    }
}
