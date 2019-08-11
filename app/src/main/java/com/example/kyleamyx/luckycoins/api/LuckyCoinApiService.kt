package com.example.kyleamyx.luckycoins.api

import com.example.kyleamyx.luckycoins.api.response.CoinDetailResponse
import com.example.kyleamyx.luckycoins.api.response.CoinListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by kyleamyx on 6/22/18.
 */

interface LuckyCoinApiService {


    @Headers(
            "X-CMC_PRO_API_KEY: 7b08fc3a-c443-4d59-932d-04bfd6074005"
    )
    @GET("/v1/cryptocurrency/listings/latest")
    fun getCoinListing(): Observable<CoinListResponse>

    @Headers(
            "X-CMC_PRO_API_KEY: 7b08fc3a-c443-4d59-932d-04bfd6074005"
    )
    @GET("/v1/cryptocurrency/info")
    fun getCoinDetail(@Query("id")
                      coinId: String): Observable<CoinDetailResponse>

}