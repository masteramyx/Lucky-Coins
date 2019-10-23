package com.example.kyleamyx.luckycoins.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by kyleamyx on 6/22/18.
 */
@Parcelize
data class CoinListItem(
        @Expose
        @SerializedName("id")
        var id: String,
        @Expose
        @SerializedName("name")
        var name: String? = null,
        @Expose
        @SerializedName("symbol")
        val symbol: String? = null,
        @Expose
        @SerializedName("slug")
        val slug: String? = null,
        @Expose
        @SerializedName("quote")
        val quoteItem: CoinListQuoteItem? = null,
        @Expose
        @SerializedName("tags")
        val tags: List<String>? = null
) : Parcelable {


    companion object {
        val EMPTY get() = CoinListItem("", "", "", "", CoinListQuoteItem(CoinListQuoteItem.QuoteUSD(0.0)), emptyList())
    }
}