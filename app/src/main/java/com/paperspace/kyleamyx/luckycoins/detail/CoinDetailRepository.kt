package com.paperspace.kyleamyx.luckycoins.detail

import com.paperspace.kyleamyx.luckycoins.models.CoinDetailItem
import io.reactivex.Single

interface CoinDetailRepository {

    fun getCoinDetail(coinId: String): Single<CoinDetailItem?>
}