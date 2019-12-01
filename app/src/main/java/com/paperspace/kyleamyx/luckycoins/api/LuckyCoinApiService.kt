package com.paperspace.kyleamyx.luckycoins.api

import com.paperspace.kyleamyx.luckycoins.BuildConfig
import com.paperspace.kyleamyx.luckycoins.api.response.CoinDetailResponse
import com.paperspace.kyleamyx.luckycoins.api.response.CoinListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by kyleamyx on 6/22/18.
 */

interface LuckyCoinApiService {


    @GET("/v1/cryptocurrency/listings/latest")
    fun getCoinListing(@Query("CMC_PRO_API_KEY")
                       key: String = BuildConfig.LUCKY_COINS_API_KEY):
            Observable<CoinListResponse>


    @GET("/v1/cryptocurrency/info")
    fun getCoinDetail(@Query("CMC_PRO_API_KEY")
                      key: String = BuildConfig.LUCKY_COINS_API_KEY,
                      @Query("id")
                      coinId: String): Observable<CoinDetailResponse>


    @GET("/v1/cryptocurrency/info")
    fun getCoinListImages(@Query("CMC_PRO_API_KEY")
                          key: String = BuildConfig.LUCKY_COINS_API_KEY,
                          @Query("id")
                          coinIdList: String): Observable<CoinDetailResponse>

}