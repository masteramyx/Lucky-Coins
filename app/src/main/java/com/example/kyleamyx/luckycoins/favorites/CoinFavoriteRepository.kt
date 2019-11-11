package com.example.kyleamyx.luckycoins.favorites

import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteItem
import io.reactivex.Single

interface CoinFavoriteRepository {

    fun saveCoin(coinFavoriteItem: CoinFavoriteItem)

    fun getFavorites(): Single<List<CoinFavoriteItem>>

    fun deleteCoin(coinFavoriteItem: CoinFavoriteItem): Single<List<CoinFavoriteItem>>
}