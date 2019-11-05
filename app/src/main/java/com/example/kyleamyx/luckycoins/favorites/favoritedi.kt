package com.example.kyleamyx.luckycoins.favorites

import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteRepository
import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteDi = module {

    single<CoinFavoriteRepository> {
        CoinFavoriteRepositoryImpl()
    }

    viewModel {
        CoinFavoriteViewModel(repository = get(), scheduler = get())
    }
}