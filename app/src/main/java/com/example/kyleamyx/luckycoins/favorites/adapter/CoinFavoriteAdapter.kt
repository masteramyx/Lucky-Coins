package com.example.kyleamyx.luckycoins.favorites.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kyleamyx.luckycoins.R
import com.example.kyleamyx.luckycoins.favorites.FavoriteCoin

class CoinFavoriteAdapter(context: Context) : RecyclerView.Adapter<CoinFavoriteViewHolder>() {

    private var coinList: List<FavoriteCoin> = emptyList()
    private val inflater: LayoutInflater = LayoutInflater.from(context)


    override fun getItemCount(): Int {
        return coinList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinFavoriteViewHolder {
        //return CoinListViewHolder(View.inflate(context, R.layout.coin_list_item, parent))
        val view = inflater.inflate(R.layout.coin_favorite_item, parent, false)
        return CoinFavoriteViewHolder(view)

    }

    override fun onBindViewHolder(holder: CoinFavoriteViewHolder, position: Int) {
        holder.bindView(coinList[position])
    }

    fun addItems(list: List<FavoriteCoin>) {
        coinList = list
        if (coinList.isNotEmpty()) {
            notifyDataSetChanged()
        }
    }

    fun getItem(position: Int): FavoriteCoin {
        return coinList[position]
    }
}
