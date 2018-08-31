package com.example.kyleamyx.luckycoins.list.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.kyleamyx.luckycoins.models.CoinListItem
import kotlinx.android.synthetic.main.coin_list_item.view.*

/**
 * Created by kyleamyx on 6/23/18.
 */
class CoinListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindView(coin: CoinListItem) {
        itemView.coinName.text = coin.name
        itemView.coinSymbol.text = coin.symbol

    }

}