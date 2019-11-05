package com.example.kyleamyx.luckycoins.favorites.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kyleamyx.luckycoins.models.CoinFavoriteItem

@Database(entities = [CoinFavoriteItem::class], version = 1)
abstract class CoinFavoriteDb : RoomDatabase() {
    abstract fun favoritesDao(): CoinFavoriteDao
}