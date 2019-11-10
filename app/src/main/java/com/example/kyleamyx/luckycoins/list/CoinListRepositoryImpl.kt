package com.example.kyleamyx.luckycoins.list

import com.example.kyleamyx.luckycoins.api.LuckyCoinApiService
import com.example.kyleamyx.luckycoins.base.scheduler
import com.example.kyleamyx.luckycoins.models.CoinDetailItem
import com.example.kyleamyx.luckycoins.models.CoinListItem
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import me.ameriod.lib.mvp.presenter.rx2.IObservableSchedulerRx2
import org.koin.core.context.GlobalContext.get


class CoinListRepositoryImpl(
        val coinListService: LuckyCoinApiService = get().koin.get()
) : CoinListRepository {

    var tempList = mutableListOf<String>()
    override fun buildCacheList(): Single<List<String>> {
        return coinListService.getCoinListing()
                .singleOrError()
                .map { response ->
                    response.cryptoList.forEach {
                        tempList.add(it.id)
                    }
                    tempList
                }
    }


    override fun getCoinList(): Single<List<CoinListItem>> {
        return Single.zip(getList(), getCoinListImages(tempList), BiFunction { itemList, imageList ->

            imageList.forEach { pair ->
                val match = itemList.find { item ->
                    pair.first == item.id
                }
                match?.logo = pair.second


            }
//            itemList.forEach { item ->
//                val match = imageList.find { imagePair ->
//                    item.id == imagePair.first
//                }
//                item.logo = match?.second ?: ""
//            }
            itemList
        })
    }


    override fun getList(): Single<List<CoinListItem>> {
        return coinListService.getCoinListing()
                .singleOrError()
                .map { response ->
                    response.cryptoList
                }
    }


    //todo -  pass in all the coins id's as query param to get whole list in 1 request
    override fun getCoinListImages(coinIdList: List<String>): Single<List<Pair<String, String>>> {
        val keyList = mutableListOf<Pair<String, String>>()
        val listToString = coinIdList.joinToString(separator = ",")
        // TRANSFORM IT TO "1,2,3,4,5....."
        //coinIdList = "\"$listToString\""
        return coinListService.getCoinListImages(coinIdList = listToString)
                .singleOrError()
                .map { detailResponse ->


                    //                    listOf("1", "1027")
                    coinIdList.forEach {
                        val data = GSON.fromJson((detailResponse.data as JsonObject).get(it),
                                CoinDetailItem::class.java)
                        keyList.add(Pair(data.id, data.logo ?: ""))
                    }

//                    val data  =  GSON.fromJson((detailResponse.data as JsonObject).get("1"),
//                            CoinDetailItem::class.java)
//                    val data = GSON.fromJson<List<CoinDetailItem>>(detailResponse.data, detailType)
//                    data.forEach {
//                        keyList.add(Pair(it.id, it.logo!!))
//                    }
//                    keyList.add(Pair(data.id, data.logo!!))
//                    keyList
                    keyList
                }
        return Single.just(emptyList<Pair<String, String>>())
    }


//    override fun addImagesToList(coinList: List<CoinListItem>): Single<List<CoinListItem>> {
//        val type = object : TypeToken<List<CoinDetailItem>>() {}.type
//        return coinListService.getCoinListImages(coinIdList = coinIdList)
//                .singleOrError()/**/
//                .map { response ->
//                    val data: List<CoinDetailItem> = GSON.fromJson<List<CoinDetailItem>>(response.data, type)
//                    coinList.forEach { listItem ->
//                        data.find { detailItem ->
//                            listItem.id == detailItem.id
//                        }.apply {
//                            listItem.logo = this?.logo!!
//                        }
//                    }
//                    coinList
//                }
//    }

    companion object {

        val detailType = object : TypeToken<List<CoinDetailItem>>() {}.type

        private val GSON = GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
    }
}