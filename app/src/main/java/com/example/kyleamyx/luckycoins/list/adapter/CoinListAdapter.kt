package com.example.kyleamyx.luckycoins.list.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kyleamyx.luckycoins.R
import com.example.kyleamyx.luckycoins.favorites.db.CoinFavoriteItem
import com.example.kyleamyx.luckycoins.models.CoinListItem

/**
 * Created by kyleamyx on 6/23/18.
 */

class CoinListAdapter(context: Context, private val listener: CoinListListener) : RecyclerView
.Adapter<CoinListViewHolder>() {

    private var coinList: List<CoinListItem> = emptyList()
    private val inflater: LayoutInflater = LayoutInflater.from(context)


    interface CoinListListener {
        fun onCoinClicked(coin: CoinListItem)
        fun onFavoriteClicked(coinFavoriteItem: CoinFavoriteItem)
    }


    override fun getItemCount(): Int {
        return coinList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListViewHolder {
        val view = inflater.inflate(R.layout.coin_list_item, parent, false)
        return CoinListViewHolder(view)

    }

    override fun onBindViewHolder(holder: CoinListViewHolder, position: Int) {
        holder.bindView(coinList[position])
        holder.listener = this.listener
    }

    fun addItems(list: List<CoinListItem>) {
        coinList = list
        if (coinList.isNotEmpty()) {
            notifyDataSetChanged()
        }
    }

    fun getItem(position: Int): CoinListItem {
        return coinList[position]
    }
}

