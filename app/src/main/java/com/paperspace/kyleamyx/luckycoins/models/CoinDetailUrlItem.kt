package com.paperspace.kyleamyx.luckycoins.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class CoinDetailUrlItem(
        val website: List<String>,
        val technical_doc: List<String>,
        val twitter: List<String>,
        val reddit: List<String>,
        val message_board: List<String>,
        val announcement: List<String>,
        val chat: List<String>,
        val explorer: List<String>,
        val source_code: List<String>) : Parcelable {

    fun buildUrlMap(): MutableMap<String, List<String>> {
        val map = mutableMapOf<String, List<String>>()
        map.put("website", website)
        map.put("technicalDoc", technical_doc)
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