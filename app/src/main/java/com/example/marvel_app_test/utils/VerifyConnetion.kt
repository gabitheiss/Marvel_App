package com.example.marvel_app_test.utils

import android.content.Context
import android.net.ConnectivityManager

fun Context.isConnected() : Boolean {
    val connect = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connect != null) {
        val info = connect.activeNetworkInfo
        return info != null && info.isConnected
    }
    return false
}