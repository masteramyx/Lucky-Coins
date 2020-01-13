package com.paperspace.kyleamyx.luckycoins.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.karakum.base.BaseMvvmController
import com.karakum.base.Mvvm
import com.paperspace.kyleamyx.luckycoins.R
import com.paperspace.kyleamyx.luckycoins.detail.adapter.CoinDetailBottomSheetAdapter
import com.paperspace.kyleamyx.luckycoins.detail.adapter.CoinDetailViewHolder
import com.paperspace.kyleamyx.luckycoins.models.CoinDetailItem
import com.paperspace.kyleamyx.luckycoins.models.CoinDetailUrlItem
import kotlinx.android.synthetic.main.coin_detail_controller.view.*
import org.koin.core.context.GlobalContext.get

/**
 * Created by kyleamyx on 8/29/18.
 */
class CoinDetailController :
        BaseMvvmController<CoinDetailViewModel, CoinDetailContract.State>(), View.OnClickListener {

    override val viewModel: CoinDetailViewModel = get().koin.get()

    private var coinId: String? = null
    private lateinit var detailView: View
    private lateinit var bottomSheet: BottomSheetBehavior<NestedScrollView>

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        CoinDetailBottomSheetAdapter(requireNotNull(activity))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        detailView = inflater.inflate(R.layout.coin_detail_controller, container, false).apply {
            detailUrlBtn.setOnClickListener(this@CoinDetailController)
            bottomSheet = BottomSheetBehavior.from(detailBottomSheet)
            bottomSheet.isHideable = true
            bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
            detailUrlRecycler.apply {
                layoutManager = LinearLayoutManager(activity)
                addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
                adapter = this@CoinDetailController.adapter
                isNestedScrollingEnabled = false
            }
        }
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
                setBottomSheetItems(state.coinDetail.urls ?: CoinDetailUrlItem.EMPTY)
            }
            is CoinDetailContract.State.Error -> {
                Log.d("OnStateChange", state.error.localizedMessage!!)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            view?.detailUrlBtn -> {
                bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    private fun setDetailItems(coinDetailItem: CoinDetailItem) {
        CoinDetailViewHolder(detailView, coinDetailItem).bindView()
    }

    private fun setBottomSheetItems(urls: CoinDetailUrlItem) {
        adapter.addItems(urls.buildUrlMap())
    }

    companion object {
        @JvmStatic
        fun newInstance(args: Bundle): CoinDetailController = CoinDetailController().apply {
            coinId = args.getString("coinFavoriteItemItem")
        }
    }

}