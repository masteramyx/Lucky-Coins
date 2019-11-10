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


    @GET("/v1/cryptocurrency/listings/latest")
    fun getCoinListing(@Query("CMC_PRO_API_KEY")
                       key: String = "7b08fc3a-c443-4d59-932d-04bfd6074005"):
            Observable<CoinListResponse>

    //    @Headers(
//            "X-CMC_PRO_API_KEY: 7b08fc3a-c443-4d59-932d-04bfd6074005"
//    )
    @GET("/v1/cryptocurrency/info")
    fun getCoinDetail(@Query("CMC_PRO_API_KEY")
                      key: String = "7b08fc3a-c443-4d59-932d-04bfd6074005",
                      @Query("id")
                      coinId: String): Observable<CoinDetailResponse>


    @GET("/v1/cryptocurrency/info")
    fun getCoinListImages(@Query("CMC_PRO_API_KEY")
                          key: String = "7b08fc3a-c443-4d59-932d-04bfd6074005",
                          @Query("id")
                          coinIdList: String): Observable<CoinDetailResponse>

}