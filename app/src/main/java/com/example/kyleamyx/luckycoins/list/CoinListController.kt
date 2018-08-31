package com.example.kyleamyx.luckycoins.list

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.example.kyleamyx.luckycoins.R
import com.example.kyleamyx.luckycoins.api.response.LuckyCoinApiClient
import com.example.kyleamyx.luckycoins.list.adapter.CoinListAdapter
import com.example.kyleamyx.luckycoins.models.CoinListItem
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by kyleamyx on 6/23/18.
 */
class CoinListController : Controller(), View.OnClickListener {

    var list: List<CoinListItem> = emptyList()
    var compositeDisposable: CompositeDisposable? = null
    var recyclerView: RecyclerView? = null

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        CoinListAdapter(applicationContext!!)
    }

    interface OnCoinClicked {
        fun onItemClicked(coin: CoinListItem)
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    var listener: OnCoinClicked? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.coin_list_controller, container, false)
        recyclerView = view.findViewById(R.id.listRecycler)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)


        addDisposable(LuckyCoinApiClient()
                .getCoins()
                .compose(ObservableTransformer { upstream: Observable<List<CoinListItem>> ->
                    upstream
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                })
                .subscribe({ response ->
                    list = response
                    adapter.addItems(list)
                    setViewVisibility(view.findViewById(R.id.progressBar), false, 1)
                },
                        { _ ->
                            Log.e("CoinListController", "Error retrieving list!!")
                        }))

        return view
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        listener = activity as OnCoinClicked
        recyclerView!!.adapter = adapter

//        addDisposable(LuckyCoinApiClient()
//                .getCoins()
//                .compose(ObservableTransformer { upstream: Observable<List<CoinListItem>> ->
//                    upstream
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                })
//                .subscribe({ response ->
//                    list = response
//                    adapter.addItems(list)
//                },
//                        { _ ->
//                            Log.e("CoinListController", "Error retrieving list!!")
//                        }))

        //setViewVisibility(R.layout.activity_coin_list, false, 1)
    }


    override fun onDetach(view: View) {
        super.onDetach(view)
        listener = null

        //Get rid of any living disposables
        compositeDisposable = null
    }


    companion object {
        @JvmStatic
        fun newInstance(): CoinListController = CoinListController()
    }


    fun addDisposable(disposable: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable(disposable)
        } else {
            compositeDisposable!!.add(disposable)
        }
    }

    fun setViewVisibility(view: View?, show: Boolean, goneType: Int) {
        if (view == null) {
            return
        }
        view.visibility = View.GONE
    }


}