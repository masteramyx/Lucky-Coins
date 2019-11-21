package com.paperspace.kyleamyx.luckycoins.list.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CoinListDao{
    @Query("SELECT * FROM list")
    fun getImagePairs(): List<CoinListDbImageItem>

    @Insert
    fun saveImagePairs(imagePairs: List<CoinListDbImageItem>)
}