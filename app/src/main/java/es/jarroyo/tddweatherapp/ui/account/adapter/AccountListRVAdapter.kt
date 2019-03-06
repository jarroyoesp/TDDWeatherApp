package es.jarroyo.tddweatherapp.ui.account.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.jarroyo.tddweatherapp.R
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocation
import kotlinx.android.synthetic.main.item_rv_account.view.*

class AccountListRVAdapter(
    private var mWeatherLocationList: List<WeatherLocation>? = listOf<WeatherLocation>(),
    private val listenerLocationClicked: (ItemAccount) -> Unit,
    private val listenerDeleteLocationClicked: (ItemAccount) -> Unit,
    private val listenerAddLocationClicked: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // CONSTANTS
    // ---------
    companion object {
        val TYPE_LOCATION = 0
        val TYPE_ADD_LOCATION = 1
    }

    override fun getItemCount(): Int {
        if (mWeatherLocationList != null) {
            return mWeatherLocationList?.size!! + 1
        }
        return 1
    }

    override fun getItemViewType(position: Int): Int {
        if (mWeatherLocationList != null && position < mWeatherLocationList?.size!!) {
            return TYPE_LOCATION
        } else {
            return TYPE_ADD_LOCATION
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_LOCATION -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_rv_account, parent, false)
                return LocationViewHolder(itemView)
            }

            TYPE_ADD_LOCATION -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_rv_add_location, parent, false)
                return AddLocationViewHolder(itemView)
            }

            else -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_rv_account, parent, false)
                return LocationViewHolder(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_LOCATION -> {
                val mWeatherLocation = mWeatherLocationList?.get(position)
                val accountHolder = holder as LocationViewHolder
                accountHolder.bind(mWeatherLocation!!, position, listenerLocationClicked, listenerDeleteLocationClicked)
            }

            TYPE_ADD_LOCATION -> {
                val addLocationHolder = holder as AddLocationViewHolder
                addLocationHolder.bind(listenerAddLocationClicked)
            }
        }

    }

    class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weatherLocation: WeatherLocation, position: Int, listener: (ItemAccount) -> Unit, listenerDeleted: (ItemAccount) -> Unit) = with(itemView) {
            item_rv_account_tv_title.text = weatherLocation.cityName

            // Delete location
            item_rv_account_iv_delete.setOnClickListener{
                listenerDeleted(ItemAccount(position, weatherLocation))
            }

            setOnClickListener {
                listener(ItemAccount(position, weatherLocation))
            }
        }
    }

    class AddLocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(listener: () -> Unit) = with(itemView) {


            setOnClickListener {
                listener()
            }
        }
    }

    fun setLocationList(locationList: List<WeatherLocation>) {
        mWeatherLocationList = locationList
    }
}

data class ItemAccount(val position: Int, val weatherLocation: WeatherLocation)