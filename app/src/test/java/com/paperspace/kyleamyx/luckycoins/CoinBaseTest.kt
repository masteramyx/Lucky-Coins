package com.paperspace.kyleamyx.luckycoins

import com.google.gson.GsonBuilder
import com.karakum.coretest.BaseMockUnitTestClass
import com.paperspace.kyleamyx.CoinBaseUrlProvider

open class CoinBaseTest : BaseMockUnitTestClass() {

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