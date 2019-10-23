package com.example.kyleamyx.luckycoins.list

import android.os.Parcelable
import com.example.kyleamyx.luckycoins.base.Mvvm
import com.example.kyleamyx.luckycoins.favorites.FavoriteCoin
import com.example.kyleamyx.luckycoins.models.CoinListItem
import kotlinx.android.parcel.Parcelize


/**
 * This Contracts dictates that the CoinListView can be in at most 1 state at a time(via `Sealed class`).
 * These states should represent whenever the screen changes for any reason
 */
interface CoinListContract {
    sealed class State : Parcelable, Mvvm.State {

        @Parcelize
        data class CoinListReceived(val coins: List<CoinListItem>) : State()

        @Parcelize
        data class CoinItemClicked(val coin: CoinListItem) : State()

        @Parcelize
        data class FavoriteClicked(val coin: FavoriteCoin) : State()

        @Parcelize
        data class QueryRan(val searchList: List<CoinListItem>): State()

        @Parcelize
        data class Error(val throwable: Throwable) : State()
    }
}