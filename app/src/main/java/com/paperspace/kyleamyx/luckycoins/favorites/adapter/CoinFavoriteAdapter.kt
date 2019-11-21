package com.paperspace.kyleamyx.luckycoins.favorites.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paperspace.kyleamyx.luckycoins.R
import com.paperspace.kyleamyx.luckycoins.favorites.db.CoinFavoriteItem

class CoinFavoriteAdapter(context: Context,
                          private val listener: OnFavoriteRemoved,
                          private val clickListener: OnFavoriteClicked) :
        RecyclerView.Adapter<CoinFavoriteViewHolder>() {

    interface OnFavoriteRemoved {
        fun onFavoriteRemoved(coinFavoriteItem: CoinFavoriteItem)
    }

    interface OnFavoriteClicked {
        fun onFavoriteClicked(coinFavoriteItem: CoinFavoriteItem)
    }

    private var coinFavoriteItemList: List<CoinFavoriteItem> = emptyList()
    private val inflater: LayoutInflater = LayoutInflater.from(context)


    override fun getItemCount(): Int {
        return coinFavoriteItemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinFavoriteViewHolder {
        val view = inflater.inflate(R.layout.coin_favorite_item, parent, false)
        return CoinFavoriteViewHolder(view, listener, clickListener)

    }

    override fun onBindViewHolder(holder: CoinFavoriteViewHolder, position: Int) {
        holder.bindView(coinFavoriteItemList[position])
    }

    fun addItems(list: List<CoinFavoriteItem>) {
        coinFavoriteItemList = list
        if (coinFavoriteItemList.isNotEmpty()) {
            notifyDataSetChanged()
        }
    }

    fun getItem(position: Int): CoinFavoriteItem {
        return coinFavoriteItemList[position]
    }
}
