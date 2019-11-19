package com.example.kyleamyx.luckycoins

import androidx.annotation.VisibleForTesting
import com.example.kyleamyx.CoinBaseUrlProvider
import com.example.kyleamyx.IObservableSchedulerRx2
import com.example.kyleamyx.luckycoins.api.LuckyCoinApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appDi = module {


    //Inject Web Service for Repository; OkHttpClient comes from this DI Class
    single<LuckyCoinApiService> {
        buildLuckyCoinsListApiService(okHttpClient = get(), coinBaseUrlProvider = get())
    }

    // Inject Client for entire application
    single<OkHttpClient> {
        val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
                .readTimeout(100, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build()
    }


    factory<IObservableSchedulerRx2> {
        IObservableSchedulerRx2.SUBSCRIBE_IO_OBSERVE_ANDROID_MAIN
    }


    single<Gson> {
        GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
    }

    //Need to provide URL via provider in order to mock the response, or else when mocking the repository out, the
    // service in the repository constructor will point to the actual endpoint and not local resource folder
    single<CoinBaseUrlProvider> {
        object : CoinBaseUrlProvider {
            override fun getBaseUrl(): String {
                return BASE_URL
            }
        }
    }

    //todo - inject room DB, how to do this?
}


@VisibleForTesting
fun buildLuckyCoinsListApiService(okHttpClient: OkHttpClient, coinBaseUrlProvider: CoinBaseUrlProvider) =
        Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl(coinBaseUrlProvider.getBaseUrl())
                .build()
                .create(LuckyCoinApiService::class.java)

private const val BASE_URL = "https://pro-api.coinmarketcap.com/"