package com.example.kyleamyx.luckycoins.favorites

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