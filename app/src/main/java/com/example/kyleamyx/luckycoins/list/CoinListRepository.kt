package com.example.kyleamyx.luckycoins.list

import com.example.kyleamyx.luckycoins.models.CoinListItem
import io.reactivex.Single

interface CoinListRepository {

    fun getCoinList() : Single<List<CoinListItem>>
}