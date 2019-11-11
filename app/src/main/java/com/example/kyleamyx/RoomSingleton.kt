package com.example.kyleamyx

import android.content.Context
import androidx.room.Room
import com.example.kyleamyx.luckycoins.CoinDb

class RoomSingleton {
    lateinit var db: CoinDb

    @Synchronized
    fun getRoomDb(): CoinDb {
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
                        CoinDb::class.java,
                        "favorites").allowMainThreadQueries().build()
            }
        }
    }

}