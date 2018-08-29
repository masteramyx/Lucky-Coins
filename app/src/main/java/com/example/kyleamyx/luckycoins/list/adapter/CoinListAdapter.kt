package com.example.kyleamyx.luckycoins.list.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.kyleamyx.luckycoins.models.CoinListItem

/**
 * Created by kyleamyx on 6/23/18.
 */

class CoinListAdapter(val coin: CoinListItem) : RecyclerView.Adapter<CoinListViewHolder>() {


    override fun getItemCount(): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: CoinListViewHolder, position: Int) {
        holder.bindView(coin)
    }

}