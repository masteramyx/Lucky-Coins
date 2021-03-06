package com.paperspace.kyleamyx.luckycoins.list

import com.paperspace.kyleamyx.luckycoins.api.LuckyCoinApiService
import com.paperspace.kyleamyx.luckycoins.list.db.CoinListDao
import com.paperspace.kyleamyx.luckycoins.list.db.CoinListDbImageItem
import com.paperspace.kyleamyx.luckycoins.models.CoinListItem
import io.reactivex.Single


class CoinListRepositoryImpl(
        val coinListService: LuckyCoinApiService,
        private val coinListDao: CoinListDao
) : CoinListRepository {

    private var coinIdList = mutableListOf<String>()

    private val imageAndIdPairList = coinListDao.getImagePairs()


    //TODO(clean up the rx chain?)
    override fun buildListWithImages(): Single<List<CoinListItem>> {
        return coinListService.getCoinListing()
                .singleOrError()
                .map { response ->
                    response.data.forEach {
                        coinIdList.add(it.id)
                    }
                    response.data
                }.flatMap { list ->
                    Single.just(list)
                }.flatMap { listToAddImage ->
                    if (imageAndIdPairList.isNullOrEmpty()) {
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
                    } else {
                        imageAndIdPairList.forEach { pair ->
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
                    response.data
                }
    }


    override fun getCoinListImages(coinIdList: List<String>): Single<List<Pair<String, String>>> {
        val keyPairList = mutableListOf<Pair<String, String>>()
        // TRANSFORM IT TO "1,2,3,4,5....."
        val listToString = coinIdList.joinToString(separator = ",")
        return coinListService.getCoinListImages(coinIdList = listToString)
                .singleOrError()
                .map { detailResponse ->
                    detailResponse.data.toList().map {
                        it.second.apply {
                            keyPairList.add(Pair(this.id, this.logo ?: ""))
                        }
                    }
                    keyPairList
                }
    }
}
