package com.paperspace.kyleamyx.luckycoins.detail

import com.paperspace.kyleamyx.luckycoins.base.BaseViewModel
import com.paperspace.kyleamyx.luckycoins.base.scheduler
import com.paperspace.kyleamyx.luckycoins.base.subscribeBy

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