package com.example.kyleamyx.luckycoins.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by kyleamyx on 6/22/18.
 */
data class CoinListItem(val logo: String? = null,
                        @Expose
                        @SerializedName("id")
                        var id: String? = null,
                        @Expose
                        @SerializedName("name")
                        var name: String? = null,
                        @Expose
                        @SerializedName("symbol")
                        val symbol: String? = null,
                        @Expose
                        @SerializedName("website_slug")
                        val slug: String? = null,
                        @Expose
                        @SerializedName("quote")
                        val quoteItem: CoinListQuoteItem
) : Parcelable {

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readParcelable<CoinListQuoteItem>(CoinListQuoteItem::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(logo)
        writeString(id)
        writeString(name)
        writeString(symbol)
        writeString(slug)
        writeParcelable(quoteItem, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CoinListItem> = object : Parcelable.Creator<CoinListItem> {
            override fun createFromParcel(source: Parcel): CoinListItem = CoinListItem(source)
            override fun newArray(size: Int): Array<CoinListItem?> = arrayOfNulls(size)
        }
    }
}