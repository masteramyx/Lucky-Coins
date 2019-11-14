package com.example.kyleamyx.luckycoins.detail

import com.example.kyleamyx.luckycoins.api.LuckyCoinApiService
import com.example.kyleamyx.luckycoins.models.CoinDetailItem
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.reactivex.Single
import org.koin.core.context.GlobalContext.get

class CoinDetailRepositoryImpl(
        val luckyCoinApiService: LuckyCoinApiService = get().koin.get()
) : CoinDetailRepository {

    private val GSON: Gson = get().koin.get()

    override fun getCoinDetail(coinId: String): Single<CoinDetailItem> {
        return luckyCoinApiService.getCoinDetail(coinId = coinId)
                .singleOrError()
                .map { coinDetailResponse ->
                    GSON.fromJson((coinDetailResponse.data as JsonObject).get(coinId), CoinDetailItem::class.java)
                }
    }
}