package com.example.kyleamyx.luckycoins.list

import com.example.kyleamyx.luckycoins.models.CoinListItem
import io.reactivex.Single

interface CoinListRepository {

    fun getCoinList(): Single<List<CoinListItem>>

    // A list of the URL's for each coin in the List Controller
    fun getCoinListImages(coinIdList: List<String>): Single<List<Pair<String, String>>>

    fun buildCacheList(): Single<List<String>>

    fun getList(): Single<List<CoinListItem>>

}