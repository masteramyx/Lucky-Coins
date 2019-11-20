package com.example.kyleamyx.luckycoins.favorites.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteItem
import kotlinx.android.synthetic.main.coin_favorite_item.view.*

class CoinFavoriteViewHolder(view: View,
                             private val listener: CoinFavoriteAdapter.OnFavoriteRemoved,
                             private val clickListener: CoinFavoriteAdapter.OnFavoriteClicked)
    : RecyclerView.ViewHolder(view) {


    fun bindView(coinFavoriteItem: CoinFavoriteItem) {
        itemView.favoriteSymbol.text = coinFavoriteItem.symbol
        itemView.favoriteName.text = coinFavoriteItem.name

        itemView.favoriteRemoveBtn.setOnClickListener {
            listener.onFavoriteRemoved(coinFavoriteItem)
        }

        itemView.setOnClickListener {
            clickListener.onFavoriteClicked(coinFavoriteItem)
        }
    }
}