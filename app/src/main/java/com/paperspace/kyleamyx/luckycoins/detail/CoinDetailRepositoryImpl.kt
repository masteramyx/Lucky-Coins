package com.paperspace.kyleamyx.luckycoins.detail

import com.paperspace.kyleamyx.luckycoins.api.LuckyCoinApiService
import com.paperspace.kyleamyx.luckycoins.models.CoinDetailItem
import io.reactivex.Single

class CoinDetailRepositoryImpl(
        val luckyCoinApiService: LuckyCoinApiService
) : CoinDetailRepository {

    override fun getCoinDetail(coinId: String): Single<CoinDetailItem?> {
        return luckyCoinApiService.getCoinDetail(coinId = coinId)
                .singleOrError()
                .map { coinDetailResponse ->
                    coinDetailResponse.data.map {
                        it.value
                    }.firstOrNull()
                }
    }
}