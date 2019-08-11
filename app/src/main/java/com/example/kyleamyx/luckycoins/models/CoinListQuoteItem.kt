package com.example.kyleamyx.luckycoins.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoinListQuoteItem(@Expose
                             @SerializedName("USD")
                             val quoteUSD: QuoteUSD) : Parcelable {

    @Parcelize
    data class QuoteUSD(@Expose
                        @SerializedName("price")
                        val priceUSD: Double = 0.0) : Parcelable
}