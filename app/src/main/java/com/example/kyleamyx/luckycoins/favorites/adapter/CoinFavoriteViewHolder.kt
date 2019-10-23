package com.example.kyleamyx.luckycoins.favorites.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.kyleamyx.luckycoins.favorites.FavoriteCoin
import kotlinx.android.synthetic.main.coin_favorite_item.view.*

class CoinFavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {


    lateinit var coinItem: FavoriteCoin

    fun bindView(coin: FavoriteCoin) {
        coinItem = coin
        itemView.favoriteSymbol.text = coinItem.symbol
        itemView.favoriteName.text = coin.name

    }

    // String Resource Ext. Function
    fun View.getStringResource(resourceId: Int): String = this.context.getString(resourceId)
}