package com.paperspace.kyleamyx.luckycoins.api.response


import com.paperspace.kyleamyx.luckycoins.models.CoinDetailItem
import com.google.gson.JsonElement
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinDetailResponse(@Expose
                              @SerializedName("data")
                              val data: JsonElement) {

    data class Item(@Expose
                    @SerializedName("id")
                    val item: CoinDetailItem)
}