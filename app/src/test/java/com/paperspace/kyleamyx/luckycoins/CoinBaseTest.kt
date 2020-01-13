package com.paperspace.kyleamyx.luckycoins

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

    val luckyCoinApiService by lazy(LazyThreadSafetyMode.NONE) {
        buildLuckyCoinsListApiService(okHttpClient, coinBaseUrlProvider)
    }
}