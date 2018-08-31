package com.example.kyleamyx.luckycoins.list.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kyleamyx.luckycoins.R
import com.example.kyleamyx.luckycoins.models.CoinListItem

/**
 * Created by kyleamyx on 6/23/18.
 */

class CoinListAdapter(context: Context) : RecyclerView.Adapter<CoinListViewHolder>() {

    private var coinList: List<CoinListItem> = emptyList()
    private val inflater: LayoutInflater = LayoutInflater.from(context)


    override fun getItemCount(): Int {
        return coinList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListViewHolder {
        //return CoinListViewHolder(View.inflate(context, R.layout.coin_list_item, parent))
        return CoinListViewHolder(inflater.inflate(R.layout.coin_list_item, parent, false))

    }

    override fun onBindViewHolder(holder: CoinListViewHolder, position: Int) {
        holder.bindView(coinList[position])
    }

    fun addItems(list: List<CoinListItem>) {
        coinList = list
        if (coinList.isNotEmpty()) {
            notifyDataSetChanged()
        }
    }
}

