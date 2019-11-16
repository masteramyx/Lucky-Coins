package com.example.kyleamyx.luckycoins.detail

import com.example.kyleamyx.luckycoins.models.CoinDetailItem
import io.reactivex.Single

interface CoinDetailRepository {

    fun getCoinDetail(coinId: String): Single<CoinDetailItem>
}