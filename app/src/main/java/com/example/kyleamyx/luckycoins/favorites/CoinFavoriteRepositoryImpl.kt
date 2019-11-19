package com.example.kyleamyx.luckycoins.favorites

import android.util.Log
import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteDao
import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteItem
import io.reactivex.Single

class CoinFavoriteRepositoryImpl(
        val favoritesDao: CoinFavoriteDao
) : CoinFavoriteRepository {


    override fun saveCoin(coinFavoriteItem: CoinFavoriteItem) {
        Log.d("FAVORITES REPOSITORY", "COIN SAVED!!")
        return favoritesDao.saveCoin(coinFavoriteItem)
    }

    override fun getFavorites(): Single<List<CoinFavoriteItem>> {
        Log.d("FAVORITES REPOSITORY", "RETRIEVING FAVORITES LIST")
        return Single.just(favoritesDao.getFavorites())
    }

    override fun deleteCoin(coinFavoriteItem: CoinFavoriteItem): Single<List<CoinFavoriteItem>> {
        Log.d("FAVORITES REPOSITORY", "REMOVING COIN FROM FAVORITES")
        favoritesDao.delete(coinFavoriteItem)
        return getFavorites()
    }

}