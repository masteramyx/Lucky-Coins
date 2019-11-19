package com.example.kyleamyx.luckycoins.list

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val listDi = module {


    // Inject List Repository which communicates with the remote database
    single<CoinListRepository> {
        CoinListRepositoryImpl(coinListService = get(), coinListDao = get())
    }

    //Inject View Model which communicates the repositoryImpl above and uses the injected scheduler(Application Level) to
    // schedule where it will observe and subscribe to the streams
    viewModel {
        CoinListViewModel(remoteRepository = get(), favoriteRepository = get(), scheduler = get())
    }


}
