package com.example.kyleamyx.luckycoins.list

import com.example.kyleamyx.RoomSingleton
import com.example.kyleamyx.luckycoins.api.LuckyCoinApiService
import com.example.kyleamyx.luckycoins.list.db.CoinListDao
import com.example.kyleamyx.luckycoins.list.db.CoinListDbImageItem
import com.example.kyleamyx.luckycoins.models.CoinDetailItem
import com.example.kyleamyx.luckycoins.models.CoinListItem
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import org.koin.core.context.GlobalContext.get


class CoinListRepositoryImpl(
        val coinListService: LuckyCoinApiService = get().koin.get()
) : CoinListRepository {

    private var tempList = mutableListOf<String>()
    private val coinListDao: CoinListDao = RoomSingleton.getInstance().getRoomDb().listDao()

    private val imagePairList = coinListDao.getImagePairs()

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


    override fun buildListWithImages(): Single<List<CoinListItem>> {
        return coinListService.getCoinListing()
                .singleOrError()
                .map { response ->
                    response.cryptoList.forEach {
                        tempList.add(it.id)
                    }
                    tempList
                }.flatMap {
                    getListNoImages()
                }.flatMap { listToAddImage ->
                    if (imagePairList.isNullOrEmpty()) {
                        getCoinListImages(listToAddImage.map { it.id })
                                .map { listKeyPair ->

                                    // Save the Image Key Pair list to local db
                                    coinListDao.saveImagePairs(listKeyPair.map {
                                        CoinListDbImageItem(it.first.toInt(), it.second)

                                    })

                                    listKeyPair.forEach { pair ->
                                        val match = listToAddImage.find { item ->
                                            pair.first == item.id
                                        }
                                        match?.logo = pair.second
                                    }
                                    listToAddImage
                                }
                    }
                    else {
                        imagePairList.forEach { pair ->
                            val match = listToAddImage.find { item ->
                                pair.id.toString() == item.id
                            }
                            match?.logo = pair.logo
                        }
                        Single.just(listToAddImage)
                    }

                }
    }


    override fun getListNoImages(): Single<List<CoinListItem>> {
        return coinListService.getCoinListing()
                .singleOrError()
                .map { response ->
                    response.cryptoList
                }
    }


    override fun getCoinListImages(coinIdList: List<String>): Single<List<Pair<String, String>>> {
        val keyPairList = mutableListOf<Pair<String, String>>()
        // TRANSFORM IT TO "1,2,3,4,5....."
        val listToString = coinIdList.joinToString(separator = ",")
        return coinListService.getCoinListImages(coinIdList = listToString)
                .singleOrError()
                .map { detailResponse ->
                    coinIdList.forEach {
                        val data = GSON.fromJson((detailResponse.data as JsonObject).get(it),
                                CoinDetailItem::class.java)
                        keyPairList.add(Pair(data.id, data.logo ?: ""))
                    }

                    keyPairList
                }
    }


    companion object {
        val detailType = object : TypeToken<List<CoinDetailItem>>() {}.type

        private val GSON = GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
    }
}