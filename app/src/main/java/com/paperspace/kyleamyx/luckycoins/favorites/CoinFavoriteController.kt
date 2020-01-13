package com.paperspace.kyleamyx.luckycoins.favorites

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.karakum.base.BaseMvvmController
import com.karakum.base.Mvvm
import com.paperspace.kyleamyx.RxBus
import com.paperspace.kyleamyx.RxEvent
import com.paperspace.kyleamyx.luckycoins.CoinMainActivity
import com.paperspace.kyleamyx.luckycoins.R
import com.paperspace.kyleamyx.luckycoins.favorites.adapter.CoinFavoriteAdapter
import com.paperspace.kyleamyx.luckycoins.favorites.db.CoinFavoriteItem
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.coin_favorite_controller.view.*
import org.koin.core.context.GlobalContext.get

class CoinFavoriteController : BaseMvvmController<CoinFavoriteViewModel, CoinFavoriteContract.State>(),
        CoinFavoriteAdapter.OnFavoriteRemoved, CoinFavoriteAdapter.OnFavoriteClicked {

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        CoinFavoriteAdapter(applicationContext!!, this, this)
    }

    private lateinit var listener: CoinFavoriteAdapter.OnFavoriteClicked
    private lateinit var favoriteDisposable: Disposable

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
        listener = activity as CoinMainActivity
        favoriteDisposable = RxBus.listen(RxEvent.AddFavorite::class.java).subscribe {
            viewModel.getFavorites()
            adapter.notifyDataSetChanged()
        }
    }

    /**
     * Keep this in viewModel since it has to call down to roomDB.
     */
    override fun onFavoriteRemoved(coinFavoriteItem: CoinFavoriteItem) {
        viewModel.removeFavorite(coinFavoriteItem)
        RxBus.publish(RxEvent.RemoveFavorite(true))
    }

    override fun onFavoriteClicked(coinFavoriteItem: CoinFavoriteItem) {
        listener.onFavoriteClicked(coinFavoriteItem)
    }

    override val viewModel: CoinFavoriteViewModel = get().koin.get()

    override fun onStateChange(state: Mvvm.State) {
        when (state) {
            is CoinFavoriteContract.State.FavoritesReceived -> {
                adapter.addItems(state.favoriteCoins)
            }
            is CoinFavoriteContract.State.FavoriteDeleted -> {
                adapter.addItems(state.favoriteCoins)
                adapter.notifyDataSetChanged()
            }
            is CoinFavoriteContract.State.Error -> {
                Log.d("Favorite Error", state.throwable.localizedMessage!!)
            }
        }
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        if (!favoriteDisposable.isDisposed) favoriteDisposable.dispose()
        Log.d("FAVORITE_CONTROLLER", "Favorite Controller Detached")
    }


    companion object {
        fun newInstance(): CoinFavoriteController =
                CoinFavoriteController()
    }

}