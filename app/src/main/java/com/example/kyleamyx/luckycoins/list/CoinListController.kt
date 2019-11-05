package com.example.kyleamyx.luckycoins.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bluelinelabs.conductor.RouterTransaction
import com.example.kyleamyx.luckycoins.CoinListActivity
import com.example.kyleamyx.luckycoins.R
import com.example.kyleamyx.luckycoins.base.BaseMvvmController
import com.example.kyleamyx.luckycoins.base.Mvvm
import com.example.kyleamyx.luckycoins.detail.CoinDetailActivity
import com.example.kyleamyx.luckycoins.detail.CoinDetailController
import com.example.kyleamyx.luckycoins.models.CoinFavoriteItem
import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteRepository
import com.example.kyleamyx.luckycoins.list.adapter.CoinListAdapter
import com.example.kyleamyx.luckycoins.models.CoinListItem
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.coin_list_controller.view.*
import org.koin.core.context.GlobalContext.get

/**
 * Created by kyleamyx on 6/23/18.1
 */

//todo- add shimmer by facebook for loading of views
class CoinListController : BaseMvvmController<CoinListViewModel, CoinListContract.State>(), CoinListAdapter
.CoinListListener {


    var list: List<CoinListItem> = emptyList()
    var recyclerView: RecyclerView? = null

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        CoinListAdapter(applicationContext!!, this)
    }

    lateinit var listener: CoinListAdapter.CoinListListener


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.coin_list_controller, container, false)
        recyclerView = view.findViewById(R.id.listRecycler)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        recyclerView!!.addItemDecoration(DividerItemDecoration(activity,
                DividerItemDecoration.VERTICAL))
        recyclerView!!.adapter = adapter
        recyclerView!!.isNestedScrollingEnabled = false
        view.swipeContainer.apply {
            this.setOnRefreshListener {
                viewModel.getCoinList()
                this.isRefreshing = false
            }
        }
        return view
    }


    override fun onAttach(view: View) {
        super.onAttach(view)
        with(viewModel) {
            if (list.isEmpty()) {
                getCoinList()
                setSearchListener(view)
            } else {
                view.listRecycler.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
        listener = activity as CoinListActivity
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        view.listRecycler.adapter = null
        println("Detached")
    }


    override fun onCoinClicked(coin: CoinListItem) {
        viewModel.onCoinClicked(coin)
    }

    override fun onFavoriteClicked(coinFavoriteItem: CoinFavoriteItem) {
        viewModel.onFavoriteClicked(coinFavoriteItem)
        adapter.notifyDataSetChanged()
    }

    override val viewModel: CoinListViewModel = get().koin.get()

    override fun onStateChange(state: Mvvm.State) {
        when (state) {
            is CoinListContract.State.CoinListReceived -> {
                list = state.coins
                adapter.addItems(list)
            }
            is CoinListContract.State.CoinItemClicked -> {
                Snackbar.make(view!!, "Coin Item Clicked", Snackbar.LENGTH_SHORT)
                //router.onActivityStarted()
//                router?.pushController(RouterTransaction.with(CoinDetailController.newInstance
//                (Bundle().apply {
//                    putParcelable("coinFavoriteItemItem", state.coin)

//                })))
                //startActivity(CoinDetailActivity.getLaunchIntent(activity!!, state.coin))
                listener.onCoinClicked(state.coin)
            }
            is CoinListContract.State.FavoriteClicked -> {
                Snackbar.make(view!!, "Favorite Button Clicked", Snackbar.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
            }
            is CoinListContract.State.QueryRan -> {
                adapter.addItems(state.searchList)
            }
            is CoinListContract.State.Error -> {
                Log.d("OnStateChange", state.throwable.localizedMessage!!)
            }
        }
    }

    //
//    override fun onFavoriteClicked(coinFavoriteItem: CoinFavoriteItem) {
//        repositoryImpl.saveCoin(coinFavoriteItem)
//    }
//
//    var listener: OnCoinClicked? = null
//
//
//
//    override fun onAttach(view: View) {
//        super.onAttach(view)
//        listener = activity as OnCoinClicked
//
//
//        setViewVisibility(view.progressBar, true, View.GONE)
//
//
//        //Make network call to receive coinFavoriteItem items...this call doesn't contain link to Currency symbol
//        getCoinList()
//
//
//        searchDisposable = RxTextView.afterTextChangeEvents(view.searchView)
//                .map(Function<com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent, String>() {
//                    it.editable()
//                            .toString()
//                            .trim()
//                })
//                .debounce(500.toLong(), TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ query ->
//                    searchList = searchItems(query).blockingFirst()
//                    adapter.addItems(searchList)
//                }, { throwable ->
//                    Log.e("CoinListController", "Error search crypto list")
//                })
//        repositoryImpl = CoinFavoriteRepositoryImpl()
//
//    }
//
////    fun getWhole(): Observable<List<CoinListItem>> {
////        val wholeList = mutableListOf<CoinListItem>()
////        return Observable.zip(LuckyCoinApiClient().getCoins().map { it.subList(0, 10) }, getImages(list),
////                BiFunction<List<CoinListItem>, List<CoinListItem>, List<CoinListItem>> { list, imageList ->
////                    list.forEach { listItem ->
////                        val matched = imageList.find {
////                            it.id == listItem.id
////                        }
////                        wholeList.add(CoinListItem(id = listItem.id,
////                                name = listItem.name,
////                                symbol = listItem.symbol,
////                                slug = listItem.slug,
////                                quoteItem = listItem.quoteItem,
////                                imageUrl = matched?.imageUrl))
////                    }
////                    wholeList
////                })
////    }
//
//    private fun getCoinList() {
//        addDisposable(LuckyCoinApiClient()
//                .getCoins()
//                .compose { upstream: Observable<List<CoinListItem>> ->
//                    upstream
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                }
//                .subscribe({ response ->
//                    // make call here to fill the list with the images?
//                    list = response
//                    adapter.addItems(list)
//                    setViewVisibility(view?.progressBar, false, View.GONE)
//                },
//                        { _ ->
//                            Log.e("CoinListController", "Error retrieving list!!")
//                        }))
//
//
//    }
//
//
//    override fun onDetach(view: View) {
//        super.onDetach(view)
//        listener = null
//
//        //Get rid of any living disposables
//        compositeDisposable = null
//    }
//
//
    companion object {
        @JvmStatic
        fun newInstance(): CoinListController = CoinListController()
    }
//
//
//    fun addDisposable(disposable: Disposable) {
//        if (compositeDisposable == null) {
//            compositeDisposable = CompositeDisposable(disposable)
//        } else {
//            compositeDisposable!!.add(disposable)
//        }
//    }
//
//    fun setViewVisibility(view: View?, show: Boolean, goneType: Int) {
//        if (view == null) {
//            return
//        }
//        if (!show) {
//            view.visibility = View.GONE
//        } else {
//            view.visibility = View.VISIBLE
//        }
//    }
//
//    fun searchItems(query: String): Observable<List<CoinListItem>> {
//        return Observable.just(list).map { list ->
//            list.filter { coinFavoriteItem ->
//                coinFavoriteItem.name!!.toLowerCase().contains(query.toLowerCase())
//            }
//        }
//    }
}

