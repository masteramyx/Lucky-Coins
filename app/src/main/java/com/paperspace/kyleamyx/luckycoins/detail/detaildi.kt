package com.paperspace.kyleamyx.luckycoins.detail

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailDi = module {

    single<CoinDetailRepository> {
        CoinDetailRepositoryImpl(luckyCoinApiService = get(), GSON = get())
    }

    viewModel{
        CoinDetailViewModel(detailRepository = get(), scheduler = get())
    }


}