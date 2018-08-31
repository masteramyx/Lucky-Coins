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

    val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val okHttpClient = OkHttpClient.Builder()
            .readTimeout(1000, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()


    private val retroFit: Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl("https://api.coinmarketcap.com/v2/")
            .build()

    val coinList: LuckyCoinApiService = retroFit.create(LuckyCoinApiService::class.java)

}