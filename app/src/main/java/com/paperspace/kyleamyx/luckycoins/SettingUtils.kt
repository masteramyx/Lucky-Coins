package com.paperspace.kyleamyx.luckycoins

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.provider.Settings

class SettingUtils {

    /**
     * Check the network status and if it is not enabled, display settings panel for user to enable
     */
    fun checkNetworkConnectivityStatus(context: Context): Boolean {
        val connectivityManger = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManger.activeNetwork == null) {
            return false
        }
        return true

    }


    fun launchPanel(context: Context) {
        if (Build.VERSION.SDK_INT < 29) {
            //no op yet
        } else {
            (context as Activity).startActivityForResult(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY), 0)
        }
    }
}