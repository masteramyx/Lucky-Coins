package com.paperspace.kyleamyx

import android.app.Application
import android.util.Log
import com.paperspace.kyleamyx.SharedPrefHelper.sharedPrefdi
import com.paperspace.kyleamyx.luckycoins.di.appDi
import com.paperspace.kyleamyx.luckycoins.detail.detailDi
import com.paperspace.kyleamyx.luckycoins.favorites.favoriteDi
import com.paperspace.kyleamyx.luckycoins.list.listDi
import com.paperspace.kyleamyx.luckycoins.di.roomDi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE

class LuckyCoinsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            logger(KoinLogger(Level.DEBUG))
            androidContext(this@LuckyCoinsApplication)
            modules(listOf(appDi,
                    listDi,
                    favoriteDi,
                    detailDi,
                    roomDi,
                    sharedPrefdi))
        }
    }


    class KoinLogger(level: Level) : Logger() {
        override fun log(level: Level, msg: MESSAGE) {
            when (level) {
                Level.DEBUG -> Log.d("KOIN_DEBUG", msg)
                else -> Log.d("KOIN", msg)
            }
        }
    }
}
