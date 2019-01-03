package es.jarroyo.tddweatherapp.ui.account.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.jarroyo.tddweatherapp.R
import es.jarroyo.tddweatherapp.domain.model.location.WeatherLocation
import kotlinx.android.synthetic.main.item_rv_account.view.*

class AccountListRVAdapter(
    private var mWeatherLocationList: List<WeatherLocation>? = listOf<WeatherLocation>(),
    private val listenerLocationClicked: (ItemAccount) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return mWeatherLocationList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_account, parent, false)
        return AccountViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val mWeatherLocation = mWeatherLocationList?.get(position)
        val accountHolder = holder as AccountViewHolder
        accountHolder.bind(mWeatherLocation!!, position, listenerLocationClicked)

    }

    class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weatherLocation: WeatherLocation, position: Int, listener: (ItemAccount) -> Unit) = with(itemView) {
            item_rv_account_tv_title.text = weatherLocation.cityName


            setOnClickListener {
                listener(ItemAccount(position, weatherLocation))
            }
        }
    }
}

data class ItemAccount(val position: Int, val weatherLocation: WeatherLocation)