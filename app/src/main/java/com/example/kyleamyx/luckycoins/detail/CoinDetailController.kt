package com.example.kyleamyx.luckycoins.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kyleamyx.luckycoins.R
import com.example.kyleamyx.luckycoins.base.BaseMvvmController
import com.example.kyleamyx.luckycoins.base.Mvvm
import com.example.kyleamyx.luckycoins.detail.adapter.CoinDetailViewHolder
import com.example.kyleamyx.luckycoins.models.CoinDetailItem
import com.example.kyleamyx.luckycoins.models.CoinListItem
import org.koin.core.context.GlobalContext.get

/**
 * Created by kyleamyx on 8/29/18.
 */
class CoinDetailController :
        BaseMvvmController<CoinDetailViewModel, CoinDetailContract.State>() {


    override val viewModel: CoinDetailViewModel = get().koin.get()

    private var coinId: String? = null
    private lateinit var detailView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        detailView = inflater.inflate(R.layout.coin_detail_controller, container, false)
        return detailView
    }


    override fun onAttach(view: View) {
        super.onAttach(view)
        //todo- fix this null shit
        viewModel.getCoinDetail(coinId!!)
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        disposeDisposables()
    }

    override fun onStateChange(state: Mvvm.State) {
        when (state) {
            is CoinDetailContract.State.Data -> {
                Log.d("DETAIL-STATE", "state changed-data received")
                setDetailItems(state.coinDetail)
            }
            is CoinDetailContract.State.Error -> {
                Log.d("OnStateChange", state.error.localizedMessage!!)
            }
        }
    }


    private fun setDetailItems(coinDetailItem: CoinDetailItem) {
        CoinDetailViewHolder(detailView, coinDetailItem).bindView()
    }

    companion object {
        @JvmStatic
        fun newInstance(args: Bundle): CoinDetailController = CoinDetailController().apply {
            coinId = args.getString("coinFavoriteItemItem")
        }
    }

}