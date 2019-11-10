package com.example.kyleamyx.luckycoins.list

import com.example.kyleamyx.luckycoins.models.CoinListItem
import io.reactivex.Single

interface CoinListRepository {

    // Returns a list of the URL's for each coin in the List Controller and their corresponding ID's
    fun getCoinListImages(coinIdList: List<String>): Single<List<Pair<String, String>>>

    fun buildCacheList(): Single<List<String>>

    fun getListNoImages(): Single<List<CoinListItem>>

    fun buildListWithImages(): Single<List<CoinListItem>>
}