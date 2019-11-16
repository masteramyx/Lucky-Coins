package com.example.kyleamyx.luckycoins

import com.example.kyleamyx.CoinBaseUrlProvider
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient

open class CoinBaseTest : BaseMockUnitTestClass() {


    private val okHttpClient by lazy(LazyThreadSafetyMode.NONE) {
        OkHttpClient()
    }

    private val coinBaseUrlProvider by lazy(LazyThreadSafetyMode.NONE) {
        object : CoinBaseUrlProvider {
            override fun getBaseUrl(): String {
                return mockWebServer.url("/").toString()
            }
        }
    }

    val GSON by lazy(LazyThreadSafetyMode.NONE) {
        GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
    }

    val luckyCoinApiService by lazy(LazyThreadSafetyMode.NONE) {
        buildLuckyCoinsListApiService(okHttpClient, coinBaseUrlProvider)
    }
}