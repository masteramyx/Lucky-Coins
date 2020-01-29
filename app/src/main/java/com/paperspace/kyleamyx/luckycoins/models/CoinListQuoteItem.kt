package com.paperspace.kyleamyx.luckycoins.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class CoinListQuoteItem(val USD: QuoteUSD) : Parcelable {

    @Parcelize
    @Serializable
    data class QuoteUSD(@Required
                        val price: Double = 0.0,
                        val percent_change_1h: Double,
                        val percent_change_24h: Double,
                        val percent_change_7d: Double) : Parcelable
}