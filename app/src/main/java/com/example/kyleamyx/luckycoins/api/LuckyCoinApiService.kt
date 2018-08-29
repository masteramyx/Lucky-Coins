package com.example.kyleamyx.luckycoins.api

import com.example.kyleamyx.luckycoins.models.CoinListItem
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by kyleamyx on 6/22/18.
 */

interface LuckyCoinApiService {

    @GET("listings/")
    fun getCoinListing() : Observable<List<CoinListItem>>

}