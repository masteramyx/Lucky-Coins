package com.paperspace.kyleamyx.luckycoins.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class CoinDetailItem(
        val id: String,
        val logo: String? = null,
        val name: String,
        val symbol: String,
        val description: String? = null,
        val urls: CoinDetailUrlItem? = null) : Parcelable {

    companion object {
        val EMPTY
            get() =
                CoinDetailItem("", "", "", "", "Unfortunately we couldn't find this listing :(")
    }
}
