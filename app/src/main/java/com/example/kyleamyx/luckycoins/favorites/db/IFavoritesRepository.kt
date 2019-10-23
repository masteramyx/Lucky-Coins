package com.example.kyleamyx.luckycoins.favorites.db

import com.example.kyleamyx.luckycoins.favorites.FavoriteCoin

interface IFavoritesRepository {

    fun saveCoin(coin: FavoriteCoin)

    fun getFavorites(): List<FavoriteCoin>
}