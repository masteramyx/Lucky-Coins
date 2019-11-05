package com.example.kyleamyx.luckycoins.favorites.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.kyleamyx.luckycoins.models.CoinFavoriteItem
import kotlinx.android.synthetic.main.coin_favorite_item.view.*

class CoinFavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {


    lateinit var coinFavoriteItemItem: CoinFavoriteItem

    fun bindView(coinFavoriteItem: CoinFavoriteItem) {
        coinFavoriteItemItem = coinFavoriteItem
        itemView.favoriteSymbol.text = coinFavoriteItemItem.symbol
        itemView.favoriteName.text = coinFavoriteItem.name

    }

    // String Resource Ext. Function
    fun View.getStringResource(resourceId: Int): String = this.context.getString(resourceId)
}