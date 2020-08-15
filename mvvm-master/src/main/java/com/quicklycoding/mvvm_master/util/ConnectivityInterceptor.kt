package com.quicklycoding.mvvm_master.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.quicklycoding.mvvm_master.util.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor(context: Context) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!applicationContext.isInternetAvailable())
            throw NoInternetException("No Internet")
        return chain.proceed(chain.request())
    }


    // check Internet
    private fun Context.isInternetAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //API > 23
            val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)

            capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))

        } else {//API < 23

            val activeNetwork = cm.activeNetworkInfo

            activeNetwork != null && activeNetwork.isConnected
        }
    }


}