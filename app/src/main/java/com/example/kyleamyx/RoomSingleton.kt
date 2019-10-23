package com.example.kyleamyx

import android.content.Context
import androidx.room.Room
import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteDb

class RoomSingleton {
    lateinit var db: CoinFavoriteDb

    @Synchronized
    fun getRoomDb(): CoinFavoriteDb {
        return db
    }

    companion object {
        lateinit var sOurInstance: RoomSingleton

        fun getInstance(): RoomSingleton {
            return sOurInstance
        }


        // DB is linked to the singleton
        fun initDB(context: Context) {
            sOurInstance = RoomSingleton()
            with(sOurInstance) {
                this.db = Room.databaseBuilder(
                        context,
                        CoinFavoriteDb::class.java,
                        "favorites").allowMainThreadQueries().build()
            }
        }
    }

}