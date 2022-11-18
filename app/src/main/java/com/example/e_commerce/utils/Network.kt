package com.example.e_commerce.utils

import android.content.Context
import android.net.ConnectivityManager
import android.provider.Settings.Global.getString
import android.widget.Toast
import com.example.e_commerce.R

object Network {

    fun checkConnectionType(context: Context) {
        val connectionManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi_Connection = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobile_data_connection = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

        if (wifi_Connection != null) {
            if (!wifi_Connection.isConnectedOrConnecting) {
                if (mobile_data_connection != null) {
                    if (!mobile_data_connection.isConnectedOrConnecting) {
                        throw  Exception(context.getString(R.string.No_Network_Connection))
                    }
                }
            }
        }
    }
}