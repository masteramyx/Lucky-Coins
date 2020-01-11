package com.paperspace.kyleamyx.luckycoins.api.response


import com.paperspace.kyleamyx.luckycoins.models.CoinDetailItem
import kotlinx.serialization.Serializable

@Serializable
data class CoinDetailResponse(
        val data: Map<String, CoinDetailItem>) {

//    data class Item(@Expose
//                    @SerializedName("id")
//                    val item: CoinDetailItem)
}