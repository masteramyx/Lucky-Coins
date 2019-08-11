package com.example.kyleamyx.luckycoins.api.response

import com.example.kyleamyx.luckycoins.CoinListRetroFit
import com.example.kyleamyx.luckycoins.models.CoinDetailItem
import com.example.kyleamyx.luckycoins.models.CoinListItem
import io.reactivex.Observable

/**
 * Created by kyleamyx on 6/23/18.
 */
class LuckyCoinApiClient : CoinListRetroFit() {

    fun getCoins(): Observable<List<CoinListItem>> =
            coinService.getCoinListing().map { response ->
                response.cryptoList
            }

    fun getCoinDetail(id: String): Observable<CoinDetailItem> =
            coinService.getCoinDetail(id).map { response ->
                response.data.item
            }
}