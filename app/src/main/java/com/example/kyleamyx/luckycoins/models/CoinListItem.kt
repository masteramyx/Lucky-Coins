package com.example.kyleamyx.luckycoins.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by kyleamyx on 6/22/18.
 */
data class CoinListItem(@Expose
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
                        val slug: String? = null

) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(symbol)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CoinListItem> {
        override fun createFromParcel(parcel: Parcel): CoinListItem {
            return CoinListItem(parcel)
        }

        override fun newArray(size: Int): Array<CoinListItem?> {
            return arrayOfNulls(size)
        }
    }

}