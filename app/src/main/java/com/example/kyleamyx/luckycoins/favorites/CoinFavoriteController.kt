package com.example.kyleamyx.luckycoins.favorites

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kyleamyx.luckycoins.R
import com.example.kyleamyx.luckycoins.base.BaseMvvmController
import com.example.kyleamyx.luckycoins.base.Mvvm
import com.example.kyleamyx.luckycoins.favorites.adapter.CoinFavoriteAdapter
import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteItem
import kotlinx.android.synthetic.main.coin_favorite_controller.view.*
import kotlinx.android.synthetic.main.coin_list_item.view.*
import org.koin.core.context.GlobalContext.get

class CoinFavoriteController : BaseMvvmController<CoinFavoriteViewModel, CoinFavoriteContract.State>(),
        CoinFavoriteAdapter.OnFavoriteRemoved {

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        CoinFavoriteAdapter(applicationContext!!, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(R.layout.coin_favorite_controller, container, false).apply {
            favoritesRecycler.layoutManager = LinearLayoutManager(activity)
            favoritesRecycler.adapter = adapter
            favoritesRecycler.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        }
        view.favoritesSwipeContainer.apply {
            this.setOnRefreshListener {
                viewModel.getFavorites()
                this.isRefreshing = false
            }
        }
        return view
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        Log.d("FAVORITE_CONTROLLER", "Attached")
        viewModel.getFavorites()

    }

    override fun onFavoriteRemoved(coinFavoriteItem: CoinFavoriteItem) {
        viewModel.removeFavorite(coinFavoriteItem)
    }

    override val viewModel: CoinFavoriteViewModel = get().koin.get()

    override fun onStateChange(state: Mvvm.State) {
        when (state) {
            is CoinFavoriteContract.State.FavoritesReceived -> {
                adapter.addItems(state.favoriteCoins)
            }
            is CoinFavoriteContract.State.FavoriteDeleted -> {
                adapter.addItems(state.favoriteCoins)
            }
            is CoinFavoriteContract.State.Error -> {
                Log.d("Favorite Error", state.throwable.localizedMessage!!)
            }
        }
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        Log.d("FAVORITE_CONTROLLER", "Favorite Controller Detached")
    }


    companion object {
        fun newInstance(): CoinFavoriteController =
                CoinFavoriteController()
    }

}