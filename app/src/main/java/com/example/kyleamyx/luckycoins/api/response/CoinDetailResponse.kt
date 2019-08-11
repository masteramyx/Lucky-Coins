package com.example.kyleamyx.luckycoins.api.response

import com.example.kyleamyx.luckycoins.models.CoinDetailItem
import com.example.kyleamyx.luckycoins.models.CoinListItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinDetailResponse(@Expose
                              @SerializedName("data")
                              val data: DetailResponse) {



    data class DetailResponse(
                              @Expose
                              @SerializedName("")
                              val item: CoinDetailItem)

}