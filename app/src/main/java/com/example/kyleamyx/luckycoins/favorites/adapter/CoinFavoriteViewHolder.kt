package com.example.kyleamyx.luckycoins.favorites.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.kyleamyx.luckycoins.models.CoinFavoriteItem
import kotlinx.android.synthetic.main.coin_favorite_item.view.*

class CoinFavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {


    fun bindView(coinFavoriteItem: CoinFavoriteItem) {
        itemView.favoriteSymbol.text = coinFavoriteItem.symbol
        itemView.favoriteName.text = coinFavoriteItem.name

    }
}