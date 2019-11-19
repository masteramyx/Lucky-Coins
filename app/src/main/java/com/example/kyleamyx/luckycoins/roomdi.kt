package com.example.kyleamyx.luckycoins

import androidx.room.Room
import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteDao
import com.example.kyleamyx.luckycoins.list.db.CoinListDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomDi = module {

    /**
     * Inject Database
     */
    single<CoinDb> {
        Room.databaseBuilder(
                androidContext(),
                CoinDb::class.java,
                "favorites").allowMainThreadQueries().build()
    }


    /**
     * Inject the DAO's needed
     */

    single<CoinListDao> {
        get<CoinDb>().listDao()
    }

    single<CoinFavoriteDao> {
        get<CoinDb>().favoritesDao()
    }
}