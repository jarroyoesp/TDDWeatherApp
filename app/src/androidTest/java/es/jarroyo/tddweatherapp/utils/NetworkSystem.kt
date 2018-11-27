package es.jarroyo.tddweatherapp.utils

import android.content.Context
import com.microhealth.lmc.utils.NetworkSystemAbstract

open class NetworkSystem(private val appContext : Context): NetworkSystemAbstract() {

    companion object {
        var mIsNetworkAvailable = true

        fun setNetworkAvailable (isNetworkAvailable: Boolean){
            mIsNetworkAvailable = isNetworkAvailable
        }
    }


    override fun isNetworkAvailable(): Boolean {
        return mIsNetworkAvailable
    }
}