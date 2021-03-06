package com.paperspace.kyleamyx.luckycoins.list

import android.os.Parcelable
import com.karakum.base.Mvvm
import com.paperspace.kyleamyx.luckycoins.favorites.db.CoinFavoriteItem
import com.paperspace.kyleamyx.luckycoins.models.CoinListItem
import kotlinx.android.parcel.Parcelize


/**
 * This Contracts dictates that the CoinListView can be in at most 1 state at a time(via `Sealed class`).
 * These states should represent whenever content on the screen changes for any reason
 */
interface CoinListContract {
    sealed class State : Parcelable, Mvvm.State {

        @Parcelize
        data class CoinListReceived(val coins: List<CoinListItem>) : State()

        @Parcelize
        data class CoinItemClicked(val coin: CoinListItem) : State()

        @Parcelize
        data class FavoriteClicked(val coinFavoriteItem: CoinFavoriteItem) : State()

        @Parcelize
        data class QueryRan(val searchList: List<CoinListItem>): State()

        @Parcelize
        data class Error(val throwable: Throwable) : State()
    }
}