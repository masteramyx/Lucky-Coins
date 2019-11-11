package com.example.kyleamyx.luckycoins

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteDao
import com.example.kyleamyx.luckycoins.list.db.CoinListDao
import com.example.kyleamyx.luckycoins.list.db.CoinListDbImageItem
import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteItem

@Database(entities = [CoinFavoriteItem::class, CoinListDbImageItem::class], version = 2)
abstract class CoinDb : RoomDatabase() {
    abstract fun favoritesDao(): CoinFavoriteDao
    abstract fun listDao(): CoinListDao
}