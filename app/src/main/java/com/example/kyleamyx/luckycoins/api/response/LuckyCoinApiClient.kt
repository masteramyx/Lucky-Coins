package com.example.kyleamyx.luckycoins.api.response

import com.example.kyleamyx.luckycoins.CoinListRetroFit
import com.example.kyleamyx.luckycoins.models.CoinListItem
import io.reactivex.Observable

/**
 * Created by kyleamyx on 6/23/18.
 */
class LuckyCoinApiClient : CoinListRetroFit() {

    fun getCoins(): Observable<List<CoinListItem>> =
            coinList.getCoinListing().map { response ->
                response.cryptoList
            }
}