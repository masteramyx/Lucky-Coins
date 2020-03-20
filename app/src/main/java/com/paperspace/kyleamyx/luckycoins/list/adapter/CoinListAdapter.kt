package com.paperspace.kyleamyx.luckycoins.list.adapter

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.paperspace.kyleamyx.luckycoins.R
import com.paperspace.kyleamyx.luckycoins.favorites.db.CoinFavoriteItem
import com.paperspace.kyleamyx.luckycoins.models.CoinListItem

/**
 * Created by kyleamyx on 6/23/18.
 */

class CoinListAdapter(context: Context, private val listener: CoinListListener) :
        RecyclerView.Adapter<CoinListViewHolder>() {

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
        Log.d("CoinListAdapter", "Listener set at position: $position")
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

