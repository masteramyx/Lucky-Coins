package com.paperspace.kyleamyx.luckycoins.detail

import com.paperspace.kyleamyx.luckycoins.api.LuckyCoinApiService
import com.paperspace.kyleamyx.luckycoins.models.CoinDetailItem
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.reactivex.Single

class CoinDetailRepositoryImpl(
        val luckyCoinApiService: LuckyCoinApiService,
        val GSON: Gson
) : CoinDetailRepository {

    override fun getCoinDetail(coinId: String): Single<CoinDetailItem> {
        return luckyCoinApiService.getCoinDetail(coinId = coinId)
                .singleOrError()
                .map { coinDetailResponse ->
                    GSON.fromJson((coinDetailResponse.data as JsonObject).get(coinId), CoinDetailItem::class.java)
                }
    }
}