package com.example.kyleamyx.luckycoins.favorites.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.kyleamyx.luckycoins.models.CoinFavoriteItem

@Dao
interface CoinFavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getFavorites(): List<CoinFavoriteItem>


    @Insert
    fun saveCoin(coinFavoriteItem: CoinFavoriteItem)


    @Delete
    fun delete(coinFavoriteItem: CoinFavoriteItem)
}