package com.example.kyleamyx.luckycoins.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluelinelabs.conductor.Controller
import com.example.kyleamyx.luckycoins.R
import com.example.kyleamyx.luckycoins.favorites.adapter.CoinFavoriteAdapter
import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteRepositoryImpl
import kotlinx.android.synthetic.main.coin_favorite_controller.view.*

class CoinFavoriteController : Controller() {

    lateinit var repositoryImpl: CoinFavoriteRepositoryImpl

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        CoinFavoriteAdapter(applicationContext!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.coin_favorite_controller, container, false).apply {
            favoritesRecycler.layoutManager = LinearLayoutManager(activity)
            favoritesRecycler.adapter = adapter
            favoritesRecycler.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }
    }

    override fun onAttach(view: View) {
        super.onAttach(view)

        repositoryImpl = CoinFavoriteRepositoryImpl()

        val favoriteList = repositoryImpl.getFavorites()
        adapter.addItems(favoriteList)

    }


    companion object {
        fun newInstance(): CoinFavoriteController =
                CoinFavoriteController()
    }

}