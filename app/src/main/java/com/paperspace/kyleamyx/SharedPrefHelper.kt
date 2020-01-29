package com.paperspace.kyleamyx

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object SharedPrefHelper {
    //Create a shared pref object for the time frame settings
    //on each apply btn click, update the settings and then refresh the data list and in the viewholder,
    // pull that saved preference to update the arrow direction.

    interface SharedPref {
        fun setCoinPercentChangeTimePreference(preference: String): Boolean

        fun getCoinPercentChangeTimePreference(): String
    }


    const val ONE_HOUR = "1h"
    const val ONE_DAY = "24h"
    const val ONE_WEEK = "7d"

    val sharedPrefdi = module {
        single<SharedPref> {
            val pref = androidContext().getSharedPreferences("coin_time_pref", Context.MODE_PRIVATE)
            object : SharedPref {
                override fun setCoinPercentChangeTimePreference(preference: String): Boolean {
                    val editor = pref.edit()
                    editor.putString("coin_time_str", preference)
                    editor.apply()
                    return true
                }

                override fun getCoinPercentChangeTimePreference(): String {
                    return requireNotNull(pref.getString("coin_time_str", "1h"))
                }
            }
        }
    }
}

