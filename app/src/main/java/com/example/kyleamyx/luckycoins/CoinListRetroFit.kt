package com.example.kyleamyx.luckycoins

import com.example.kyleamyx.luckycoins.api.LuckyCoinApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by kyleamyx on 6/22/18.
 */
open class CoinListRetroFit {

    private val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpClient = OkHttpClient.Builder()
            .readTimeout(100, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()


    private val retroFit: Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl("https://pro-api.coinmarketcap.com")
            .build()

    val coinService: LuckyCoinApiService = retroFit.create(LuckyCoinApiService::class.java)

    companion object {
        val MARKET_API_KEY = "7b08fc3a-c443-4d59-932d-04bfd6074005"
    }

}