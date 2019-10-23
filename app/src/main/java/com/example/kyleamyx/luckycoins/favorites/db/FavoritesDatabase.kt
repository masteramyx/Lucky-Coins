package com.example.kyleamyx.luckycoins.favorites.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kyleamyx.luckycoins.favorites.FavoriteCoin

@Database(entities = [FavoriteCoin::class], version = 1)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}