package com.example.kyleamyx.luckycoins.favorites.db

import android.util.Log
import com.example.kyleamyx.RoomSingleton
import com.example.kyleamyx.luckycoins.favorites.FavoriteCoin

class FavoritesRepository : IFavoritesRepository {

    private val favoritesDao: FavoritesDao

    init {
        favoritesDao = RoomSingleton.getInstance().getRoomDb().favoritesDao()
    }

    override fun saveCoin(coin: FavoriteCoin) {
        Log.d("FAVORITES REPOSITORY", "COIN SAVED!!")
        return favoritesDao.saveCoin(coin)
    }

    override fun getFavorites(): List<FavoriteCoin> {
        Log.d("FAVORITES REPOSITORY", "RETRIEVING FAVORITES LIST")
        return favoritesDao.getFavorites()
    }

}