package com.example.kyleamyx.luckycoins.api.response

import com.example.kyleamyx.luckycoins.api.LuckyCoinApiService
import com.example.kyleamyx.luckycoins.models.CoinDetailItem
import com.example.kyleamyx.luckycoins.models.CoinListItem
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import io.reactivex.Observable
import org.koin.core.context.GlobalContext.get

/**
 * Created by kyleamyx on 6/23/18.
 */
class LuckyCoinApiClient {

    val coinService: LuckyCoinApiService = get().koin.get()

    private val GSON = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()

    fun getCoins(): Observable<List<CoinListItem>> =
            coinService.getCoinListing().map { response ->
                response.cryptoList
            }

    /**
     * Since the `id` field is unknown we have to get the whole JsonObject and pull out this key and then deserialize
     * the json value into the object
     */
    fun getCoinDetail(id: String): Observable<CoinDetailItem> =
            coinService.getCoinDetail(id).map { response ->
                GSON.fromJson((response.data as JsonObject).get(id), CoinDetailItem::class.java)
            }
}