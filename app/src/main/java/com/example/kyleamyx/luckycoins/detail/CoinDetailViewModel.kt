package com.example.kyleamyx.luckycoins.detail

import com.example.kyleamyx.luckycoins.base.BaseViewModel
import com.example.kyleamyx.luckycoins.base.scheduler
import com.example.kyleamyx.luckycoins.base.subscribeBy

data class CoinDetailViewModel internal constructor(
        val detailRepository: CoinDetailRepository,
        val scheduler: scheduler
) : BaseViewModel<CoinDetailContract.State>() {

    fun getCoinDetail(coinId: String) {
        detailRepository.getCoinDetail(coinId)
                .compose(scheduler.scheduleSingle())
                .subscribeBy(
                        onSuccess = {
                            stateSubject.onNext(CoinDetailContract.State.Data(it))
                        },
                        onError = {
                            stateSubject.onError(it)
                        }
                )
    }
}