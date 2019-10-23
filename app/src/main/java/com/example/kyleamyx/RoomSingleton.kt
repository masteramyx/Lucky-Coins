package com.example.kyleamyx

import android.content.Context
import androidx.room.Room
import com.example.kyleamyx.luckycoins.favorites.db.FavoritesDatabase

class RoomSingleton {
    lateinit var db: FavoritesDatabase

    @Synchronized
    fun getRoomDb(): FavoritesDatabase {
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
                        FavoritesDatabase::class.java,
                        "favorites").allowMainThreadQueries().build()
            }
        }
    }

}