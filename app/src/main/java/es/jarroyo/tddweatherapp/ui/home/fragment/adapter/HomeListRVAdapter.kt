package es.jarroyo.tddweatherapp.ui.home.fragment.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.jarroyo.tddweatherapp.R
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeather
import es.jarroyo.tddweatherapp.ui.account.adapter.AccountListRVAdapter.Companion.TYPE_LOCATION
import kotlinx.android.synthetic.main.item_rv_weather.view.*

class HomeListRVAdapter(
    private var mWeatherList: List<CurrentWeather>? = listOf<CurrentWeather>(),
    private val listenerWeatherClicked: (ItemWeather) -> Unit,
    private val listenerAddLocationClicked: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // CONSTANTS
    // ---------
    companion object {
        val TYPE_WEATHER = 0
        val TYPE_ADD_LOCATION = 1
    }

    override fun getItemCount(): Int {
        if (mWeatherList != null) {
            return mWeatherList?.size!!
        }
        return 0
    }

    override fun getItemViewType(position: Int): Int {
        if (mWeatherList != null && position < mWeatherList?.size!!) {
            return TYPE_WEATHER
        } else {
            return TYPE_ADD_LOCATION
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_WEATHER -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_rv_weather, parent, false)
                return WeatherViewHolder(itemView)
            }

            TYPE_ADD_LOCATION -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_rv_add_location, parent, false)
                return AddLocationViewHolder(itemView)
            }

            else -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_rv_weather, parent, false)
                return WeatherViewHolder(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_LOCATION -> {
                val mWeather = mWeatherList?.get(position)
                val weatherHolder = holder as WeatherViewHolder
                weatherHolder.bind(mWeather!!, position, listenerWeatherClicked)
            }

            TYPE_ADD_LOCATION -> {
                val addLocationHolder = holder as AddLocationViewHolder
                addLocationHolder.bind(listenerAddLocationClicked)
            }
        }

    }

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weather: CurrentWeather, position: Int, listener: (ItemWeather) -> Unit) = with(itemView) {
            item_rv_weather_tv_city.text = weather.name
            item_rv_weather_tv_temp.text = weather.main.temp.toString()
            setOnClickListener {
                listener(ItemWeather(position, weather))
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

    fun setWeatherList(weatherList: List<CurrentWeather>?) {
        mWeatherList = weatherList
    }
}

data class ItemWeather(val position: Int, val weather: CurrentWeather)