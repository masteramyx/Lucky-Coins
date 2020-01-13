package com.paperspace.kyleamyx.luckycoins.models

import android.os.Parcelable
import com.paperspace.kyleamyx.luckycoins.favorites.db.CoinFavoriteItem
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

/**
 * Created by kyleamyx on 6/22/18.
 */
@Parcelize
@Serializable
data class CoinListItem(
        var id: String,
        var name: String,
        val symbol: String,
        val slug: String,
        val quote: CoinListQuoteItem,
        val tags: List<String>,
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