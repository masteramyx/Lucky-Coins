package com.example.kyleamyx.luckycoins.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.example.kyleamyx.luckycoins.R
import com.example.kyleamyx.luckycoins.api.response.LuckyCoinApiClient
import com.example.kyleamyx.luckycoins.detail.adapter.CoinDetailViewHolder
import com.example.kyleamyx.luckycoins.models.CoinDetailItem
import com.example.kyleamyx.luckycoins.models.CoinListItem
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.coin_detail_controller.view.*

/**
 * Created by kyleamyx on 8/29/18.
 */
class CoinDetailController(private val arg: Bundle) : Controller() {

    private var coin: CoinListItem? = null
    private var compositeDisposable: CompositeDisposable? = null
    private lateinit var detailView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        detailView = inflater.inflate(R.layout.coin_detail_controller, container, false);
        val coin: CoinListItem = arg.getParcelable("coinFavoriteItemItem")!!
        detailView.detailTv.text = "ID = ${coin.id} and Symbol = ${coin.symbol}"

        return detailView
    }


    override fun onAttach(view: View) {
        super.onAttach(view)

        //Make CoinDetail network call
        addDisposable(LuckyCoinApiClient()
                .getCoinDetail(coin?.id!!)
                .compose { upstream: Observable<CoinDetailItem> ->
                    upstream
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                }
                .subscribe({ detailItem ->
                    setDetailItems(detailItem)

                }, {
                    Log.e("CoinDetailController", "Error retrieving coinFavoriteItem detail")
                }))

        Snackbar.make(detailView, "DETAIL", Snackbar.LENGTH_SHORT).show()
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        compositeDisposable?.dispose()
    }


    private fun addDisposable(disposable: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable(disposable)
        } else {
            compositeDisposable!!.add(disposable)
        }
    }

    private fun setDetailItems(coinDetailItem: CoinDetailItem) {
        CoinDetailViewHolder(detailView, coinDetailItem).bindView()
    }

    companion object {
        @JvmStatic
        fun newInstance(args: Bundle): CoinDetailController = CoinDetailController(args).apply {
            coin = args.getParcelable("coinFavoriteItemItem")
        }
    }

}