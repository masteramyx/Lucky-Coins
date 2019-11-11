package com.example.kyleamyx.luckycoins

import me.ameriod.lib.mvp.presenter.rx2.IObservableSchedulerRx2
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val appDi = module {


    // Inject Client for entire application
    single<OkHttpClient> {
        val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
                .readTimeout(100, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build()
    }


    factory<IObservableSchedulerRx2>{
        IObservableSchedulerRx2.SUBSCRIBE_IO_OBSERVE_ANDROID_MAIN
    }

    //todo - inject room DB, how to do this?
}