package com.paperspace.kyleamyx.luckycoins.favorites

import android.util.Log
import com.paperspace.kyleamyx.luckycoins.base.BaseViewModel
import com.paperspace.kyleamyx.luckycoins.base.scheduler
import com.paperspace.kyleamyx.luckycoins.base.subscribeBy
import com.paperspace.kyleamyx.luckycoins.favorites.db.CoinFavoriteItem

class CoinFavoriteViewModel(
        val repository: CoinFavoriteRepository,
        val scheduler: scheduler
) : BaseViewModel<CoinFavoriteContract.State>() {

    fun getFavorites() {
        addToDisposables(repository.getFavorites()
                .compose(scheduler.scheduleSingle())
                .subscribeBy(
                        onSuccess = {
                            stateSubject.onNext(CoinFavoriteContract.State.FavoritesReceived(it))
                        },
                        onError = {
                            stateSubject.onNext(CoinFavoriteContract.State.Error(it))
                        }
                ))
    }

    fun removeFavorite(coinFavoriteItem: CoinFavoriteItem) {
        addToDisposables(repository.deleteCoin(coinFavoriteItem)
                .compose(scheduler.scheduleSingle())
                .subscribeBy(
                        onSuccess = {
                            Log.d("FavoriteViewModel-removeFavorite", "list updated")
                            stateSubject.onNext(CoinFavoriteContract.State.FavoriteDeleted(it))
                        },
                        onError = {
                            stateSubject.onNext(CoinFavoriteContract.State.Error(it))
                        }
                ))
    }

}