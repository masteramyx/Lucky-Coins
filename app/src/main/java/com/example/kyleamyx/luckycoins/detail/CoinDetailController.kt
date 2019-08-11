package com.example.kyleamyx.luckycoins.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bluelinelabs.conductor.Controller
import com.example.kyleamyx.luckycoins.R
import com.example.kyleamyx.luckycoins.api.response.LuckyCoinApiClient
import com.example.kyleamyx.luckycoins.detail.adapter.CoinDetailViewHolder
import com.example.kyleamyx.luckycoins.models.CoinDetailItem
import com.example.kyleamyx.luckycoins.models.CoinListItem
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
    //private var detailView : View? = null
    private lateinit var detailView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        detailView = inflater.inflate(R.layout.coin_detail_controller, container, false);
        val coin: CoinListItem = arg.getParcelable("coinItem")
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
                    Toast.makeText(applicationContext, "Detail: ${detailItem.description}", Toast
                            .LENGTH_LONG).show()

                }, {
                    Log.e("CoinDetailController", "Error retrieving coin detail")
                }))
    }


    fun addDisposable(disposable: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable(disposable)
        } else {
            compositeDisposable!!.add(disposable)
        }
    }

    fun setDetailItems(coinDetailItem: CoinDetailItem) {
        CoinDetailViewHolder(detailView, coinDetailItem).bindView()
    }

    companion object {
        @JvmStatic
        fun newInstance(args: Bundle): CoinDetailController = CoinDetailController(args).apply {
            coin = args.getParcelable("coinItem")
        }
    }

}