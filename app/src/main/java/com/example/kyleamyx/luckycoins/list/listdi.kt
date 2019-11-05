package com.example.kyleamyx.luckycoins.list

import androidx.annotation.VisibleForTesting
import com.example.kyleamyx.luckycoins.api.LuckyCoinApiService
import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteRepository
import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteRepositoryImpl
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val listDi = module {

    //Inject Web Service for Repository; Client comes from Application DI Class
    single<LuckyCoinApiService> {
        buildLuckyCoinsListApiService(okHttpClient = get())
    }

    // Inject List Repository which communicates with the remote database
    single<CoinListRepository> {
        CoinListRepositoryImpl(coinListService = get())
    }


//    // Inject Favorites Repository which communicates with the local DB
//    single<CoinFavoriteRepository> {
//        CoinFavoriteRepositoryImpl()
//    }

    //Inject View Model which communicates the repositoryImpl above and uses the injected scheduler(Application Level) to
    // schedule where it will observe and subscribe to the streams
    viewModel {
        CoinListViewModel(remoteRepository = get(),favoriteRepository = get(), scheduler = get())
    }


}


@VisibleForTesting
fun buildLuckyCoinsListApiService(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl("https://pro-api.coinmarketcap.com/")
                .build()
                .create(LuckyCoinApiService::class.java)
