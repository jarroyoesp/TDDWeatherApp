package es.jarroyo.tddweatherapp.ui.forecast.fragment.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import es.jarroyo.tddweatherapp.R
import es.jarroyo.tddweatherapp.domain.model.forecast.Forecast

class ForecastListRVAdapter(
    private var mForecast: Forecast? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun getItemCount(): Int {
        if (mForecast?.list != null) {
            return mForecast?.list?.size!!
        }
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_forecast, parent, false)
        return ForecastViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mForecastPrediction = mForecast?.list?.get(position)
        val forecastViewHolder = holder as ForecastViewHolder
        forecastViewHolder.bind(mForecastPrediction!!, position)
    }



    fun setForecast(forecast: Forecast) {
        mForecast = forecast
    }
}

data class ItemForecast(val position: Int)