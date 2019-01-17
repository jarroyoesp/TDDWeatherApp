package es.jarroyo.tddweatherapp.ui.forecast.fragment.adapter

import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.View
import es.jarroyo.tddweatherapp.BuildConfig
import es.jarroyo.tddweatherapp.R
import es.jarroyo.tddweatherapp.domain.model.forecast.Prediction
import es.jarroyo.tddweatherapp.ui.base.loadUrl
import kotlinx.android.synthetic.main.item_rv_forecast.view.*

class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(prediction: Prediction, position: Int) = with(itemView) {
        item_rv_forecast_tv_date.text = DateUtils.getRelativeDateTimeString(context, prediction.dt * 1000L, DateUtils.SECOND_IN_MILLIS, DateUtils.DAY_IN_MILLIS, DateUtils.FORMAT_NO_NOON)

        // Wind
        item_rv_forecast_tv_wind.text = "${context.getString(R.string.wind)}: ${Math.round(prediction.wind.speed)} km/h"

        // Humidity
        item_rv_forecast_tv_humidity.text = "${context.getString(R.string.humidity)}: ${Math.round(prediction.main.humidity)} %"

        // TEMP BIG
        var tempRounded = Math.round(prediction.main.temp)
        item_rv_forecast_tv_temp_big.text = "${tempRounded} ºC"

        // ICON
        item_rv_forecast_iv_avatar.loadUrl("${BuildConfig.OPEN_WEATHER_URL_ICON_BASE}${prediction.weather.first().icon}.png")
    }
}