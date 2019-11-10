package com.example.kyleamyx.luckycoins.favorites

import android.util.Log
import com.example.kyleamyx.RoomSingleton
import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteDao
import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteItem
import io.reactivex.Single

class CoinFavoriteRepositoryImpl : CoinFavoriteRepository {

    private val favoritesDao: CoinFavoriteDao = RoomSingleton.getInstance().getRoomDb().favoritesDao()

    override fun saveCoin(coinFavoriteItem: CoinFavoriteItem) {
        Log.d("FAVORITES REPOSITORY", "COIN SAVED!!")
        return favoritesDao.saveCoin(coinFavoriteItem)
    }

    override fun getFavorites(): Single<List<CoinFavoriteItem>> {
        Log.d("FAVORITES REPOSITORY", "RETRIEVING FAVORITES LIST")
        return Single.just(favoritesDao.getFavorites())
    }

}