package com.example.kyleamyx.luckycoins.api.response

import com.example.kyleamyx.luckycoins.models.CoinListItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by kyleamyx on 6/23/18.
 */
data class CoinListResponse(@Expose
                            @SerializedName("data")
                            val cryptoList: List<CoinListItem>)