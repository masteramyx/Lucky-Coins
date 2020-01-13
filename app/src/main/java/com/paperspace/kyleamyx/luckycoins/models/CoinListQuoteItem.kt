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
    data class QuoteUSD(@Required val price: Double = 0.0) : Parcelable
}