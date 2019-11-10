package com.example.kyleamyx.luckycoins.favorites

import com.example.kyleamyx.luckycoins.base.BaseViewModel
import com.example.kyleamyx.luckycoins.base.scheduler
import com.example.kyleamyx.luckycoins.base.subscribeBy

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

}