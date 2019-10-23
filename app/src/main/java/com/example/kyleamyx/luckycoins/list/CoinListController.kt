package com.example.kyleamyx.luckycoins.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.support.RouterPagerAdapter
import com.example.kyleamyx.luckycoins.R
import com.example.kyleamyx.luckycoins.base.BaseMvvmController
import com.example.kyleamyx.luckycoins.base.Mvvm
import com.example.kyleamyx.luckycoins.detail.CoinDetailActivity
import com.example.kyleamyx.luckycoins.favorites.FavoriteCoin
import com.example.kyleamyx.luckycoins.favorites.db.IFavoritesRepository
import com.example.kyleamyx.luckycoins.list.adapter.CoinListAdapter
import com.example.kyleamyx.luckycoins.models.CoinListItem
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.coin_list_controller.view.*
import org.koin.core.context.GlobalContext.get

/**
 * Created by kyleamyx on 6/23/18.
 */

//todo- add shimmer by facebook for loading of views
class CoinListController : BaseMvvmController<CoinListViewModel, CoinListContract.State>(), CoinListAdapter
.CoinListListener {


    var list: List<CoinListItem> = emptyList()
    var compositeDisposable: CompositeDisposable? = null
    var recyclerView: RecyclerView? = null
    var searchDisposable: Disposable? = null
    lateinit var swipeLayout: SwipeRefreshLayout
    lateinit var repository: IFavoritesRepository

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        CoinListAdapter(applicationContext!!, this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.coin_list_controller, container, false)
        swipeLayout = view.findViewById(R.id.swipeContainer)
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
            getCoinList()
            setSearchListener(view)
        }

    }


    override fun onCoinClicked(coin: CoinListItem) {
        viewModel.onCoinClicked(coin)
    }

    override fun onFavoriteClicked(coin: FavoriteCoin) {
        viewModel.onFavoriteClicked()
    }

    override val viewModel: CoinListViewModel = get().koin.get()

    override fun onStateChange(state: Mvvm.State) {
        when (state) {
            is CoinListContract.State.CoinListReceived -> {
                adapter.addItems(state.coins)
            }
            is CoinListContract.State.CoinItemClicked -> {
                Snackbar.make(view!!, "Coin Item Clicked", Snackbar.LENGTH_SHORT)
                startActivity(CoinDetailActivity.getLaunchIntent(activity!!, state.coin))
            }
            is CoinListContract.State.FavoriteClicked -> {
                Snackbar.make(view!!, "Favorite Button Clicked", Snackbar.LENGTH_SHORT)
            }
            is CoinListContract.State.QueryRan -> {
                adapter.addItems(state.searchList)
            }
            is CoinListContract.State.Error -> {
                Log.d("OnStateChange", state.throwable.localizedMessage!!)
            }
        }
    }

    inner class TabAdapter : RouterPagerAdapter(this){
        override fun configureRouter(router: Router, position: Int) {
            println("Router: ${router.containerId}, Position: $position")
        }

        override fun getCount(): Int {
           return 2
        }
    }

    //
//    override fun onFavoriteClicked(coin: FavoriteCoin) {
//        repository.saveCoin(coin)
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
//        //Make network call to receive coin items...this call doesn't contain link to Currency symbol
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
//        repository = FavoritesRepository()
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
//            list.filter { coin ->
//                coin.name!!.toLowerCase().contains(query.toLowerCase())
//            }
//        }
//    }
}

