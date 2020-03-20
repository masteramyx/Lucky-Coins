package com.paperspace.kyleamyx.luckycoins.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.valueParameters

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

    //    fun buildUrlMap(): MutableMap<String, List<String>> {
//        val map = mutableMapOf<String, List<String>>()
//        map.put("website", website)
//        map.put("technicalDoc", technical_doc)
//        map.put("twitter", twitter)
//        map.put("reddit", reddit)
////        if(explorer.size > 1){
////            explorer.forEachIndexed { index, url ->
////                when(index){
////                    0 -> {
////                        map.put("explorer", listOf(explorer[index]))
////                    }
////                    else -> map.put("_$index", listOf(explorer[index]))
////                }
////            }
////        }
//        map.put("explorer", explorer)
//
//        return map
//    }

    /**
     * Currently only displaying `website`, `techDoc`, `twitter`, `reddit` & Explorer
     */
    fun buildUrlMap(): MutableMap<String, List<String>> {
        val map = mutableMapOf<String, List<String>>()
        this::class.memberProperties.forEach {
            if (it.name == "website" || it.name == "technical_doc" || it.name == "twitter" ||
                    it.name == "reddit" || it.name == "explorer"){
                when(it.name){
                    "website" -> {
                        website.forEachIndexed { index, url ->
                            if(index == 0){
                                map.put(it.name, listOf(url))
                            }else{
                                map.put("${it.name}_$index", listOf(url))
                            }
                        }
                    }
                    "technical_doc" -> {
                        technical_doc.forEachIndexed { index, url ->
                            if(index == 0){
                                map.put("TechnicalDoc", listOf(url))
                            }else{
                                map.put("TechnicalDoc_$index", listOf(url))
                            }
                        }
                    }
                    "twitter" -> {
                        twitter.forEachIndexed { index, url ->
                            if(index == 0){
                                map.put(it.name, listOf(url))
                            }else{
                                map.put("${it.name}_$index", listOf(url))
                            }
                        }
                    }
                    "reddit" -> {
                        reddit.forEachIndexed { index, url ->
                            if(index == 0){
                                map.put(it.name, listOf(url))
                            }else{
                                map.put("${it.name}_$index", listOf(url))
                            }
                        }
                    }
                    "explorer" -> {
                        explorer.forEachIndexed { index, url ->
                            if(index == 0){
                                map.put(it.name, listOf(url))
                            }else{
                                map.put("${it.name}_$index", listOf(url))
                            }
                        }
                    }
                }
            }
        }
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