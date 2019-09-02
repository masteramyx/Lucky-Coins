package com.example.kyleamyx.luckycoins

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.Futures
import com.uber.simplestore.ScopeConfig
import com.uber.simplestore.SimpleStore
import com.uber.simplestore.impl.SimpleStoreFactory
import java.util.concurrent.Executors

object StorageUtils {

    fun storeFavorite(context: Context, name: String, id: String): Boolean {

        val pref = context.getSharedPreferences("Favorites", Context.MODE_PRIVATE)
        val editor = pref.edit()

        val coinFavorites = pref.getStringSet("Coins", mutableSetOf())

        if (coinFavorites!!.contains(id)) {

        } else {
            coinFavorites.add(id)
        }

        editor.putStringSet("Coins", coinFavorites)

        editor.apply()

//        val simpleStore: SimpleStore = SimpleStoreFactory.create(context, "luckyCoins/favorites", ScopeConfig.DEFAULT)
//        val put = simpleStore.putString(name, id)
//        Futures.addCallback(
//                put,
//                object : FutureCallback<String> {
//                    override fun onSuccess(result: String?) {
//                        Log.d("StorageUtils", "SAVED COIN W/ ID $result")
//                        simpleStore.close()
//                    }
//
//                    override fun onFailure(t: Throwable) {
//                        Toast.makeText(context, "SAVE FAILED", Toast.LENGTH_SHORT).show()
//                    }
//                },
//                Executors.newFixedThreadPool(10)
//        )
//
        return true
    }


    fun isItemAFavorite(id: String, context: Context): Boolean {
        val pref = context.getSharedPreferences("Favorites", Context.MODE_PRIVATE)

        val coinFavorites = pref.getStringSet("Coins", mutableSetOf())

        return (coinFavorites!!.contains(id))
    }
}