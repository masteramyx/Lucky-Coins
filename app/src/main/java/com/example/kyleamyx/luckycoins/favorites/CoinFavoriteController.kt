package com.example.kyleamyx.luckycoins.favorites

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.example.kyleamyx.luckycoins.R
import com.example.kyleamyx.luckycoins.list.adapter.CoinListAdapter
import com.example.kyleamyx.luckycoins.models.CoinListItem

class CoinFavoriteController : Controller(), CoinListAdapter.CoinListListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.coin_favorite_controller, container, false)
    }

    override fun onCoinClicked(coin: CoinListItem) {
        Log.d("CoinFavoriteController", coin.id!!)
    }


    override fun onAttach(view: View) {
        super.onAttach(view)

    }


    companion object {
        fun newInstance(): CoinFavoriteController =
                CoinFavoriteController()
    }

}