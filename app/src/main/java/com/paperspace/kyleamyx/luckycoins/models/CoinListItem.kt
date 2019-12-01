package com.paperspace.kyleamyx.luckycoins.models

import android.os.Parcelable
import com.paperspace.kyleamyx.luckycoins.favorites.db.CoinFavoriteItem
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
        val tags: List<String>? = null,
        var logo: String? = null
) : Parcelable {


    fun toFavoriteItem(): CoinFavoriteItem {
        return CoinFavoriteItem(this.id.toInt(),
                this.slug,
                this.name,
                this.symbol)
    }


    companion object {
        val EMPTY
            get() =
                CoinListItem("", "", "", "", CoinListQuoteItem(CoinListQuoteItem.QuoteUSD(0.0)), emptyList(), "")
    }
}