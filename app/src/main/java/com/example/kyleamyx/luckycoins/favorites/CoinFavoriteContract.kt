package com.example.kyleamyx.luckycoins.favorites

import android.os.Parcelable
import com.example.kyleamyx.luckycoins.base.Mvvm
import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteItem
import kotlinx.android.parcel.Parcelize

interface CoinFavoriteContract {

    sealed class State : Mvvm.State, Parcelable {

        @Parcelize
        data class FavoritesReceived(val favoriteCoins: List<CoinFavoriteItem>) : State()

        @Parcelize
        data class FavoriteDeleted(val favoriteCoins: List<CoinFavoriteItem>) : State()

        @Parcelize
        data class Error(val throwable: Throwable) : State()
    }
}