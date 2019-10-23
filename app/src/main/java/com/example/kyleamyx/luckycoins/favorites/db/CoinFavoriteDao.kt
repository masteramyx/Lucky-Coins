package com.example.kyleamyx.luckycoins.favorites.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.kyleamyx.luckycoins.favorites.FavoriteCoin

@Dao
interface CoinFavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getFavorites(): List<FavoriteCoin>


    @Insert
    fun saveCoin(coin: FavoriteCoin)


    @Delete
    fun delete(coin: FavoriteCoin)
}