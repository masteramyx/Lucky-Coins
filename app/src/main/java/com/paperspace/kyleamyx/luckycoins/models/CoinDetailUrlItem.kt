package com.paperspace.kyleamyx.luckycoins.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoinDetailUrlItem(@Expose
                             @SerializedName("website")
                             val website: List<String>,
                             @Expose
                             @SerializedName("technical_doc")
                             val technicalDoc: List<String>,
                             @Expose
                             @SerializedName("twitter")
                             val twitter: List<String>,
                             @Expose
                             @SerializedName("reddit")
                             val reddit: List<String>,
                             @Expose
                             @SerializedName("message_board")
                             val messageBoard: List<String>,
                             @Expose
                             @SerializedName("announcement")
                             val announcement: List<String>,
                             @Expose
                             @SerializedName("chat")
                             val chat: List<String>,
                             @Expose
                             @SerializedName("explorer")
                             val explorer: List<String>,
                             @Expose
                             @SerializedName("source_code")
                             val sourceCode: List<String>) : Parcelable {

    fun buildUrlMap(): MutableMap<String, List<String>> {
        val map = mutableMapOf<String, List<String>>()
        map.put("website", website)
        map.put("technicalDoc", technicalDoc)
        map.put("twitter", twitter)
        map.put("reddit", reddit)
        map.put("explorer", explorer)
        return map
    }


    companion object {
        val EMPTY
            get() = CoinDetailUrlItem(emptyList(),
                    emptyList(),
                    emptyList(),
                    emptyList(),
                    emptyList(),
                    emptyList(),
                    emptyList(),
                    emptyList(),
                    emptyList())
    }
}