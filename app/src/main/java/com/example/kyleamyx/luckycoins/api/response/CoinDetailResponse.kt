package com.example.kyleamyx.luckycoins.api.response


import com.google.gson.JsonElement
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinDetailResponse(@Expose
                              @SerializedName("data")
                              val data: JsonElement) {




    companion object {

    }

}