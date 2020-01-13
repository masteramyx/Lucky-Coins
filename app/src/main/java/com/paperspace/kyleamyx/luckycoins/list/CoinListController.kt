package com.paperspace.kyleamyx.luckycoins.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paperspace.kyleamyx.luckycoins.CoinMainActivity
import com.paperspace.kyleamyx.luckycoins.R
import com.paperspace.kyleamyx.luckycoins.list.adapter.CoinListAdapter
import com.paperspace.kyleamyx.luckycoins.favorites.db.CoinFavoriteItem
import com.paperspace.kyleamyx.luckycoins.models.CoinListItem
import com.google.android.material.snackbar.Snackbar
import com.karakum.base.BaseMvvmController
import com.karakum.base.Mvvm
import com.paperspace.kyleamyx.RxBus
import com.paperspace.kyleamyx.RxEvent
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.coin_list_controller.view.*
import org.koin.core.context.GlobalContext.get

/**
 * Created by kyleamyx on 6/23/18
 */

//todo- add shimmer by facebook for loading of views
class CoinListController : BaseMvvmController<CoinListViewModel, CoinListContract.State>(), CoinListAdapter
.CoinListListener {


    var list: List<CoinListItem> = emptyList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteDisposable: Disposable

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        CoinListAdapter(applicationContext!!, this)
    }

    private lateinit var listener: CoinListAdapter.CoinListListener


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.coin_list_controller, container, false)
        recyclerView = view.findViewById(R.id.listRecycler)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            adapter = this@CoinListController.adapter
            isNestedScrollingEnabled = false
        }
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
        Log.d("LIST_CONTROLLER", "Attached")
        with(viewModel) {
            if (list.isEmpty()) {
                getCoinList()
                setSearchListener(view)
            } else {
                //Reset adapter in case controller was detached(adapter set to null)
                view.listRecycler.adapter = adapter
                adapter.addItems(list)
            }
        }
        listener = activity as CoinMainActivity
        favoriteDisposable = RxBus.listen(RxEvent.RemoveFavorite::class.java).subscribe {
            viewModel.getCoinList()
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        view.listRecycler.adapter = null
        if (!favoriteDisposable.isDisposed) favoriteDisposable.dispose()
        Log.d("LIST_CONTROLLER", "Detached")
    }



    override fun onCoinClicked(coin: CoinListItem) {
        viewModel.onCoinClicked(coin)
    }

    override fun onFavoriteClicked(coinFavoriteItem: CoinFavoriteItem) {
        viewModel.onFavoriteClicked(coinFavoriteItem)
        adapter.notifyDataSetChanged()
        //update favorite controller
        RxBus.publish(RxEvent.AddFavorite(true))
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

    companion object {
        @JvmStatic
        fun newInstance(): CoinListController = CoinListController()
    }

}

