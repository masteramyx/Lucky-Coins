package com.example.kyleamyx.luckycoins

import com.example.kyleamyx.luckycoins.api.LuckyCoinApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by kyleamyx on 6/22/18.
 */
open class CoinListRetroFit {
    private val retroFit: Retrofit = Retrofit.Builder()
            .baseUrl(" https://api.coinmarketcap.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val coinList: LuckyCoinApiService = retroFit.create(LuckyCoinApiService::class.java)

}