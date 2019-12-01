package com.paperspace.kyleamyx.luckycoins

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paperspace.kyleamyx.luckycoins.favorites.db.CoinFavoriteDao
import com.paperspace.kyleamyx.luckycoins.list.db.CoinListDao
import com.paperspace.kyleamyx.luckycoins.list.db.CoinListDbImageItem
import com.paperspace.kyleamyx.luckycoins.favorites.db.CoinFavoriteItem

@Database(entities = [CoinFavoriteItem::class, CoinListDbImageItem::class], version = 1)
abstract class CoinDb : RoomDatabase() {
    abstract fun favoritesDao(): CoinFavoriteDao
    abstract fun listDao(): CoinListDao
}