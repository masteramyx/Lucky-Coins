package com.example.kyleamyx

import android.app.Application
import android.util.Log
import com.example.kyleamyx.luckycoins.appDi
import com.example.kyleamyx.luckycoins.favorites.favoriteDi
import com.example.kyleamyx.luckycoins.list.listDi
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
            modules(listOf(appDi, listDi, favoriteDi))
        }

        RoomSingleton.initDB(this)
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
