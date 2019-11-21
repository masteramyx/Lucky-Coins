package com.paperspace.kyleamyx.luckycoins

import androidx.room.Room
import com.paperspace.kyleamyx.luckycoins.favorites.db.CoinFavoriteDao
import com.paperspace.kyleamyx.luckycoins.list.db.CoinListDao
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