package com.paperspace.kyleamyx.luckycoins.list

import android.util.Log
import android.view.View
import com.paperspace.kyleamyx.luckycoins.favorites.CoinFavoriteRepository
import com.paperspace.kyleamyx.luckycoins.favorites.db.CoinFavoriteItem
import com.paperspace.kyleamyx.luckycoins.models.CoinListItem
import com.jakewharton.rxbinding2.widget.RxTextView
import com.karakum.base.BaseViewModel
import com.karakum.base.scheduler
import com.karakum.base.subscribeBy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import kotlinx.android.synthetic.main.coin_list_controller.view.*
import java.util.*
import java.util.concurrent.TimeUnit

data class CoinListViewModel internal constructor(
        val remoteRepository: CoinListRepository,
        val favoriteRepository: CoinFavoriteRepository,
        val scheduler: scheduler
) : BaseViewModel<CoinListContract.State>() {

    private var coinList: List<CoinListItem> = emptyList()
    private var searchList: List<CoinListItem> = emptyList()


    fun getCoinList(loadingView: View) {
        loadingView.visibility = View.VISIBLE
        addToDisposables(remoteRepository.buildListWithImages()
                .compose(scheduler.scheduleSingle())
                .doOnError { Log.d("LISTVIEWMODEL", "COIN LIST ERROR") }
                .subscribeBy(
                        onSuccess = {
                            loadingView.visibility = View.GONE
                            coinList = it
                            stateSubject.onNext(CoinListContract.State.CoinListReceived(coinList))
                        },
                        onError = {
                            loadingView.visibility = View.GONE
                            stateSubject.onNext(CoinListContract.State.Error(it))
                        }
                ))
    }

    fun onCoinClicked(coin: CoinListItem) {
        stateSubject.onNext(CoinListContract.State.CoinItemClicked(coin))
    }

    fun onFavoriteClicked(coin: CoinFavoriteItem) {
        favoriteRepository.saveCoin(coin)
        stateSubject.onNext(CoinListContract.State.FavoriteClicked(coin))
    }

    fun setSearchListener(view: View) {
        addToDisposables(RxTextView.afterTextChangeEvents(view.searchView)
                .map(Function<com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent, String>() {
                    it.editable()
                            .toString()
                            .trim()
                })
                .debounce(500.toLong(), TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ query ->
                    searchList = searchItems(query)
                    stateSubject.onNext(CoinListContract.State.QueryRan(searchList))
                }, { throwable ->
                    stateSubject.onNext(CoinListContract.State.Error(throwable))
                }))
    }

    private fun searchItems(query: String): List<CoinListItem> {
        return coinList.filter { coin ->
            coin.name.toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault()))
        }
    }
}
