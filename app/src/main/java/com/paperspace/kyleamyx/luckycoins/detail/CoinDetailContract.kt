package com.paperspace.kyleamyx.luckycoins.detail

import android.os.Parcelable
import com.paperspace.kyleamyx.luckycoins.base.Mvvm
import com.paperspace.kyleamyx.luckycoins.models.CoinDetailItem
import kotlinx.android.parcel.Parcelize

interface CoinDetailContract {
    sealed class State : Mvvm.State, Parcelable {

        @Parcelize
        data class Data(val coinDetail: CoinDetailItem) : State()

        @Parcelize
        data class Error(val error: Throwable) : State()

    }
}