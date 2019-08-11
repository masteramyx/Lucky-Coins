package com.example.kyleamyx.luckycoins.list

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.bluelinelabs.conductor.Controller
import com.example.kyleamyx.luckycoins.R
import com.example.kyleamyx.luckycoins.api.response.LuckyCoinApiClient
import com.example.kyleamyx.luckycoins.list.adapter.CoinListAdapter

import com.example.kyleamyx.luckycoins.models.CoinListItem
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.coin_list_controller.view.*
import java.util.concurrent.TimeUnit

/**
 * Created by kyleamyx on 6/23/18.
 */
class CoinListController : Controller(), CoinListAdapter.CoinListListener {

    var list: List<CoinListItem> = emptyList()
    var searchList: List<CoinListItem> = emptyList()
    var compositeDisposable: CompositeDisposable? = null
    var recyclerView: RecyclerView? = null
    var searchDisposable: Disposable? = null

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        CoinListAdapter(applicationContext!!, this)
    }

    interface OnCoinClicked {
        fun onItemClicked(coin: CoinListItem)
    }

    override fun onCoinClicked(coin: CoinListItem) {
        listener?.onItemClicked(coin)
    }

    var listener: OnCoinClicked? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.coin_list_controller, container, false)
        recyclerView = view.findViewById(R.id.listRecycler)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        recyclerView!!.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        recyclerView!!.adapter = adapter
        recyclerView!!.isNestedScrollingEnabled = false

        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        return view
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        listener = activity as OnCoinClicked


        setViewVisibility(view.progressBar, true, View.GONE)


        //Make network call to receive coin items...this call doesn't contain link to Currency symbol
        addDisposable(LuckyCoinApiClient()
                .getCoins()
                .take(100)
                .compose { upstream: Observable<List<CoinListItem>> ->
                    upstream
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                }
                .subscribe({ response ->
                    list = response
                    adapter.addItems(list)
                    setViewVisibility(view.progressBar, false, View.GONE)
                },
                        { _ ->
                            Log.e("CoinListController", "Error retrieving list!!")
                        }))




        searchDisposable = RxTextView.afterTextChangeEvents(view.searchView).map { t: TextViewAfterTextChangeEvent? ->
            t?.editable()
                    .toString()
                    .trim()
        }
                .debounce(500.toLong(), TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ query ->
                    searchList = searchItems(query).blockingFirst()
                    adapter.addItems(searchList)
                }, { throwable ->
                    Log.e("CoinListController", "Error search crypto list")
                })

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
        if (!show) {
            view.visibility = View.GONE
        } else {
            view.visibility = View.VISIBLE
        }
    }

    fun searchItems(query: String): Observable<List<CoinListItem>> {
        return Observable.just(list).map { list ->
            list.filter { coin ->
                coin.name!!.toLowerCase().contains(query.toLowerCase())
            }
        }
    }
}
