package com.example.kyleamyx.luckycoins.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoinDetailItem(@Expose
                          @SerializedName("id")
                          val id: String,
                          @Expose
                          @SerializedName("logo")
                          val logo: String? = null,
                          @Expose
                          @SerializedName("name")
                          val name: String? = null,
                          @Expose
                          @SerializedName("symbol")
                          val symbol: String? = null,
                          @Expose
                          @SerializedName("description")
                          val description: String? = null) : Parcelable
