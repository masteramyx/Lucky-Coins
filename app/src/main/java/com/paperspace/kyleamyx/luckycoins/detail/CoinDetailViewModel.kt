package com.paperspace.kyleamyx.luckycoins.detail

import android.view.View
import com.karakum.base.BaseViewModel
import com.karakum.base.scheduler
import com.karakum.base.subscribeBy
import com.paperspace.kyleamyx.luckycoins.models.CoinDetailItem

data class CoinDetailViewModel internal constructor(
        val detailRepository: CoinDetailRepository,
        val scheduler: scheduler
) : BaseViewModel<CoinDetailContract.State>() {

    fun getCoinDetail(coinId: String, loading: View) {
        loading.visibility = View.VISIBLE
        detailRepository.getCoinDetail(coinId)
                .compose(scheduler.scheduleSingle())
                .subscribeBy(
                        onSuccess = {
                            loading.visibility = View.GONE
                            stateSubject.onNext(CoinDetailContract.State.Data(it ?: CoinDetailItem.EMPTY))
                        },
                        onError = {
                            loading.visibility = View.GONE
                            stateSubject.onError(it)
                        }
                )
    }
}