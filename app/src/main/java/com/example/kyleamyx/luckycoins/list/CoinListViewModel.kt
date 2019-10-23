package com.example.kyleamyx.luckycoins.list

import android.view.View
import com.example.kyleamyx.luckycoins.base.BaseViewModel
import com.example.kyleamyx.luckycoins.base.subscribeBy
import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteRepository
import com.example.kyleamyx.luckycoins.models.CoinListItem
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import kotlinx.android.synthetic.main.coin_list_controller.view.*
import me.ameriod.lib.mvp.presenter.rx2.IObservableSchedulerRx2
import java.util.concurrent.TimeUnit

data class CoinListViewModel internal constructor(
        val remoteRepository: CoinListRepository,
        val scheduler: IObservableSchedulerRx2
) : BaseViewModel<CoinListContract.State>() {

    private var coinList: List<CoinListItem> = emptyList()
    private var searchList: List<CoinListItem> = emptyList()

    fun getCoinList() {
        addToDisposables(remoteRepository.getCoinList()
                .compose(scheduler.scheduleSingle())
                .subscribeBy(
                        onSuccess = {
                            coinList = it
                            stateSubject.onNext(CoinListContract.State.CoinListReceived(it))
                        },
                        onError = {
                            stateSubject.onNext(CoinListContract.State.Error(it))
                        }
                ))
    }

    fun onCoinClicked(coin: CoinListItem) {
        stateSubject.onNext(CoinListContract.State.CoinItemClicked(coin))
    }

    fun onFavoriteClicked() {

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

    fun searchItems(query: String): List<CoinListItem> {
        return coinList.filter { coin ->
            coin.name?.toLowerCase()!!.contains(query.toLowerCase())
        }
    }
}
