package es.jarroyo.tddweatherapp.ui.account.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.jarroyo.tddweatherapp.R
import es.jarroyo.tddweatherapp.app.di.component.ApplicationComponent
import es.jarroyo.tddweatherapp.app.di.subcomponent.account.fragment.AccountFragmentModule
import es.jarroyo.tddweatherapp.app.navigator.Navigator
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocation
import es.jarroyo.tddweatherapp.ui.account.adapter.AccountListRVAdapter
import es.jarroyo.tddweatherapp.ui.base.BaseFragment
import es.jarroyo.tddweatherapp.ui.viewmodel.LocationViewModel
import es.jarroyo.tddweatherapp.ui.viewmodel.model.ErrorLocationListState
import es.jarroyo.tddweatherapp.ui.viewmodel.model.LoadingLocationListState
import es.jarroyo.tddweatherapp.ui.viewmodel.model.LocationListState
import es.jarroyo.tddweatherapp.ui.viewmodel.model.SuccessLocationListState
import kotlinx.android.synthetic.main.fragment_account.*
import javax.inject.Inject

val REQUEST_CODE_MH_LOGIN = 12

class AccountFragment : BaseFragment() {
    override var layoutId = R.layout.fragment_account

    @Inject
    lateinit var navigator: Navigator

    // View model
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var locationviewModel: LocationViewModel

    // RV Adapter
    private var mLayoutManager: LinearLayoutManager? = null
    private var mRvAdapter: AccountListRVAdapter? = null

    override fun setupInjection(applicationComponent: ApplicationComponent) {
        applicationComponent.plus(AccountFragmentModule(this)).injectTo(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflateView(inflater, container)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ///Observer
        locationviewModel = ViewModelProviders.of(this, viewModelFactory).get(LocationViewModel::class.java)
        observeCurrentLocationViewModel()

        configRecyclerView()

    }

    override fun onResume() {
        super.onResume()
        getLocationListFromViewModel()
    }

    /**
     * CONFIG VIEW
     */
    fun configRecyclerView() {
        mLayoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        fragment_account_rv.layoutManager = mLayoutManager

        mRvAdapter = AccountListRVAdapter(
            listenerLocationClicked = {

            },
            listenerAddLocationClicked = {

            }
        )

        fragment_account_rv.adapter = mRvAdapter
        fragment_account_swipe_refresh_rv.setOnRefreshListener {
            getLocationListFromViewModel()
        }
    }

    private fun getLocationListFromViewModel() {
        locationviewModel.getWeatherLocationList()
    }

    /****************************************************************************
     * OBSERVER
     ***************************************************************************/

    /** CURRENT LOCATION OBSERVER **/
    private fun observeCurrentLocationViewModel() {
        locationviewModel.locationListLiveData.observe(this, locationListStateObserver)

    }

    private val locationListStateObserver = Observer<LocationListState> { state ->
        state?.let {
            when (state) {
                is SuccessLocationListState -> {
                    hideLoading()
                    val success = it.response as Response.Success
                    showLocationList(success.data)
                }
                is LoadingLocationListState -> {
                    showLoading()
                }
                is ErrorLocationListState -> {
                    hideLoading()
                    //showError((it as ErrorCurrentWeatherState))
                }
            }
        }
    }

    /**
     * SHOW LOCATION LIST
     */
    private fun showLocationList(locationList: List<WeatherLocation>) {
        mRvAdapter?.setLocationList(locationList)
        mRvAdapter?.notifyDataSetChanged()
    }

    /**
     * SHOW LOADING
     */
    private fun showLoading() {
        fragment_account_swipe_refresh_rv.setRefreshing(true)
    }

    /**
     * HIDE LOADING
     */
    private fun hideLoading() {
        fragment_account_swipe_refresh_rv.setRefreshing(false)
    }


    companion object {
        fun newInstance() =
            AccountFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
