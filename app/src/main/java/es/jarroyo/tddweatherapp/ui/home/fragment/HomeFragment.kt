package es.jarroyo.tddweatherapp.ui.home.fragment

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.jarroyo.tddweatherapp.R
import es.jarroyo.tddweatherapp.app.di.component.ApplicationComponent
import es.jarroyo.tddweatherapp.app.di.subcomponent.home.fragment.HomeFragmentModule
import es.jarroyo.tddweatherapp.ui.base.BaseFragment
import es.jarroyo.tddweatherapp.ui.home.viewmodel.CurrentWeatherViewModel
import javax.inject.Inject

class HomeFragment : BaseFragment() {
    override var layoutId = R.layout.fragment_home

    private var listener: OnFragmentInteractionListener? = null

    // View model
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun setupInjection(applicationComponent: ApplicationComponent) {
        applicationComponent.plus(HomeFragmentModule(this)).injectTo(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflateView(inflater, container)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

}
