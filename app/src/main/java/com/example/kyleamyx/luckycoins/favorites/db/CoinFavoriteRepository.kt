package com.example.kyleamyx.luckycoins.favorites.db

import com.example.kyleamyx.luckycoins.models.CoinFavoriteItem
import io.reactivex.Single

interface CoinFavoriteRepository {

    fun saveCoin(coinFavoriteItem: CoinFavoriteItem)

    fun getFavorites(): Single<List<CoinFavoriteItem>>
}