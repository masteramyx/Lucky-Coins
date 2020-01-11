package com.paperspace.kyleamyx.luckycoins

import androidx.annotation.VisibleForTesting
import com.paperspace.kyleamyx.CoinBaseUrlProvider
import com.paperspace.kyleamyx.luckycoins.api.LuckyCoinApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.karakum.IObservableSchedulerRx2
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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


    //Need to provide URL via provider in order to mock the response, or else when mocking the repository out, the
    // service in the repository constructor will point to the actual endpoint and not local resource folder
    single<CoinBaseUrlProvider> {
        object : CoinBaseUrlProvider {
            override fun getBaseUrl(): String {
                return BASE_URL
            }
        }
    }

    //TODO(inject room DB, how to do this?)
}


@VisibleForTesting
fun buildLuckyCoinsListApiService(okHttpClient: OkHttpClient,
                                  coinBaseUrlProvider: CoinBaseUrlProvider): LuckyCoinApiService =
        Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //Set [strictMode = false] to escape unmapped json keys and avoid `UNKNOWN KEY` error
                .addConverterFactory(Json(JsonConfiguration(strictMode = false)).asConverterFactory(MediaType.get
                (MEDIA_TYPE_JSON)))
                .client(okHttpClient)
                .baseUrl(coinBaseUrlProvider.getBaseUrl())
                .build()
                .create(LuckyCoinApiService::class.java)

private const val BASE_URL = "https://pro-api.coinmarketcap.com/"
private const val MEDIA_TYPE_JSON = "application/json"